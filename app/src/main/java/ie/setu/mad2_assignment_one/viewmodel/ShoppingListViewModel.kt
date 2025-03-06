package ie.setu.mad2_assignment_one.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import ie.setu.mad2_assignment_one.data.ShoppingListItem

class ShoppingListViewModel : ViewModel() {
    // A mutable list to hold shopping items
    private val _shoppingList = mutableStateListOf<ShoppingListItem>()
    val shoppingList: SnapshotStateList<ShoppingListItem> = _shoppingList

    // Method to add an item
    fun addItem(item: ShoppingListItem) {
        _shoppingList.add(item)
    }

    // Method to remove an item
    fun removeItem(item: ShoppingListItem) {
        _shoppingList.remove(item)
    }

    // Method to remove all items
    fun removeAllItems() {
        _shoppingList.clear()
    }

    // Increase quantity
    fun increaseItemQuantity(item: ShoppingListItem) {
        val newItem = ShoppingListItem(item.shoppingItem, item.quantity+1)
        _shoppingList[_shoppingList.indexOf(item)] = newItem
    }

    // Decrease quantity
    fun decreaseItemQuantity(item: ShoppingListItem) {
        val newItem = ShoppingListItem(item.shoppingItem, item.quantity-1)
        if (newItem.quantity < 1) {
            removeItem(item)
        } else {
            _shoppingList[_shoppingList.indexOf(item)] = newItem
        }
    }
}