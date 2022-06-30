package com.mc.vengateshm.expensetracker.domain.model

import com.mc.vengateshm.expensetracker.data.local.room.dto.ExpenseDto

@kotlinx.serialization.Serializable
data class ExpenseWithCategory(
    val expenseId: Long? = null,
    val description: String,
    val amount: Double,
    val categoryId: Long? = null,
    val categoryName: String,
    val paymentTypeId: Long? = null,
    val paymentTypeName: String,
    val createdAt: Long? = null
)

fun ExpenseWithCategory.toExpenseDto() = ExpenseDto(
    expenseId = this.expenseId,
    description = this.description,
    amount = this.amount,
    createdAt = null,
    categoryId = this.categoryId!!,
    paymentTypeId = this.paymentTypeId!!
)