package com.mc.vengateshm.expensetracker.presentation.expenseDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mc.vengateshm.expensetracker.domain.model.ExpenseWithCategory

@Composable
fun ExpenseDetailScreen(
    viewModel: ExpenseDetailViewModel = hiltViewModel()
) {
    val state = viewModel.expenseDetailState.value

    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .align(Alignment.Center)
            )
        }
        state.expenseWithCategory?.let { ExpenseDetailContent(expenseWithCategory = it) }
    }
}

@Composable
fun ExpenseDetailContent(expenseWithCategory: ExpenseWithCategory) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.White
            )
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color.Red,
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(start = 8.dp, end = 8.dp)
        ) {
            Text(
                text = expenseWithCategory.categoryName,
                color = Color.Red,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body2
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = expenseWithCategory.description,
            style = MaterialTheme.typography.body1
        )
        Text(
            text = "\u20B9 ${expenseWithCategory.amount}",
            style = MaterialTheme.typography.body2
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = buildAnnotatedString {
            append(text = "Paid by ")
            append(
                AnnotatedString(
                    text = expenseWithCategory.paymentTypeName.uppercase(),
                    spanStyle = SpanStyle(fontWeight = FontWeight.Bold)
                )
            )
        })
    }
}