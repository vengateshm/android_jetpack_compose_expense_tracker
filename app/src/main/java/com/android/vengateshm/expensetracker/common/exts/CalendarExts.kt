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