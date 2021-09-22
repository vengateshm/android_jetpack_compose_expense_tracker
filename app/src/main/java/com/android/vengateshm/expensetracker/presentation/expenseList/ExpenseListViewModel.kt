package com.android.vengateshm.expensetracker.presentation.expenseList

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.vengateshm.expensetracker.data.local.room.dto.toExpenseCategory
import com.android.vengateshm.expensetracker.data.local.room.dto.toExpenseWithCategory
import com.android.vengateshm.expensetracker.data.local.room.prepopulateData.defaultExpenseCategoryDtoTypeList
import com.android.vengateshm.expensetracker.domain.model.ExpenseCategory
import com.android.vengateshm.expensetracker.domain.model.ExpenseWithCategory
import com.android.vengateshm.expensetracker.domain.model.toExpenseDto
import com.android.vengateshm.expensetracker.domain.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ExpenseListViewModel @Inject constructor(
    private val repository: ExpenseRepository
) : ViewModel() {

    private var _expenseListState = mutableStateOf(ExpenseListState())
    val expenseListState: State<ExpenseListState> = _expenseListState

    private var _categoryList =
        mutableStateOf(defaultExpenseCategoryDtoTypeList.map { it.toExpenseCategory() })
    val categoryList: State<List<ExpenseCategory>> = _categoryList

    private var _sortMode = mutableStateOf(UiEvent.SortMode(isOn = false))
    val sortMode: State<UiEvent.SortMode> = _sortMode

    private var _clickedExpenseCategory =
        mutableStateOf(UiEvent.ExpenseCategorySelectionForSort(null))
    val clickedExpenseCategory: State<UiEvent.ExpenseCategorySelectionForSort> =
        _clickedExpenseCategory

    private var _clickedExpenseCategoryIndex = mutableStateOf(0)
    val clickedExpenseCategoryIndex: State<Int> = _clickedExpenseCategoryIndex

    var getExpensesJob: Job? = null

    init {
        getAllExpenseCategories()
        getExpenses()
    }

    private fun getAllExpenseCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.consumeAllCategories()
                .collect { categoryListDto ->
                    withContext(Dispatchers.Main) {
                        _categoryList.value = categoryListDto.map { it.toExpenseCategory() }
                    }
                }
        }
    }

    private fun getExpenses(expenseCategory: ExpenseCategory? = null) {
        _expenseListState.value = ExpenseListState(isLoading = true)
        getExpensesJob = viewModelScope.launch(Dispatchers.IO) {
            val expensesFlow = expenseCategory?.let {
                repository.getExpensesByCategory(it)
            } ?: repository.getAllExpenses()

            expensesFlow
                .catch { throwable ->
                    _expenseListState.value = ExpenseListState(
                        error = throwable.message
                            ?: "Error occurred while retrieving expenses"
                    )
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

    fun onEvent(uiEvent: UiEvent) {
        when (uiEvent) {
            is UiEvent.SortMode -> {
                _sortMode.value = UiEvent.SortMode(isOn = uiEvent.isOn.not())
                if (sortMode.value.isOn.not()) {
                    if (_clickedExpenseCategory.value.expenseCategory != null) {
                        getExpensesJob?.cancel()
                        getExpenses()
                    }
                    _clickedExpenseCategory.value = UiEvent.ExpenseCategorySelectionForSort(null)
                }
            }
            is UiEvent.ExpenseCategorySelectionForSort -> {
                _clickedExpenseCategoryIndex.value = uiEvent.position
                if (_clickedExpenseCategory.value.expenseCategory?.categoryId != uiEvent.expenseCategory?.categoryId) {
                    getExpensesJob?.cancel()
                    getExpenses(uiEvent.expenseCategory)
                }
                _clickedExpenseCategory.value = UiEvent.ExpenseCategorySelectionForSort(
                    expenseCategory = uiEvent.expenseCategory
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        getExpensesJob?.cancel()
    }
}