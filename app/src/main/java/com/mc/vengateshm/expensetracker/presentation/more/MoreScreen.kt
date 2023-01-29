package com.mc.vengateshm.expensetracker.presentation.more

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mc.vengateshm.expensetracker.R
import com.mc.vengateshm.expensetracker.presentation.more.components.MoreScreenItem

@Composable
fun MoreScreen(
    navController: NavController,
    viewModel: MoreViewModel = hiltViewModel()
) {
    val addCategoryTitle = stringResource(id = R.string.add_category_label)
    val addCategoryDescription = stringResource(id = R.string.add_category_description)

    var showAddCategoryAlertDialog by remember { mutableStateOf(false) }

    LazyColumn {
        item {
            MoreScreenItem(title = addCategoryTitle, description = addCategoryDescription,
                onMoreItemClicked = {
                    when (it) {
                        addCategoryTitle -> {
                            showAddCategoryAlertDialog = true
                        }
                    }
                })
            Divider()
        }
    }

    if (showAddCategoryAlertDialog) {
        AddCategoryAlertState(
            onShowDialogStateChanged = {
                showAddCategoryAlertDialog = it
            },
            onAddCategory = {
                viewModel.addExpenseCategory(it)
                navController.popBackStack()
            }
        )
    }
}

@Composable
fun AddCategoryAlertState(
    onShowDialogStateChanged: (Boolean) -> Unit,
    onAddCategory: (String) -> Unit
) {
    var categoryName by remember { mutableStateOf("") }
    var isCategoryTextFieldError by remember { mutableStateOf(false) }
    AddCategoryAlert(
        title = stringResource(id = R.string.add_category_label),
        categoryName = categoryName,
        onCategoryValueChange = {
            categoryName = it
        },
        isCategoryTextFieldError = isCategoryTextFieldError,
        onCategoryErrorFieldError = {
            isCategoryTextFieldError = it
        },
        onShowDialogStateChanged = onShowDialogStateChanged,
        onValidationSuccess = {
            onAddCategory(categoryName)
        }
    )
}

@Composable
fun AddCategoryAlert(
    title: String,
    categoryName: String,
    onCategoryValueChange: (String) -> Unit,
    isCategoryTextFieldError: Boolean,
    onCategoryErrorFieldError: (Boolean) -> Unit,
    onShowDialogStateChanged: (Boolean) -> Unit,
    onValidationSuccess: () -> Unit
) {
    Dialog(
        onDismissRequest = { onShowDialogStateChanged(false) },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    color = Color.White
                )
                .padding(8.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = categoryName,
                onValueChange = {
                    onCategoryValueChange(it)
                },
                label = {
                    Text(text = stringResource(id = R.string.category))
                },
                isError = isCategoryTextFieldError
            )
            Text(
                text = if (isCategoryTextFieldError) "Category name should contain alphabets only" else "",
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier/*.padding(start = 16.dp*/
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                OutlinedButton(onClick = {
                    onShowDialogStateChanged(false)
                }) {
                    Text(text = stringResource(id = R.string.cancel))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    val regexPattern = """^[a-z A-Z]*${'$'}""".toRegex()
                    if (categoryName.isEmpty() || categoryName.matches(regex = regexPattern)
                            .not()
                    ) {
                        onCategoryErrorFieldError(true)
                    } else {
                        onValidationSuccess()
                        onShowDialogStateChanged(false)
                    }
                }) {
                    Text(text = stringResource(id = R.string.add))
                }
            }
        }
    }
}
