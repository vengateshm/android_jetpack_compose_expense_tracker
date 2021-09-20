package com.android.vengateshm.expensetracker.presentation.more

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.android.vengateshm.expensetracker.presentation.more.components.MoreScreenItem

@Composable
fun MoreScreen(navController: NavController) {
    LazyColumn {
        item {
            MoreScreenItem(title = "Add Category", description = "Adds new expense category")
            Divider()
        }
    }
}