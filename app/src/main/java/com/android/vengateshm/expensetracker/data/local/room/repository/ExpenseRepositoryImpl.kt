package com.android.vengateshm.expensetracker.data.local.room.repository

import com.android.vengateshm.expensetracker.data.local.room.dao.ExpenseDao
import com.android.vengateshm.expensetracker.data.local.room.dto.ExpenseDto
import com.android.vengateshm.expensetracker.data.local.room.dto.ExpenseWithCategoryDto
import com.android.vengateshm.expensetracker.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseRepositoryImpl @Inject constructor(private val expenseDao: ExpenseDao) :
    ExpenseRepository {
    override suspend fun addExpense(expenseDto: ExpenseDto) {
        expenseDao.insert(expenseDto)
    }

    override fun getAllExpenses(): Flow<List<ExpenseWithCategoryDto>> {
        return expenseDao.getAllExpensesWithCategoryRecord()
    }
}