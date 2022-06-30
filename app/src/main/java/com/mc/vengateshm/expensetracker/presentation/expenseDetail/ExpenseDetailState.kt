package com.mc.vengateshm.expensetracker.presentation.expenseDetail

import com.mc.vengateshm.expensetracker.domain.model.ExpenseWithCategory

data class ExpenseDetailState(
    val isLoading: Boolean = false,
    val expenseWithCategory: ExpenseWithCategory? = null,
    val error: String = ""
)