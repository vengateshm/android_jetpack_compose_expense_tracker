package com.mc.vengateshm.expensetracker.data.local.room.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mc.vengateshm.expensetracker.domain.model.PaymentType

@Entity(tableName = "payment_type")
data class PaymentTypeDto(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "payment_type_id")
    val paymentId: Long? = null,
    @ColumnInfo(name = "name")
    val name: String,
)

fun PaymentTypeDto.toPaymentType() = PaymentType(
    name = this.name,
    paymentId = this.paymentId
)