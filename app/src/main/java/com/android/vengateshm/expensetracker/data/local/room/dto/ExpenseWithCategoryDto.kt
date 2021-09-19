package com.android.vengateshm.expensetracker.data.local.room.dto

import androidx.room.ColumnInfo
import java.util.*

data class ExpenseWithCategoryDto(
    @ColumnInfo(name = "expense_id")
    val expenseId: Long? = null,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "amount")
    val amount: Double,
    @ColumnInfo(name = "created_at")
    val createdAt: Calendar?,
    @ColumnInfo(name = "category_id")
    val categoryId: Long? = null,
    @ColumnInfo(name = "category_name")
    val categoryName: String,
)
