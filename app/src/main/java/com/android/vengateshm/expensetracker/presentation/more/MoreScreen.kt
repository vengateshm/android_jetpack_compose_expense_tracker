package com.android.vengateshm.expensetracker.presentation.more

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.vengateshm.expensetracker.R
import com.android.vengateshm.expensetracker.presentation.more.components.MoreScreenItem

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
    AlertDialog(
        onDismissRequest = {
            onShowDialogStateChanged(false)
        },
        title = {
            Text(text = title)
        },
        text = {
            OutlinedTextField(
                value = categoryName,
                onValueChange = {
                    onCategoryValueChange(it)
                },
                label = {
                    Text(text = stringResource(id = R.string.category))
                },
                isError = isCategoryTextFieldError
            )
        },
        confirmButton = {
            Button(onClick = {
                val regexPattern = """^[a-zA-Z]*${'$'}""".toRegex()
                if (categoryName.isEmpty() || categoryName.matches(regex = regexPattern).not()) {
                    onCategoryErrorFieldError(true)
                } else {
                    onValidationSuccess()
                    onShowDialogStateChanged(false)
                }
            }) {
                Text(text = stringResource(id = R.string.add))
            }
        },
        dismissButton = {
            Button(onClick = {
                onShowDialogStateChanged(false)
            }) {
                Text(text = stringResource(id = R.string.close))
            }
        }
    )
}
