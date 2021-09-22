package com.android.vengateshm.expensetracker.common.exts

import java.text.SimpleDateFormat
import java.util.*

fun Calendar.toFormattedDate(pattern: String): String {
    return try {
        SimpleDateFormat(pattern, Locale.getDefault()).format(this.time)
    } catch (e: Exception) {
        "NA"
    }
}

fun Long.toFormattedDate(pattern: String): String {
    return try {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = this
        SimpleDateFormat(pattern, Locale.getDefault()).format(calendar.time)
    } catch (e: Exception) {
        "NA"
    }
}