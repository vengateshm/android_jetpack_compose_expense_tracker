package com.mc.vengateshm.expensetracker.presentation.expenseList

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.PieChart
import androidx.compose.material.icons.rounded.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mc.vengateshm.expensetracker.R
import com.mc.vengateshm.expensetracker.presentation.Screen
import com.mc.vengateshm.expensetracker.presentation.expenseList.components.Chip
import com.mc.vengateshm.expensetracker.presentation.expenseList.components.ExpenseListItem
import com.mc.vengateshm.expensetracker.ui.theme.Purple200
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@ExperimentalAnimationApi
@ExperimentalSerializationApi
@Composable
fun ExpenseListScreen(
    navController: NavController,
    viewModel: ExpenseListViewModel = hiltViewModel(),
) {
    val state = viewModel.expenseListState.value
    val sortMode = viewModel.sortMode.value
    val categoryList = viewModel.categoryList.value
    val categoryLazyRowState = rememberLazyListState()
    val clickedCategory = viewModel.clickedExpenseCategory.value
    val clickedCategoryIndex = viewModel.clickedExpenseCategoryIndex.value

    LaunchedEffect(key1 = Unit) {
        with(viewModel) {
            getAllExpenseCategories()
            getExpenses()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .align(Alignment.Center)
            )
        } else {
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
                    IconButton(onClick = {
                        viewModel.onEvent(UiEvent.SortMode(sortMode.isOn))
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.Sort,
                            tint = if (sortMode.isOn) Purple200 else Color.Gray,
                            contentDescription = null
                        )
                    }
                    IconButton(onClick = {
                        navController.navigate(Screen.Chart.route)
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.PieChart,
                            tint = Color.Gray,
                            contentDescription = null
                        )
                    }
                }

                LaunchedEffect(key1 = clickedCategoryIndex, block = {
                    categoryLazyRowState.scrollToItem(clickedCategoryIndex)
                })

                AnimatedVisibility(visible = sortMode.isOn) {
                    LazyRow(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                        state = categoryLazyRowState
                    ) {
                        itemsIndexed(categoryList) { index, expenseCategory ->
                            Chip(
                                value = expenseCategory,
                                isSelected = expenseCategory == clickedCategory.expenseCategory,
                                onChipClicked = { clickedCategory ->
                                    viewModel.onEvent(
                                        UiEvent.ExpenseCategorySelectionForSort(
                                            clickedCategory, index
                                        )
                                    )
                                })
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                if (state.expenseList.isEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = "No Expenses found",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.height(8.dp))
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