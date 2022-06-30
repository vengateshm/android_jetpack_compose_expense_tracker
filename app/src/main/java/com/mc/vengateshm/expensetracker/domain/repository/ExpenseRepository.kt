package com.mc.vengateshm.expensetracker.domain.repository

import com.mc.vengateshm.expensetracker.data.local.room.dto.ExpenseCategoryDto
import com.mc.vengateshm.expensetracker.data.local.room.dto.ExpenseDto
import com.mc.vengateshm.expensetracker.data.local.room.dto.ExpenseWithCategoryDto
import com.mc.vengateshm.expensetracker.data.local.room.dto.PaymentTypeDto
import com.mc.vengateshm.expensetracker.domain.model.ExpenseCategory
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    suspend fun addExpense(expenseDto: ExpenseDto)
    suspend fun deleteExpense(expenseDto: ExpenseDto)
    fun getAllExpenses(): Flow<List<ExpenseWithCategoryDto>>
    fun getExpensesByCategory(expenseCategory: ExpenseCategory): Flow<List<ExpenseWithCategoryDto>>
    suspend fun getAllCategories(): List<ExpenseCategoryDto>
    fun consumeAllCategories(): Flow<List<ExpenseCategoryDto>>
    suspend fun getAllPaymentType(): List<PaymentTypeDto>
    suspend fun addExpenseCategory(expenseCategoryDto: ExpenseCategoryDto)
    suspend fun getExpenseById(expenseId: Long): ExpenseWithCategoryDto
}