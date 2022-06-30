package com.mc.vengateshm.expensetracker.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mc.vengateshm.expensetracker.data.local.room.dto.ExpenseCategoryDto
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseCategoryDao {
    @Insert
    fun insertAll(expenseCategoryDtoList: List<ExpenseCategoryDto>)

    @Query("SELECT * FROM expense_category")
    suspend fun getAllCategories(): List<ExpenseCategoryDto>

    @Query("SELECT * FROM expense_category")
    fun consumeAllCategories(): Flow<List<ExpenseCategoryDto>>

    @Insert
    fun addExpenseCategory(expenseCategoryDto: ExpenseCategoryDto)
}