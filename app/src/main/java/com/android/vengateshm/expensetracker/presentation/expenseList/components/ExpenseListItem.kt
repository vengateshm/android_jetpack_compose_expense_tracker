package com.android.vengateshm.expensetracker.presentation.expenseList.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.vengateshm.expensetracker.domain.model.ExpenseWithCategory

@Composable
fun ExpenseListItem(
    expenseWithCategory: ExpenseWithCategory,
    onDeleteClicked: (ExpenseWithCategory) -> Unit,
) {
    Row(modifier = Modifier.padding(16.dp)) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = expenseWithCategory.description,
                style = MaterialTheme.typography.body1)
            Text(text = "\u20B9 ${expenseWithCategory.amount}",
                style = MaterialTheme.typography.body2)
        }
        Icon(modifier = Modifier.clickable {
            onDeleteClicked(expenseWithCategory)
        }, imageVector = Icons.Rounded.DeleteOutline, contentDescription = null)
    }
}