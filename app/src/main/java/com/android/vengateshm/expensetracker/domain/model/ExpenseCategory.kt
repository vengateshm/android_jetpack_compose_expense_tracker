package com.android.vengateshm.expensetracker.domain.model

import com.android.vengateshm.expensetracker.data.local.room.dto.ExpenseCategoryDto
import com.android.vengateshm.expensetracker.presentation.expenseList.components.ChipLabel

data class ExpenseCategory(
    val categoryId: Long? = null,
    val categoryName: String,
) : ChipLabel {
    override var chipLabel: String
        get() = this.categoryName
        set(value) {}
}

fun ExpenseCategory.toExpenseCategoryDto() = ExpenseCategoryDto(
    categoryId = this.categoryId,
    categoryName = this.categoryName
)
