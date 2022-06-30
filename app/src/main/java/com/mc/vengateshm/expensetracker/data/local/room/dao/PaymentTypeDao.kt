package com.mc.vengateshm.expensetracker.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mc.vengateshm.expensetracker.data.local.room.dto.PaymentTypeDto

@Dao
interface PaymentTypeDao {
    @Insert
    fun insertAll(paymentTypeDto: List<PaymentTypeDto>)

    @Query("SELECT * FROM payment_type")
    suspend fun getAllPaymentTypes(): List<PaymentTypeDto>
}