package com.android.vengateshm.expensetracker.presentation.expenseList

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Sort
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.vengateshm.expensetracker.R
import com.android.vengateshm.expensetracker.presentation.Screen
import com.android.vengateshm.expensetracker.presentation.expenseList.components.ExpenseListItem
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@ExperimentalSerializationApi
@Composable
fun ExpenseListScreen(
    navController: NavController,
    viewModel: ExpenseListViewModel = hiltViewModel(),
) {
    val state = viewModel.expenseListState.value

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            state.error.isNotBlank() -> {
                Text(
                    text = state.error,
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .align(Alignment.Center)
                )
            }
            else -> {
                Column {
                    Row(modifier = Modifier.padding(16.dp)) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = buildAnnotatedString {
                                append("Total Expense")
                                append("\n")
                                append(
                                    AnnotatedString(
                                        text = "\u20B9${state.expenseList.sumOf { it.amount }}",
                                        spanStyle = SpanStyle(fontWeight = FontWeight.Bold)
                                    )
                                )
                            })
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Rounded.Sort, contentDescription = null)
                        }
                    }
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(state.expenseList) { expenseListItem ->
                            ExpenseListItem(expenseWithCategory = expenseListItem,
                                onDeleteClicked = {
                                    viewModel.deleteExpense(it)
                                },
                                onItemClicked = {
                                    navController.navigate(
                                        Screen.ExpenseDetailDialog.route + "/${
                                            Json.encodeToString(
                                                expenseListItem
                                            )
                                        }"
                                    )
                                })
                            Divider()
                        }
                    }
                }
                ExtendedFloatingActionButton(modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(8.dp),
                    icon = {
                        Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
                    },
                    text = {
                        Text(text = stringResource(id = R.string.add_expense))
                    },
                    onClick = {
                        navController.navigate(Screen.ExpenseAdd.route)
                    })
            }
        }
    }
}