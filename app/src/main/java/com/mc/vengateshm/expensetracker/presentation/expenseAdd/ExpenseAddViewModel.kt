package com.mc.vengateshm.expensetracker.presentation.expenseAdd

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mc.vengateshm.expensetracker.R
import com.mc.vengateshm.expensetracker.data.local.room.dto.ExpenseDto
import com.mc.vengateshm.expensetracker.data.local.room.dto.toExpenseCategory
import com.mc.vengateshm.expensetracker.data.local.room.dto.toPaymentType
import com.mc.vengateshm.expensetracker.domain.model.ExpenseCategory
import com.mc.vengateshm.expensetracker.domain.model.PaymentType
import com.mc.vengateshm.expensetracker.domain.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.Serializable
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ExpenseAddViewModel @Inject constructor(
    private val repository: ExpenseRepository,
) : ViewModel() {

    private val _expenseAddState = mutableStateOf(ExpenseAddState())
    val expenseAddState: State<ExpenseAddState> = _expenseAddState

    init {
        getExpenseAddData()
    }

    private fun getExpenseAddData() {
        _expenseAddState.value = ExpenseAddState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val expenseAddState = try {
                val categoryListDto = repository.getAllCategories()
                val paymentTypeListDto = repository.getAllPaymentType()
                if (categoryListDto.isNullOrEmpty() || paymentTypeListDto.isNullOrEmpty()) {
                    ExpenseAddState(error = "Error retrieving expense category or payment type")
                } else {
                    ExpenseAddState(expenseAddData = ExpenseAddData(
                        categoryList = categoryListDto.map { it.toExpenseCategory() },
                        paymentTypeList = paymentTypeListDto.map { it.toPaymentType() }
                    ))
                }
            } catch (e: Exception) {
                ExpenseAddState(
                    error = e.message
                        ?: "Error retrieving expense category or payment type"
                )
            }
            withContext(Dispatchers.Main) {
                _expenseAddState.value = expenseAddState
            }
        }
    }

    fun addExpense(
        selectedCategory: ExpenseCategory,
        selectedPaymentType: PaymentType,
        description: String,
        amount: String,
        selectedExpenseDate: Calendar,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addExpense(
                ExpenseDto(
                    description = description,
                    amount = amount.toDouble(),
                    createdAt = selectedExpenseDate,
                    categoryId = selectedCategory.categoryId!!,
                    paymentTypeId = selectedPaymentType.paymentId!!
                )
            )
        }
    }

    fun getExpenseValidatedResult(
        selectedCategory: ExpenseCategory?,
        selectedPaymentType: PaymentType?,
        description: String,
        amount: String,
        selectedExpenseDate: Calendar?,
    ): ValidExpenseResult {
        return when {
            selectedCategory == null -> {
                ValidExpenseResult(false, R.string.err_please_select_category)
            }
            selectedPaymentType == null -> {
                ValidExpenseResult(false, R.string.err_please_select_payment_type)
            }
            description.isEmpty() -> {
                ValidExpenseResult(false, R.string.err_please_enter_description)
            }
            amount.isEmpty() || amount.toDoubleOrNull() == null -> {
                ValidExpenseResult(false, R.string.err_please_enter_amount)
            }
            amount.toDouble() <= 0 -> {
                ValidExpenseResult(false, R.string.err_amount_should_not_be_zero)
            }
            selectedExpenseDate == null -> {
                ValidExpenseResult(false, R.string.err_please_select_date)
            }
            else -> {
                ValidExpenseResult(true, null)
            }
        }
    }
}

data class ValidExpenseResult(
    val isValid: Boolean,
    val errStringRes: Int?,
) : Serializable {
    override fun toString(): String = "($isValid, $errStringRes)"
}