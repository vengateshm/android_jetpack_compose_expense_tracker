package com.android.vengateshm.expensetracker.presentation.more

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.vengateshm.expensetracker.data.local.room.dto.ExpenseCategoryDto
import com.android.vengateshm.expensetracker.domain.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository
) : ViewModel() {
    fun addExpenseCategory(categoryName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            expenseRepository.addExpenseCategory(
                ExpenseCategoryDto(
                    categoryName = categoryName
                )
            )
        }
    }
}