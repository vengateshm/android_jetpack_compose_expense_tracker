package com.android.vengateshm.expensetracker.domain.model

import java.util.*

data class Expense(
    val expenseId: Long? = null,
    val description: String,
    val amount: Double,
    val createdAt: Date?,
    val categoryId: Long,
)