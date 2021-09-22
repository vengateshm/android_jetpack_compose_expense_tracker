package com.android.vengateshm.expensetracker.di.modules

import com.android.vengateshm.expensetracker.data.local.room.dao.ExpenseCategoryDao
import com.android.vengateshm.expensetracker.data.local.room.dao.ExpenseDao
import com.android.vengateshm.expensetracker.data.local.room.dao.PaymentTypeDao
import com.android.vengateshm.expensetracker.data.local.room.repository.ExpenseRepositoryImpl
import com.android.vengateshm.expensetracker.domain.repository.ExpenseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // All dependencies in this module live as long as the application is alive
object AppModule {

    @Provides
    @Singleton
    fun provideExpenseRepository(
        expenseDao: ExpenseDao,
        expenseCategoryDao: ExpenseCategoryDao,
        paymentTypeDao: PaymentTypeDao,
    ): ExpenseRepository =
        ExpenseRepositoryImpl(
            expenseDao,
            expenseCategoryDao,
            paymentTypeDao
        )
}