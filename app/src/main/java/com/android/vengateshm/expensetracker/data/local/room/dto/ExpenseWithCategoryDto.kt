package com.android.vengateshm.expensetracker.data.local.room.dto

import androidx.room.ColumnInfo
import com.android.vengateshm.expensetracker.domain.model.ExpenseWithCategory
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
    @ColumnInfo(name = "payment_type_id")
    val paymentTypeId: Long? = null,
    @ColumnInfo(name = "name")
    val paymentTypeName: String,
)

fun ExpenseWithCategoryDto.toExpenseWithCategory() = ExpenseWithCategory(
    expenseId = this.expenseId,
    description = this.description,
    amount = this.amount,
    createdAt = this.createdAt,
    categoryId = this.categoryId,
    categoryName = this.categoryName,
    paymentTypeId = this.paymentTypeId,
    paymentTypeName = this.paymentTypeName
)
