package com.android.vengateshm.expensetracker.presentation.expenseAdd

import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.vengateshm.expensetracker.R
import com.android.vengateshm.expensetracker.common.EXPENSE_DATE_FORMAT
import com.android.vengateshm.expensetracker.common.exts.toFormattedDate
import com.android.vengateshm.expensetracker.domain.model.ExpenseCategory
import com.android.vengateshm.expensetracker.domain.model.PaymentType
import com.android.vengateshm.expensetracker.presentation.components.DateChooser
import com.android.vengateshm.expensetracker.presentation.expenseAdd.components.ExpenseAdd
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun ExpenseAddScreen(
    navController: NavController,
    viewModel: ExpenseAddViewModel = hiltViewModel(),
) {
    val context = LocalContext.current as ComponentActivity
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val state = viewModel.expenseAddState.value
    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var categoryListDropdownExpanded by remember { mutableStateOf(false) }
    var paymentTypeListDropdownExpanded by remember { mutableStateOf(false) }
    var selectedExpenseCategoryName by remember { mutableStateOf("Select Category") }
    var selectedPaymentTypeName by remember { mutableStateOf("Select Payment Type") }
    var selectedDate by remember { mutableStateOf("Select Date") }

    var selectedCategory: ExpenseCategory? = null
    var selectedPaymentType: PaymentType? = null
    var selectedExpenseDate: Calendar? = null

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.add_expense))
                },
                navigationIcon = {
                    Icon(imageVector = Icons.Rounded.ArrowBackIosNew, contentDescription = null,
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        })
                }
            )
        },
    ) {
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
            onAmountChanged = {
                amount = it
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
                val result = viewModel.getExpenseValidatedResult(selectedCategory,
                    selectedPaymentType,
                    description,
                    amount,
                    selectedExpenseDate)
                if (result.isValid) {
                    viewModel.addExpense(selectedCategory!!, selectedPaymentType!!, description,
                        amount, selectedExpenseDate!!)
                    navController.popBackStack()
                } else {
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = context.getString(result.errStringRes!!),
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            })
    }
}