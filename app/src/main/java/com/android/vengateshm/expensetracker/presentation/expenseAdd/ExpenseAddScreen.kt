package com.android.vengateshm.expensetracker.presentation.expenseAdd

import androidx.activity.ComponentActivity
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.vengateshm.expensetracker.common.EXPENSE_DATE_FORMAT
import com.android.vengateshm.expensetracker.common.exts.toFormattedDate
import com.android.vengateshm.expensetracker.domain.model.ExpenseCategory
import com.android.vengateshm.expensetracker.domain.model.PaymentType
import com.android.vengateshm.expensetracker.presentation.components.DateChooser
import com.android.vengateshm.expensetracker.presentation.expenseAdd.components.ExpenseAdd
import java.util.*

@ExperimentalComposeUiApi
@Composable
fun ExpenseAddScreen(
    navController: NavController,
    viewModel: ExpenseAddViewModel = hiltViewModel(),
    onShowSnackBar: (Int) -> Unit
) {
    val context = LocalContext.current as ComponentActivity

    val state = viewModel.expenseAddState.value
    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var categoryListDropdownExpanded by remember { mutableStateOf(false) }
    var paymentTypeListDropdownExpanded by remember { mutableStateOf(false) }
    var selectedExpenseCategoryName by remember { mutableStateOf("Select Category") }
    var selectedPaymentTypeName by remember { mutableStateOf("Select Payment Type") }
    var selectedDate by remember { mutableStateOf("Select Date") }

    var selectedCategory by remember { mutableStateOf<ExpenseCategory?>(null) }
    var selectedPaymentType by remember { mutableStateOf<PaymentType?>(null) }
    var selectedExpenseDate by remember { mutableStateOf<Calendar?>(null) }

    val keyboardController = LocalSoftwareKeyboardController.current

    ExpenseAdd(
        categoryList = state.expenseAddData.categoryList,
        categoryListDropdownExpanded = categoryListDropdownExpanded,
        onCategoryListDropDownDisMissRequest = {
            categoryListDropdownExpanded = it
        },
        onExpenseCategoryDropDownButtonClicked = {
            categoryListDropdownExpanded = it
        },
        onExpenseCategorySelected = { selectedExpenseCategory, selectedIndex ->
            selectedCategory = selectedExpenseCategory
            selectedExpenseCategoryName = selectedExpenseCategory.categoryName
            categoryListDropdownExpanded = false // Dismiss after selection
        },
        selectedExpenseCategoryName = selectedExpenseCategoryName,
        paymentTypeList = state.expenseAddData.paymentTypeList,
        paymentTypeListDropdownExpanded = paymentTypeListDropdownExpanded,
        onPaymentTypeListDropDownDisMissRequest = {
            paymentTypeListDropdownExpanded = it
        },
        onPaymentTypeDropDownButtonClicked = {
            paymentTypeListDropdownExpanded = it
        },
        onPaymentTypeSelected = { selectedExpensePaymentType, selectedIndex ->
            selectedPaymentType = selectedExpensePaymentType
            selectedPaymentTypeName = selectedExpensePaymentType.name
            paymentTypeListDropdownExpanded = false // Dismiss after selection
        },
        selectedPaymentTypeName = selectedPaymentTypeName,
        description = description,
        amount = amount,
        onDescriptionChanged = {
            description = it
        },
        onDescriptionKeyboardNextAction = {
            keyboardController?.hide()
        },
        onAmountChanged = {
            amount = it
        },
        onAmountKeyboardDoneAction = {
            keyboardController?.hide()
        },
        selectedDate = selectedDate,
        onSelectDateClicked = {
            DateChooser(context = context,
                onDateChosen = {
                    selectedDate = it.toFormattedDate(EXPENSE_DATE_FORMAT)
                    selectedExpenseDate = it
                })
        },
        onAddExpenseClicked = {
            val result = viewModel.getExpenseValidatedResult(
                selectedCategory,
                selectedPaymentType,
                description,
                amount,
                selectedExpenseDate
            )
            if (result.isValid) {
                viewModel.addExpense(
                    selectedCategory!!, selectedPaymentType!!, description,
                    amount, selectedExpenseDate!!
                )
                navController.popBackStack()
            } else {
                onShowSnackBar(result.errStringRes!!)
            }
        }
    )
}