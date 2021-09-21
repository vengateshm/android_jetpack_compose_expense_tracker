package com.android.vengateshm.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.android.vengateshm.expensetracker.presentation.Screen
import com.android.vengateshm.expensetracker.presentation.expenseAdd.ExpenseAddScreen
import com.android.vengateshm.expensetracker.presentation.expenseList.ExpenseListScreen
import com.android.vengateshm.expensetracker.presentation.more.MoreScreen
import com.android.vengateshm.expensetracker.presentation.toToolbarLabelResId
import com.android.vengateshm.expensetracker.ui.theme.ExpenseTrackerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

val bottomMenuList = listOf(
    Screen.ExpenseList,
    Screen.More
)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpenseTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    MainScreen(navController)
                }
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current as ComponentActivity
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            val route = navBackStackEntry?.destination?.route ?: ""
            if (route == Screen.ExpenseAdd.route) {
                TopAppBar(
                    title = {
                        Text(text = stringResource(id = route.toToolbarLabelResId()))
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navController.popBackStack()
                            }) {
                            Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
                        }
                    }
                )
            } else {
                TopAppBar(
                    title = {
                        Text(text = stringResource(id = route.toToolbarLabelResId()))
                    },
                )
            }
        },
        bottomBar = {
            BottomNavigation {
                val currentDestination = navBackStackEntry?.destination
                bottomMenuList.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(text = stringResource(id = screen.labelResourceId)) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.ExpenseList.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Screen.ExpenseList.route) {
                ExpenseListScreen(navController = navController)
            }
            composable(Screen.More.route) {
                MoreScreen(navController = navController)
            }
            composable(Screen.ExpenseAdd.route) {
                ExpenseAddScreen(navController = navController,
                onShowSnackBar = {
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = context.getString(it),
                            duration = SnackbarDuration.Short
                        )
                    }
                })
            }
        }
    }
}