package com.android.vengateshm.expensetracker.presentation.expenseList.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.android.vengateshm.expensetracker.common.EXPENSE_DATE_FORMAT
import com.android.vengateshm.expensetracker.common.exts.toFormattedDate
import com.android.vengateshm.expensetracker.domain.model.ExpenseWithCategory

@Composable
fun ExpenseListItem(
    expenseWithCategory: ExpenseWithCategory,
    onDeleteClicked: ((ExpenseWithCategory) -> Unit)? = null,
    onItemClicked: ((ExpenseWithCategory) -> Unit)? = null
) {
    Row(modifier = Modifier.padding(16.dp)) {
        Column(modifier = Modifier
            .weight(1f)
            .clickable {
                onItemClicked?.invoke(expenseWithCategory)
            }) {
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
            expenseWithCategory.createdAt?.let { timeInMillis ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = timeInMillis.toFormattedDate(EXPENSE_DATE_FORMAT),
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
        Icon(modifier = Modifier.clickable {
            onDeleteClicked?.invoke(expenseWithCategory)
        }, imageVector = Icons.Rounded.DeleteOutline, contentDescription = null)
    }
}