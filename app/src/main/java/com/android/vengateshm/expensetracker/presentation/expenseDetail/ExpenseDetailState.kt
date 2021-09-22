package com.android.vengateshm.expensetracker.presentation.expenseDetail

import com.android.vengateshm.expensetracker.domain.model.ExpenseWithCategory

data class ExpenseDetailState(
    val isLoading: Boolean = false,
    val expenseWithCategory: ExpenseWithCategory? = null,
    val error: String = ""
)