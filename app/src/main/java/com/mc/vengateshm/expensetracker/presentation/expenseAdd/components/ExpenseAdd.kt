package com.mc.vengateshm.expensetracker.presentation.expenseAdd.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mc.vengateshm.expensetracker.R
import com.mc.vengateshm.expensetracker.domain.model.ExpenseCategory
import com.mc.vengateshm.expensetracker.domain.model.PaymentType

@Composable
fun ExpenseAdd(
    categoryList: List<ExpenseCategory>,
    categoryListDropdownExpanded: Boolean,
    onCategoryListDropDownDisMissRequest: (Boolean) -> Unit,
    onExpenseCategoryDropDownButtonClicked: (Boolean) -> Unit,
    onExpenseCategorySelected: (ExpenseCategory, Int) -> Unit,
    selectedExpenseCategoryName: String,
    paymentTypeList: List<PaymentType>,
    paymentTypeListDropdownExpanded: Boolean,
    onPaymentTypeDropDownButtonClicked: (Boolean) -> Unit,
    onPaymentTypeSelected: (PaymentType, Int) -> Unit,
    onPaymentTypeListDropDownDisMissRequest: (Boolean) -> Unit,
    selectedPaymentTypeName: String,
    description: String,
    onDescriptionChanged: (String) -> Unit,
    onDescriptionKeyboardNextAction: () -> Unit,
    amount: String,
    onAmountChanged: (String) -> Unit,
    onAmountKeyboardDoneAction: () -> Unit,
    selectedDate: String,
    onSelectDateClicked: () -> Unit,
    onAddExpenseClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Box {
            OutlinedDropDownContainer(labelName = selectedExpenseCategoryName,
                isExpanded = categoryListDropdownExpanded,
                onDropDownClicked = {
                    onExpenseCategoryDropDownButtonClicked(it)
                })
            DropdownMenu(modifier = Modifier
                .fillMaxWidth(),
                expanded = categoryListDropdownExpanded,
                onDismissRequest = {
                    onCategoryListDropDownDisMissRequest(false)
                }) {
                categoryList.forEachIndexed { index, expenseCategory ->
                    DropdownMenuItem(onClick = {
                        onExpenseCategorySelected(expenseCategory, index)
                    }) {
                        Text(text = expenseCategory.categoryName)
                    }
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        Box {
            OutlinedDropDownContainer(labelName = selectedPaymentTypeName,
                isExpanded = paymentTypeListDropdownExpanded,
                onDropDownClicked = {
                    onPaymentTypeDropDownButtonClicked(it)
                })
            DropdownMenu(modifier = Modifier
                .fillMaxWidth(),
                expanded = paymentTypeListDropdownExpanded,
                onDismissRequest = {
                    onPaymentTypeListDropDownDisMissRequest(false)
                }) {
                paymentTypeList.forEachIndexed { index, paymentType ->
                    DropdownMenuItem(onClick = {
                        onPaymentTypeSelected(paymentType, index)
                    }) {
                        Text(text = paymentType.name)
                    }
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.description_label)) },
            value = description,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            onValueChange = {
                onDescriptionChanged(it)
            },
            keyboardActions = KeyboardActions(onDone = {
                onDescriptionKeyboardNextAction()
            })
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.amount_label)) },
            value = amount,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done),
            onValueChange = {
                onAmountChanged(it)
            },
            keyboardActions = KeyboardActions(onDone = {
                onAmountKeyboardDoneAction()
            })
        )

        Spacer(Modifier.height(8.dp))

        OutlinedContainerWithTrailingIcon(labelName = selectedDate,
            trailingIcon = Icons.Rounded.CalendarToday,
            onClicked = {
                onSelectDateClicked()
            }
        )

        Spacer(Modifier.height(32.dp))

        Button(modifier = Modifier
            .fillMaxWidth(), onClick = { onAddExpenseClicked() }) {
            Text(text = stringResource(id = R.string.add))
        }
    }
}