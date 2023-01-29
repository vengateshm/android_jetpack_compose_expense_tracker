package com.mc.vengateshm.expensetracker.presentation.chart

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.tehras.charts.piechart.PieChartData
import com.mc.vengateshm.expensetracker.common.random
import com.mc.vengateshm.expensetracker.data.local.room.dto.toExpenseWithCategory
import com.mc.vengateshm.expensetracker.domain.model.ExpenseCategory
import com.mc.vengateshm.expensetracker.domain.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val repository: ExpenseRepository
) : ViewModel() {

    var uiState by mutableStateOf(ChartScreenState())

    fun getExpenses() {
        uiState = uiState.copy(isLoading = true)

        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllExpenses()
                .catch { throwable ->
                    uiState = uiState.copy(
                        isLoading = false,
                        error = throwable.message
                            ?: "Error occurred while retrieving expenses"
                    )
                }
                .collect { expenseWithCategoryDtoList ->
                    val expenseWithCategory =
                        expenseWithCategoryDtoList.map { expenseWithCategoryDto ->
                            expenseWithCategoryDto.toExpenseWithCategory()
                        }
                    val result = expenseWithCategory
                        .groupBy { it.categoryName }
                        .mapValues { it.value.sumOf { it.amount } }
                        .map {
                            ChartData(it.key, PieChartData.Slice(it.value.toFloat(), Color.random()))
                        }
                    uiState = uiState.copy(isLoading = false, chartData = result)
                }
        }
    }
}