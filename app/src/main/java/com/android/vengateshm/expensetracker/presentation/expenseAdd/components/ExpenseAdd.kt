package com.android.vengateshm.expensetracker.presentation.expenseAdd.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.android.vengateshm.expensetracker.R
import com.android.vengateshm.expensetracker.domain.model.ExpenseCategory
import com.android.vengateshm.expensetracker.domain.model.PaymentType

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
    description: String, amount: String,
    onDescriptionChanged: (String) -> Unit,
    onAmountChanged: (String) -> Unit,
    selectedDate: String,
    onSelectDateClicked: () -> Unit,
    onAddExpenseClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Box {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = selectedExpenseCategoryName,
                onValueChange = {

                },
                enabled = true,
                trailingIcon = {
                    Icon(
                        imageVector = if (categoryListDropdownExpanded) Icons.Rounded.KeyboardArrowUp else
                            Icons.Rounded.KeyboardArrowDown,
                        contentDescription = null,
                        modifier = Modifier.clickable(
                            onClick = {
                                onExpenseCategoryDropDownButtonClicked(categoryListDropdownExpanded.not())
                            }),
                    )
                },
                readOnly = true
            )
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
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = selectedPaymentTypeName,
                onValueChange = {

                },
                enabled = true,
                trailingIcon = {
                    Icon(
                        imageVector = if (paymentTypeListDropdownExpanded) Icons.Rounded.KeyboardArrowUp else
                            Icons.Rounded.KeyboardArrowDown,
                        contentDescription = null,
                        modifier = Modifier.clickable(
                            onClick = {
                                onPaymentTypeDropDownButtonClicked(paymentTypeListDropdownExpanded.not())
                            }),
                    )
                },
                readOnly = true
            )
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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = {
                onDescriptionChanged(it)
            },
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(modifier = Modifier
            .fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.amount_label)) },
            value = amount,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = {
                onAmountChanged(it)
            })

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = selectedDate,
            onValueChange = {

            },
            enabled = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Rounded.CalendarToday,
                    contentDescription = null,
                    modifier = Modifier.clickable(
                        onClick = {
                            onSelectDateClicked()
                        }),
                )
            },
            readOnly = true
        )

        Spacer(Modifier.height(32.dp))

        Button(modifier = Modifier
            .fillMaxWidth(), onClick = { onAddExpenseClicked() }) {
            Text(text = stringResource(id = R.string.add))
        }
    }
}