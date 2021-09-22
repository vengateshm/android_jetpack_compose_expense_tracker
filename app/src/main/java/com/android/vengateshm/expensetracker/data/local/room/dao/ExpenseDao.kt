package com.android.vengateshm.expensetracker.data.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.android.vengateshm.expensetracker.data.local.room.dto.ExpenseDto
import com.android.vengateshm.expensetracker.data.local.room.dto.ExpenseWithCategoryDto
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Insert
    suspend fun insert(expenseDto: ExpenseDto)

    @Delete
    suspend fun delete(expenseDto: ExpenseDto)

    @Query("SELECT * FROM expense e INNER JOIN expense_category ec ON e.expense_category_id = ec.category_id INNER JOIN payment_type pt ON e.expense_payment_type_id = pt.payment_type_id")
    fun getAllExpenses(): Flow<List<ExpenseWithCategoryDto>>

    @Query("SELECT * FROM expense e INNER JOIN expense_category ec ON e.expense_category_id = ec.category_id INNER JOIN payment_type pt ON e.expense_payment_type_id = pt.payment_type_id WHERE e.expense_category_id = :expenseCategoryId")
    fun getExpensesByCategory(expenseCategoryId: Long): Flow<List<ExpenseWithCategoryDto>>

    @Query("SELECT * FROM expense e INNER JOIN expense_category ec ON e.expense_category_id = ec.category_id INNER JOIN payment_type pt ON e.expense_payment_type_id = pt.payment_type_id WHERE e.expense_id = :expenseId")
    suspend fun getExpenseById(expenseId: Long): ExpenseWithCategoryDto
}