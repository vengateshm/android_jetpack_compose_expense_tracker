package com.mc.vengateshm.expensetracker.presentation.chart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.tehras.charts.piechart.PieChart
import com.github.tehras.charts.piechart.PieChartData
import com.github.tehras.charts.piechart.animation.simpleChartAnimation
import com.github.tehras.charts.piechart.renderer.SimpleSliceDrawer

data class ChartData(
    val categoryName: String,
    val slice: PieChartData.Slice
)

@Composable
fun ChartScreen(
    viewModel: ChartViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.getExpenses()
    }

    val state = viewModel.uiState

    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.padding(4.dp),
                text = "Total expenses by category"
            )
            Spacer(modifier = Modifier.height(8.dp))
            PieChart(
                modifier = Modifier
                    .size(150.dp),
                pieChartData = PieChartData(state.chartData.map { it.slice }),
                animation = simpleChartAnimation(),
                sliceDrawer = SimpleSliceDrawer()
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                items(state.chartData) { chartData ->
                    Row {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .background(
                                    color = chartData.slice.color,
                                    shape = RoundedCornerShape(8.dp)
                                )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            modifier = Modifier.padding(4.dp),
                            text = "${chartData.categoryName} : Rs. ${chartData.slice.value}"
                        )
                    }
                }
            }
        }
    }
}