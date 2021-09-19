package com.android.vengateshm.expensetracker.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.android.vengateshm.expensetracker.data.local.room.dto.ExpenseDto
import com.android.vengateshm.expensetracker.data.local.room.dto.ExpenseWithCategoryDto
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Insert
    suspend fun insert(expenseDto: ExpenseDto)

    @Query("SELECT * FROM expense e INNER JOIN expense_category ec ON e.expense_category_id = ec.category_id")
    fun getAllExpensesWithCategoryRecord(): Flow<List<ExpenseWithCategoryDto>>
}