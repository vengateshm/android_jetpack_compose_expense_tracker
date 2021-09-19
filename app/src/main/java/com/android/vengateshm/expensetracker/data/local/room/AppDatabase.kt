package com.android.vengateshm.expensetracker.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.android.vengateshm.expensetracker.common.DB_NAME
import com.android.vengateshm.expensetracker.common.exts.ioThread
import com.android.vengateshm.expensetracker.data.local.room.dao.ExpenseCategoryDao
import com.android.vengateshm.expensetracker.data.local.room.dao.ExpenseDao
import com.android.vengateshm.expensetracker.data.local.room.dao.PaymentTypeDao
import com.android.vengateshm.expensetracker.data.local.room.dto.ExpenseCategoryDto
import com.android.vengateshm.expensetracker.data.local.room.dto.ExpenseDto
import com.android.vengateshm.expensetracker.data.local.room.dto.PaymentTypeDto
import com.android.vengateshm.expensetracker.data.local.room.prepopulateData.defaultExpenseCategoryDtoTypeList
import com.android.vengateshm.expensetracker.data.local.room.prepopulateData.defaultPaymentDtoTypeList
import com.android.vengateshm.expensetracker.data.local.room.typeConverters.Converters

@Database(entities = [ExpenseCategoryDto::class, ExpenseDto::class, PaymentTypeDto::class],
    version = 1,
    exportSchema = true)
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
            return Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java,
                DB_NAME)
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        ioThread {
                            getInstance(context).paymentTypeDao().insertAll(
                                defaultPaymentDtoTypeList
                            )
                            getInstance(context).expenseCategoryDao().insertAll(
                                defaultExpenseCategoryDtoTypeList
                            )
                        }
                    }
                })
                .build()
        }
    }
}