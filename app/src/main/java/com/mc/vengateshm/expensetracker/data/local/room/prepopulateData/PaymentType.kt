package com.mc.vengateshm.expensetracker.data.local.room.prepopulateData

import com.mc.vengateshm.expensetracker.data.local.room.dto.PaymentTypeDto

val defaultPaymentDtoTypeList = listOf(
    PaymentTypeDto(name = "Cash"),
    PaymentTypeDto(name = "Debit Card"),
    PaymentTypeDto(name = "Credit Card"),
    PaymentTypeDto(name = "UPI"),
    PaymentTypeDto(name = "Net Banking"),
)