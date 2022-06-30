package com.mc.vengateshm.expensetracker.di.modules

import com.mc.vengateshm.expensetracker.data.local.room.dao.ExpenseCategoryDao
import com.mc.vengateshm.expensetracker.data.local.room.dao.ExpenseDao
import com.mc.vengateshm.expensetracker.data.local.room.dao.PaymentTypeDao
import com.mc.vengateshm.expensetracker.data.local.room.repository.ExpenseRepositoryImpl
import com.mc.vengateshm.expensetracker.domain.repository.ExpenseRepository
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