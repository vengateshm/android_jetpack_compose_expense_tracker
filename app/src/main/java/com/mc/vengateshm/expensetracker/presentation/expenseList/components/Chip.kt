package com.mc.vengateshm.expensetracker.presentation.expenseList.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mc.vengateshm.expensetracker.ui.theme.Purple200

interface ChipLabel {
    var chipLabel: String
}

@Composable
fun <T : ChipLabel> Chip(value: T, isSelected: Boolean, onChipClicked: (T) -> Unit) {
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = if (isSelected) Purple200 else Color.Gray,
                shape = RoundedCornerShape(50.dp)
            )
            .padding(start = 8.dp, end = 8.dp)
            .clickable {
                onChipClicked(value)
            }
    ) {
        Text(
            text = value.chipLabel,
            color = if (isSelected) Purple200 else Color.Gray,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body2
        )
    }
}