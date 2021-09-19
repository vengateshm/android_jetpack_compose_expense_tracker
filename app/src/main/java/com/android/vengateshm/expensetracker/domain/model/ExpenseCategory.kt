package com.android.vengateshm.expensetracker.domain.model

import com.android.vengateshm.expensetracker.data.local.room.dto.ExpenseCategoryDto

data class ExpenseCategory(
    val categoryId: Long? = null,
    val categoryName: String,
)

fun ExpenseCategory.toExpenseCategoryDto() = ExpenseCategoryDto(
    categoryId = this.categoryId,
    categoryName = this.categoryName
)
