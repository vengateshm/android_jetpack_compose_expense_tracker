package com.android.vengateshm.expensetracker.di.modules

import android.content.Context
import com.android.vengateshm.expensetracker.data.local.room.AppDatabase
import com.android.vengateshm.expensetracker.data.local.room.dao.ExpenseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context) :AppDatabase{
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideExpenseDao(appDatabase: AppDatabase): ExpenseDao {
        return appDatabase.expenseDao()
    }
}