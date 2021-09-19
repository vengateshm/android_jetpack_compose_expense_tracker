package com.android.vengateshm.expensetracker.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import com.android.vengateshm.expensetracker.data.local.room.dto.ExpenseCategoryDto

@Dao
interface ExpenseCategoryDao {
    @Insert
    fun insertAll(expenseCategoryDtoList: List<ExpenseCategoryDto>)
}