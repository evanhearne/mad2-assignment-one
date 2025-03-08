package ie.setu.mad2_assignment_one

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ie.setu.mad2_assignment_one.data.ShoppingItem
import ie.setu.mad2_assignment_one.data.loadShoppingItems
import ie.setu.mad2_assignment_one.navigation.ItemDetails
import ie.setu.mad2_assignment_one.navigation.Login
import ie.setu.mad2_assignment_one.navigation.Main
import ie.setu.mad2_assignment_one.navigation.Register
import ie.setu.mad2_assignment_one.navigation.ShoppingList
import ie.setu.mad2_assignment_one.ui.ItemDetailsScreen
import ie.setu.mad2_assignment_one.ui.main.MainScreen
import ie.setu.mad2_assignment_one.ui.shopping.ShoppingListScreen
import ie.setu.mad2_assignment_one.ui.theme.Mad2assignmentoneTheme
import ie.setu.mad2_assignment_one.ui.user.LoginScreen
import ie.setu.mad2_assignment_one.ui.user.RegisterScreen
import ie.setu.mad2_assignment_one.viewmodel.ItemViewModel
import ie.setu.mad2_assignment_one.viewmodel.ShoppingListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Mad2assignmentoneTheme {
                val context = LocalContext.current
                val navController = rememberNavController()

                // ViewModels
                val itemViewModel: ItemViewModel = viewModel()
                val shoppingListViewModel: ShoppingListViewModel = viewModel()

                // Ensure shopping list is loaded once
                LaunchedEffect(Unit) {
                    shoppingListViewModel.loadShoppingList(context)
                }

                // Search query state
                val query = remember { mutableStateOf("") }

                // Scroll state
                val scrollState = rememberScrollState()

                // Load shopping items once
                val allItems = remember { mutableStateOf(loadShoppingItems(context)) }

                // Optimize filtering using derivedStateOf to avoid unnecessary recalculations
                val filteredItems by remember {
                    // https://developer.android.com/develop/ui/compose/side-effects#derivedstateof
                    derivedStateOf {
                        if (query.value != "") {
                            allItems.value.filter { it.name.contains(query.value, ignoreCase = true) }
                        } else {
                            allItems.value
                        }
                    }
                }

                // Navigation host
                NavHost(navController, startDestination = Main) {
                    // Main Screen
                    composable<Main> {
                        MainScreen(
                            onNavigateToShoppingList = { navController.navigate(route = ShoppingList) },
                            onItemClick = { item ->
                                itemViewModel.selectItem(item as ShoppingItem)
                                navController.navigate(route = ItemDetails)
                            },
                            query = query,
                            scrollState = scrollState,
                            items = filteredItems // Use optimized filtered items
                        )
                    }

                    // Shopping List Screen
                    composable<ShoppingList> {
                        ShoppingListScreen(
                            onNavigateBack = { navController.popBackStack() },
                            shoppingListViewModel = shoppingListViewModel,
                            onItemClick = { item ->
                                itemViewModel.selectItem(item)
                                navController.navigate(route = ItemDetails)
                            },
                            context = context
                        )
                    }

                    // Item Details Screen
                    composable<ItemDetails> {
                        val selectedItem = itemViewModel.selectedItem.value
                        if (selectedItem != null) {
                            ItemDetailsScreen(
                                item = selectedItem,
                                onNavigateBack = { navController.popBackStack() },
                                shoppingListViewModel = shoppingListViewModel,
                                context = context
                            )
                        }
                    }

                    // Register Screen
                    composable<Register> {
                        RegisterScreen(context = context, onNavigateToLoginScreen = { navController.navigate(route = Login) })
                    }

                    // Log in Screen
                    composable<Login> {
                        LoginScreen(
                            context = context,
                            onNavigateToHome = { navController.navigate(route = Main) },
                            onNavigateToRegistration = { navController.navigate(route = Register) }
                        )
                    }
                }
            }
        }
    }
}
