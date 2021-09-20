package com.android.vengateshm.expensetracker.presentation.expenseList

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.vengateshm.expensetracker.data.local.room.dto.toExpenseWithCategory
import com.android.vengateshm.expensetracker.domain.model.ExpenseWithCategory
import com.android.vengateshm.expensetracker.domain.model.toExpenseDto
import com.android.vengateshm.expensetracker.domain.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseListViewModel @Inject constructor(
    private val repository: ExpenseRepository,
) : ViewModel() {
    private var _expenseListState = mutableStateOf(ExpenseListState())
    val expenseListState: State<ExpenseListState> = _expenseListState

    init {
        getAllExpenses()
    }

    private fun getAllExpenses() {
        _expenseListState.value = ExpenseListState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllExpenses()
                .catch { throwable ->
                    _expenseListState.value = ExpenseListState(error = throwable.message
                        ?: "Error occurred while retrieving expenses")
                }
                .collect { expenseWithCategoryDtoList ->
                    _expenseListState.value = ExpenseListState(
                        expenseList = expenseWithCategoryDtoList.map { expenseWithCategoryDto ->
                            expenseWithCategoryDto.toExpenseWithCategory()
                        }
                    )
                }
        }
    }

    fun deleteExpense(expenseWithCategory: ExpenseWithCategory) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteExpense(expenseWithCategory.toExpenseDto())
        }
    }
}