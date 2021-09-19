package com.android.vengateshm.expensetracker.domain.repository

import com.android.vengateshm.expensetracker.data.local.room.dto.ExpenseDto
import com.android.vengateshm.expensetracker.data.local.room.dto.ExpenseWithCategoryDto
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    suspend fun addExpense(expenseDto: ExpenseDto)
    fun getAllExpenses(): Flow<List<ExpenseWithCategoryDto>>
}