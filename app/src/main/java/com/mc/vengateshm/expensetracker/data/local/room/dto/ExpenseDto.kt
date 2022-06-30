package com.mc.vengateshm.expensetracker.data.local.room.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "expense",
    foreignKeys = [ForeignKey(
        entity = ExpenseCategoryDto::class,
        parentColumns = ["category_id"],
        childColumns = ["expense_category_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class ExpenseDto(
    @ColumnInfo(name = "expense_id")
    @PrimaryKey(autoGenerate = true)
    val expenseId: Long? = null,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "amount")
    val amount: Double,
    @ColumnInfo(name = "created_at")
    val createdAt: Calendar?,
    @ColumnInfo(name = "expense_category_id")
    val categoryId: Long,
    @ColumnInfo(name = "expense_payment_type_id")
    val paymentTypeId: Long,
)
