package com.mc.vengateshm.expensetracker.presentation.expenseList

import com.mc.vengateshm.expensetracker.domain.model.ExpenseCategory

sealed class UiEvent {
    class SortMode(val isOn: Boolean) : UiEvent()
    class ExpenseCategorySelectionForSort(val expenseCategory: ExpenseCategory?, val position:Int=0) : UiEvent()
}
