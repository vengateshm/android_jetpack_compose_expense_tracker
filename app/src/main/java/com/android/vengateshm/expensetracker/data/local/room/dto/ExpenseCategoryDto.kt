package com.android.vengateshm.expensetracker.data.local.room.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.vengateshm.expensetracker.domain.model.ExpenseCategory

@Entity(tableName = "expense_category")
data class ExpenseCategoryDto(
    @ColumnInfo(name = "category_id")
    @PrimaryKey(autoGenerate = true)
    val categoryId: Long? = null,
    @ColumnInfo(name = "category_name")
    val categoryName: String,
)

fun ExpenseCategoryDto.toExpenseCategory() = ExpenseCategory(
    categoryId = this.categoryId,
    categoryName = this.categoryName
)
