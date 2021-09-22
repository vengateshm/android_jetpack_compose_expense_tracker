package com.android.vengateshm.expensetracker.presentation.expenseDetail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.vengateshm.expensetracker.common.PARAM_EXPENSE_ID
import com.android.vengateshm.expensetracker.data.local.room.dto.toExpenseWithCategory
import com.android.vengateshm.expensetracker.domain.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ExpenseDetailViewModel @Inject constructor(
    private val repository: ExpenseRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private var _expenseDetailState = mutableStateOf(ExpenseDetailState())
    val expenseDetailState: State<ExpenseDetailState> = _expenseDetailState

    init {
        savedStateHandle.get<String>(PARAM_EXPENSE_ID)?.let { expenseId ->
            getExpenseById(expenseId)
        }
    }

    private fun getExpenseById(expenseId: String) {
        _expenseDetailState.value = ExpenseDetailState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000) // Remove in production

            val expenseDetailState = try {
                val expenseWithCategory = repository.getExpenseById(expenseId.toLong())
                ExpenseDetailState(expenseWithCategory = expenseWithCategory.toExpenseWithCategory())
            } catch (e: Exception) {
                ExpenseDetailState(
                    error = e.message
                        ?: "Error retrieving expense details"
                )
            }
            withContext(Dispatchers.Main) {
                _expenseDetailState.value = expenseDetailState
            }
        }
    }
}