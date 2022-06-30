package com.mc.vengateshm.expensetracker.presentation.components

import android.app.DatePickerDialog
import android.content.Context
import java.util.*

fun DateChooser(
    context: Context,
    onDateChosen: (Calendar) -> Unit,
) {
    val now = Calendar.getInstance()
    val year = now.get(Calendar.YEAR)
    val month = now.get(Calendar.MONTH)
    val dayOfMonth = now.get(Calendar.DAY_OF_MONTH)
    DatePickerDialog(context, { _, yr, mth, dayOfMth ->
        val calendar = Calendar.getInstance()
        calendar.set(yr, mth, dayOfMth)
        onDateChosen(calendar)
    }, year, month, dayOfMonth)
        .show()
}