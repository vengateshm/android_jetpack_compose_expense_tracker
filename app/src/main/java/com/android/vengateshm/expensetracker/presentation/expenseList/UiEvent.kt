package com.android.vengateshm.expensetracker.presentation.expenseList

import com.android.vengateshm.expensetracker.domain.model.ExpenseCategory

sealed class UiEvent {
    class SortMode(val isOn: Boolean) : UiEvent()
    class ExpenseCategorySelectionForSort(val expenseCategory: ExpenseCategory?, val position:Int=0) : UiEvent()
}
