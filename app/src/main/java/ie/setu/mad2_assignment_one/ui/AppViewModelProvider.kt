package ie.setu.mad2_assignment_one.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ie.setu.mad2_assignment_one.inventory.InventoryApplication
import ie.setu.mad2_assignment_one.viewmodel.ItemViewModel
import ie.setu.mad2_assignment_one.viewmodel.ShoppingListViewModel
import ie.setu.mad2_assignment_one.viewmodel.StoreViewModel

object AppViewModelProvider {
    val factory = viewModelFactory {
        initializer {
            ItemViewModel(
               // inventoryApplication.container.shoppingItemsRepository
            )
        }
        initializer {
            ShoppingListViewModel(inventoryApplication().container.shoppingListItemListsRepository)
        }
        initializer {
            StoreViewModel(inventoryApplication().container.storesRepository)
        }
    }
}

fun CreationExtras.inventoryApplication(): InventoryApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as InventoryApplication)