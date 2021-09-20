package com.android.vengateshm.expensetracker.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.android.vengateshm.expensetracker.data.local.room.dto.PaymentTypeDto
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentTypeDao {
    @Insert
    fun insertAll(paymentTypeDto: List<PaymentTypeDto>)

    @Query("SELECT * FROM payment_type")
    fun getAllPaymentTypes(): List<PaymentTypeDto>
}