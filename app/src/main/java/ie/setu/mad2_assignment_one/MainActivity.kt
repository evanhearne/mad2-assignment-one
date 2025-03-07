package ie.setu.mad2_assignment_one

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ie.setu.mad2_assignment_one.data.ShoppingItem
import ie.setu.mad2_assignment_one.navigation.ItemDetails
import ie.setu.mad2_assignment_one.navigation.Main
import ie.setu.mad2_assignment_one.navigation.ShoppingList
import ie.setu.mad2_assignment_one.ui.ItemDetailsScreen
import ie.setu.mad2_assignment_one.ui.main.MainScreen
import ie.setu.mad2_assignment_one.ui.shopping.ShoppingListScreen
import ie.setu.mad2_assignment_one.ui.theme.Mad2assignmentoneTheme
import ie.setu.mad2_assignment_one.viewmodel.ItemViewModel
import ie.setu.mad2_assignment_one.viewmodel.ShoppingListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Mad2assignmentoneTheme {
                // define context

                val context = LocalContext.current
                // define nav controller + host
                val navController = rememberNavController()

                // define item view model
                val itemViewModel: ItemViewModel = viewModel()

                // define shopping list view model
                val shoppingListViewModel: ShoppingListViewModel = viewModel()

                // load shopping list
                shoppingListViewModel.loadShoppingList(context)

                // navhost
                NavHost(navController, startDestination = Main) {
                    // Main Screen
                    composable<Main> {
                        MainScreen(
                            onNavigateToShoppingList = { navController.navigate(route = ShoppingList) },
                            onItemClick = { item ->
                                itemViewModel.selectItem(item as ShoppingItem) // Save item in ViewModel
                                navController.navigate(route = ItemDetails)
                            }
                        )
                    }
                    // Shopping List Screen
                    composable<ShoppingList> {
                        ShoppingListScreen(
                            onNavigateBack = { navController.popBackStack() },  // Go back dynamically
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
                        // Access the selected item from the ViewModel
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
                }
            }
        }
    }
}