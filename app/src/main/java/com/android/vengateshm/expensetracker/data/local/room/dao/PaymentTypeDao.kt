package com.android.vengateshm.expensetracker.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import com.android.vengateshm.expensetracker.data.local.room.dto.PaymentTypeDto

@Dao
interface PaymentTypeDao {
    @Insert
    fun insertAll(paymentTypeDto: List<PaymentTypeDto>)
}