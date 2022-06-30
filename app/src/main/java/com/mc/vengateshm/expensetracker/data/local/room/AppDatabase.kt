package com.mc.vengateshm.expensetracker.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.mc.vengateshm.expensetracker.backgroundWorkers.DbPrepopulateWorker
import com.mc.vengateshm.expensetracker.common.DB_NAME
import com.mc.vengateshm.expensetracker.data.local.room.dao.ExpenseCategoryDao
import com.mc.vengateshm.expensetracker.data.local.room.dao.ExpenseDao
import com.mc.vengateshm.expensetracker.data.local.room.dao.PaymentTypeDao
import com.mc.vengateshm.expensetracker.data.local.room.dto.ExpenseCategoryDto
import com.mc.vengateshm.expensetracker.data.local.room.dto.ExpenseDto
import com.mc.vengateshm.expensetracker.data.local.room.dto.PaymentTypeDto
import com.mc.vengateshm.expensetracker.data.local.room.typeConverters.Converters

@Database(
    entities = [ExpenseCategoryDto::class, ExpenseDto::class, PaymentTypeDto::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun paymentTypeDao(): PaymentTypeDao
    abstract fun expenseCategoryDao(): ExpenseCategoryDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: createDatabase(context).also {
                    INSTANCE = it
                }
            }
        }

        private fun createDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DB_NAME
            )
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // Use one time work to prepopulate the database
                        OneTimeWorkRequestBuilder<com.mc.vengateshm.expensetracker.backgroundWorkers.DbPrepopulateWorker>().build().also {
                            WorkManager.getInstance(context).enqueue(it)
                        }
                    }
                })
                .build()
        }
    }
}