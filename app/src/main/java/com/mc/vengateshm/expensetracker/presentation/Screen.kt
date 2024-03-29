package com.mc.vengateshm.expensetracker.presentation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material.icons.rounded.PieChart
import androidx.compose.ui.graphics.vector.ImageVector
import com.mc.vengateshm.expensetracker.R

sealed class Screen(val route: String, @StringRes val labelResourceId: Int, val icon: ImageVector) {
    object ExpenseList : Screen("expense_list", R.string.expenses_label, Icons.Rounded.List)
    object ExpenseDetailDialog :
        Screen("expense_detail_dialog", R.string.expenses_label, Icons.Rounded.List)

    object ExpenseDetail : Screen("expense_detail", R.string.expenses_label, Icons.Rounded.List)
    object ExpenseAdd : Screen("expense_add", R.string.expenses_add_label, Icons.Rounded.Add)
    object More : Screen("more", R.string.more, Icons.Rounded.MoreHoriz)
    object Chart : Screen("chart", R.string.chart, Icons.Rounded.PieChart)
}

fun String.toToolbarLabelResId() = when {
    this == Screen.ExpenseList.route -> Screen.ExpenseList.labelResourceId
    this == Screen.More.route -> Screen.More.labelResourceId
    this == Screen.ExpenseAdd.route -> Screen.ExpenseAdd.labelResourceId
    else -> R.string.app_name
}