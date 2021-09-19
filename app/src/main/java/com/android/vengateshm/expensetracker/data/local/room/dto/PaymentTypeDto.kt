package com.android.vengateshm.expensetracker.data.local.room.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "payment_type")
data class PaymentTypeDto(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "payment_id")
    val paymentId: Long? = null,
    @ColumnInfo(name = "name")
    val name: String,
)
