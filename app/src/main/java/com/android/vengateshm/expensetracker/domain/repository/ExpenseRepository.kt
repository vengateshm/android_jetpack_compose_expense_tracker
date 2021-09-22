package com.android.vengateshm.expensetracker.domain.repository

import com.android.vengateshm.expensetracker.data.local.room.dto.ExpenseCategoryDto
import com.android.vengateshm.expensetracker.data.local.room.dto.ExpenseDto
import com.android.vengateshm.expensetracker.data.local.room.dto.ExpenseWithCategoryDto
import com.android.vengateshm.expensetracker.data.local.room.dto.PaymentTypeDto
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    suspend fun addExpense(expenseDto: ExpenseDto)
    suspend fun deleteExpense(expenseDto: ExpenseDto)
    fun getAllExpenses(): Flow<List<ExpenseWithCategoryDto>>
    suspend fun getAllCategories(): List<ExpenseCategoryDto>
    suspend fun getAllPaymentType(): List<PaymentTypeDto>
    suspend fun addExpenseCategory(expenseCategoryDto: ExpenseCategoryDto)
    suspend fun getExpenseById(expenseId: Long):ExpenseWithCategoryDto
}