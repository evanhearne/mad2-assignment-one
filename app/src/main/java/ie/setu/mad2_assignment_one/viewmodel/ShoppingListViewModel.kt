package ie.setu.mad2_assignment_one.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import ie.setu.mad2_assignment_one.data.ShoppingListItem
import ie.setu.mad2_assignment_one.data.ShoppingListItemList

class ShoppingListViewModel() : ViewModel() {

    private val _shoppingList = mutableStateListOf<ShoppingListItem>()
    val shoppingList: SnapshotStateList<ShoppingListItem> = _shoppingList

    fun loadShoppingList(context: Context) {
        val savedList = ShoppingListItemList.readFromStorage(context)
        savedList?.let {
            _shoppingList.addAll(it.list)
        }
    }

    private fun saveShoppingList(context: Context) {
        ShoppingListItemList.saveToStorage(context, ShoppingListItemList(_shoppingList))
    }

    fun addItem(item: ShoppingListItem, context: Context) {
        _shoppingList.add(item)
        saveShoppingList(context)
    }

    private fun removeItem(item: ShoppingListItem, context: Context) {
        _shoppingList.remove(item)
        saveShoppingList(context)
    }

    fun removeAllItems(context: Context) {
        _shoppingList.clear()
        saveShoppingList(context)
    }

    fun increaseItemQuantity(item: ShoppingListItem, context: Context) {
        val index = _shoppingList.indexOf(item)
        if (index != -1) {
            val newItem = item.copy(quantity = item.quantity + 1)
            _shoppingList[index] = newItem
            saveShoppingList(context)
        }
    }

    fun decreaseItemQuantity(item: ShoppingListItem, context: Context) {
        val index = _shoppingList.indexOf(item)
        if (index != -1) {
            val newQuantity = item.quantity - 1
            if (newQuantity < 1) {
                removeItem(item, context)
            } else {
                _shoppingList[index] = item.copy(quantity = newQuantity)
                saveShoppingList(context)
            }
        }
    }
}
