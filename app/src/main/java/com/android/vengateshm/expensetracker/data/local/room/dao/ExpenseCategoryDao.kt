package com.android.vengateshm.expensetracker.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.android.vengateshm.expensetracker.data.local.room.dto.ExpenseCategoryDto
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseCategoryDao {
    @Insert
    fun insertAll(expenseCategoryDtoList: List<ExpenseCategoryDto>)

    @Query("SELECT * FROM expense_category")
    fun getAllCategories(): List<ExpenseCategoryDto>
}