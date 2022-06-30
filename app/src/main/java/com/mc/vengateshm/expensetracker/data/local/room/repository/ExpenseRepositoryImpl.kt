package com.mc.vengateshm.expensetracker.data.local.room.repository

import com.mc.vengateshm.expensetracker.data.local.room.dao.ExpenseCategoryDao
import com.mc.vengateshm.expensetracker.data.local.room.dao.ExpenseDao
import com.mc.vengateshm.expensetracker.data.local.room.dao.PaymentTypeDao
import com.mc.vengateshm.expensetracker.data.local.room.dto.ExpenseCategoryDto
import com.mc.vengateshm.expensetracker.data.local.room.dto.ExpenseDto
import com.mc.vengateshm.expensetracker.data.local.room.dto.ExpenseWithCategoryDto
import com.mc.vengateshm.expensetracker.data.local.room.dto.PaymentTypeDto
import com.mc.vengateshm.expensetracker.domain.model.ExpenseCategory
import com.mc.vengateshm.expensetracker.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseRepositoryImpl @Inject constructor(
    private val expenseDao: ExpenseDao,
    private val expenseCategoryDao: ExpenseCategoryDao,
    private val paymentTypeDao: PaymentTypeDao,
) :
    ExpenseRepository {
    override suspend fun addExpense(expenseDto: ExpenseDto) {
        expenseDao.insert(expenseDto)
    }

    override suspend fun deleteExpense(expenseDto: ExpenseDto) {
        expenseDao.delete(expenseDto)
    }

    override fun getAllExpenses(): Flow<List<ExpenseWithCategoryDto>> {
        return expenseDao.getAllExpenses()
    }

    override fun getExpensesByCategory(expenseCategory: ExpenseCategory): Flow<List<ExpenseWithCategoryDto>> {
        return expenseDao.getExpensesByCategory(expenseCategoryId = expenseCategory.categoryId!!)
    }

    override suspend fun getAllCategories(): List<ExpenseCategoryDto> {
        return expenseCategoryDao.getAllCategories()
    }

    override fun consumeAllCategories(): Flow<List<ExpenseCategoryDto>> {
        return expenseCategoryDao.consumeAllCategories()
    }

    override suspend fun getAllPaymentType(): List<PaymentTypeDto> {
        return paymentTypeDao.getAllPaymentTypes()
    }

    override suspend fun addExpenseCategory(expenseCategoryDto: ExpenseCategoryDto) {
        expenseCategoryDao.addExpenseCategory(expenseCategoryDto)
    }

    override suspend fun getExpenseById(expenseId: Long): ExpenseWithCategoryDto {
        return expenseDao.getExpenseById(expenseId)
    }
}