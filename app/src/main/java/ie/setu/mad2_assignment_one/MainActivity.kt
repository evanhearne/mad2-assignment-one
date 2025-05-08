package ie.setu.mad2_assignment_one

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import ie.setu.mad2_assignment_one.data.ShoppingItem
import ie.setu.mad2_assignment_one.data.Store
import ie.setu.mad2_assignment_one.data.loadShoppingItems
import ie.setu.mad2_assignment_one.navigation.About
import ie.setu.mad2_assignment_one.navigation.Account
import ie.setu.mad2_assignment_one.navigation.ItemDetails
import ie.setu.mad2_assignment_one.navigation.Login
import ie.setu.mad2_assignment_one.navigation.Main
import ie.setu.mad2_assignment_one.navigation.Register
import ie.setu.mad2_assignment_one.navigation.SelectStore
import ie.setu.mad2_assignment_one.navigation.ShoppingList
import ie.setu.mad2_assignment_one.ui.AboutScreen
import ie.setu.mad2_assignment_one.ui.AppViewModelProvider
import ie.setu.mad2_assignment_one.ui.ItemDetailsScreen
import ie.setu.mad2_assignment_one.ui.main.MainScreen
import ie.setu.mad2_assignment_one.ui.shopping.ShoppingListScreen
import ie.setu.mad2_assignment_one.ui.store.StoreSelectScreen
import ie.setu.mad2_assignment_one.ui.theme.Mad2assignmentoneTheme
import ie.setu.mad2_assignment_one.ui.user.RegisterScreen
import ie.setu.mad2_assignment_one.viewmodel.ItemViewModel
import ie.setu.mad2_assignment_one.viewmodel.ShoppingListViewModel
import ie.setu.mad2_assignment_one.viewmodel.StoreViewModel
import ie.setu.mad2_assignment_one.ui.BottomNavigationBar
import ie.setu.mad2_assignment_one.ui.user.AccountScreen

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
                val shoppingListViewModel: ShoppingListViewModel = viewModel(factory = AppViewModelProvider.factory)
                val storeViewModel: StoreViewModel = viewModel(factory = AppViewModelProvider.factory)

                // get documentId i.e. the email address
                val documentId = Firebase.auth.currentUser?.email
                // Ensure shopping list is loaded once
                LaunchedEffect(Unit) {
                    if (documentId != null)
                        shoppingListViewModel.loadShoppingList(documentId)
                    else
                        shoppingListViewModel.loadShoppingList()
                }

                // Search query state
                val query = remember { mutableStateOf("") }

                // Scroll state
                val scrollState = rememberScrollState()

                // Load shopping items once
                val allItems = remember { mutableStateOf(loadShoppingItems(context)) }

                var selectedOption = remember { mutableIntStateOf(0) }

                // Optimized filtering using derivedStateOf to avoid unnecessary recalculations
                val filteredItems by remember {
                    derivedStateOf {
                        // Apply store filter first if selectedStore is not null
                        val filteredByStore = if (storeViewModel.selectedStore.value != null) {
                            allItems.value.filter { it.storeId == storeViewModel.selectedStore.value?.storeId }
                        } else {
                            allItems.value // No store filter if none selected
                        }

                        // Apply search query filter after store filter
                        if (query.value.isNotEmpty()) {
                            filteredByStore.filter { it.name.contains(query.value, ignoreCase = true) }
                        } else {
                            filteredByStore
                        }
                    }
                }

                // Navigation host
                NavHost(navController, startDestination = Main) {
                    // Main Screen
                    composable<Main> {
                        Column {
                            Row {
                                MainScreen(
                                    onNavigateToShoppingList = { navController.navigate(route = ShoppingList) },
                                    onItemClick = { item ->
                                        itemViewModel.selectItem(item as ShoppingItem)
                                        navController.navigate(route = ItemDetails)
                                    },
                                    query = query,
                                    scrollState = scrollState,
                                    items = filteredItems, // Use optimized filtered items,
                                    onNavigateChooseStore = {
                                        navController.navigate(route = SelectStore)
                                    },
                                    bottomNavBar = { BottomNavigationBar(navController = navController, selectedOption = selectedOption.intValue, onOptionSelected = { selectedOption.intValue = it}) },
                                    chooseStoreText = storeViewModel.selectedStore.value?.name ?: context.getString(R.string.choose_store_button_text)
                                )
                            }
                        }
                    }
                    // Select Store Screen
                    composable<SelectStore> {
                        StoreSelectScreen(context = context, onNavigateBack = {
                            navController.navigate(route = Main)
                        }, onChooseStore = { store ->
                            storeViewModel.selectStore(store as Store)
                            Toast.makeText(context, "Chosen store is ${store.name}...", Toast.LENGTH_LONG).show()
                            navController.navigate(route = Main)
                        },
                            bottomNavBar = { BottomNavigationBar(navController = navController, selectedOption = selectedOption.intValue, onOptionSelected = { selectedOption.intValue = it}) })

                    }
                    // Shopping List Screen
                    composable<ShoppingList> {
                        ShoppingListScreen(
                            onNavigateBack = { navController.popBackStack() },
                            shoppingListViewModel = shoppingListViewModel,
                            onItemClick = { item ->
                                itemViewModel.selectItem(item)
                                navController.navigate(route = ItemDetails)
                            }
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
                            )
                        }
                    }

                    // Register Screen
                    composable<Register> {
                        RegisterScreen(context = context, onNavigateToLoginScreen = { navController.navigate(route = Login) })
                    }

                    // Log in Screen
                    composable<Account> {
                        AccountScreen(
                            bottomNavBar = { BottomNavigationBar(navController = navController, selectedOption = selectedOption.intValue, onOptionSelected = { selectedOption.intValue = it}) },
                            onNavigateToHome = { navController.navigate(route = Main); selectedOption.intValue = 0 },
                            onNavigateToAbout = { navController.navigate(route = About) }
                        )
                    }
                    // About Screen
                    composable<About> {
                        AboutScreen(
                            bottomNavBar = { BottomNavigationBar(navController = navController, selectedOption = selectedOption.intValue, onOptionSelected = { selectedOption.intValue = it}) },
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
