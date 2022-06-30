package com.mc.vengateshm.expensetracker.presentation.expenseAdd

import com.mc.vengateshm.expensetracker.domain.model.ExpenseCategory
import com.mc.vengateshm.expensetracker.domain.model.PaymentType

data class ExpenseAddState(
    val isLoading: Boolean = false,
    val expenseAddData: ExpenseAddData = ExpenseAddData(),
    val error: String = "",
)

data class ExpenseAddData(
    val categoryList: List<ExpenseCategory> = emptyList(),
    val paymentTypeList: List<PaymentType> = emptyList(),
)
