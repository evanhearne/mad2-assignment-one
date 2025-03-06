package ie.setu.mad2_assignment_one

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ie.setu.mad2_assignment_one.navigation.ItemDetails
import ie.setu.mad2_assignment_one.navigation.Main
import ie.setu.mad2_assignment_one.navigation.ShoppingList
import ie.setu.mad2_assignment_one.ui.ItemDetailsScreen
import ie.setu.mad2_assignment_one.ui.main.MainScreen
import ie.setu.mad2_assignment_one.ui.shopping.ShoppingListScreen
import ie.setu.mad2_assignment_one.ui.theme.Mad2assignmentoneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Mad2assignmentoneTheme {
                // define nav controller + host
                val navController = rememberNavController()

                NavHost(navController, startDestination = Main) {
                    composable<Main> {
                        MainScreen(
                            onNavigateToShoppingList = { navController.navigate(route = ShoppingList) },
                            onItemClick = { navController.navigate(route = ItemDetails) }
                        )
                    }
                    composable<ShoppingList> {
                        ShoppingListScreen(
                            onNavigateBack = { navController.popBackStack() },  // Go back dynamically
                            onItemClick = { navController.navigate(route = ItemDetails) }
                        )
                    }
                    composable<ItemDetails> {
                        ItemDetailsScreen(
                            onNavigateBack = { navController.popBackStack() }  // Go back dynamically
                        )
                    }
                }
            }
        }
    }
}