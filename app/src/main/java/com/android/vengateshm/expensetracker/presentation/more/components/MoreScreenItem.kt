package com.android.vengateshm.expensetracker.presentation.more.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MoreScreenItem(title: String, description: String) {
    Row(modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title,
                style = MaterialTheme.typography.body1)
            Text(text = description,
                style = MaterialTheme.typography.caption)
        }
        Icon(imageVector = Icons.Rounded.ArrowForwardIos, contentDescription = null)
    }
}