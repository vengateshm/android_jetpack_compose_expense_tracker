package com.mc.vengateshm.expensetracker.presentation.expenseList

import com.mc.vengateshm.expensetracker.domain.model.ExpenseWithCategory

data class ExpenseListState(
    val isLoading: Boolean = false,
    val expenseList: List<ExpenseWithCategory> = emptyList(),
    val error: String = "",
)
