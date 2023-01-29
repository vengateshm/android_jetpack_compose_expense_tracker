package com.mc.vengateshm.expensetracker.presentation.chart

data class ChartScreenState(
    val isLoading: Boolean = false,
    val chartData: List<ChartData> = emptyList(),
    val error: String = "",
)
