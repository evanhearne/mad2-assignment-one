package ie.setu.mad2_assignment_one.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import ie.setu.mad2_assignment_one.data.ShoppingListItem
import ie.setu.mad2_assignment_one.data.ShoppingListItemList

class ShoppingListViewModel() : ViewModel() {

    private val _shoppingList = mutableStateListOf<ShoppingListItem>()
    val shoppingList: SnapshotStateList<ShoppingListItem> = _shoppingList

    suspend fun loadShoppingList(documentId: String = "default") {
        val savedList = ShoppingListItemList.readFromFirestore(documentId)
        savedList?.let {
            _shoppingList.addAll(it.list)
        }
    }

    suspend fun saveShoppingList(documentId: String = "default") {
        ShoppingListItemList.saveToFirestore(ShoppingListItemList(_shoppingList.toList()), documentId)
    }

    suspend fun addItem(item: ShoppingListItem, documentId: String = "default") {
        _shoppingList.add(item)
        saveShoppingList(documentId)
    }

    suspend fun removeItem(item: ShoppingListItem, documentId: String = "default") {
        _shoppingList.remove(item)
        saveShoppingList(documentId)
    }

    suspend fun removeAllItems(documentId: String = "default") {
        _shoppingList.clear()
        saveShoppingList(documentId)
    }

    suspend fun increaseItemQuantity(item: ShoppingListItem, documentId: String = "default") {
        val index = _shoppingList.indexOf(item)
        if (index != -1) {
            val newItem = item.copy(quantity = item.quantity + 1)
            _shoppingList[index] = newItem
            saveShoppingList(documentId)
        }
    }

    suspend fun decreaseItemQuantity(item: ShoppingListItem, documentId: String = "default") {
        val index = _shoppingList.indexOf(item)
        if (index != -1) {
            val newQuantity = item.quantity - 1
            if (newQuantity < 1) {
                removeItem(item, documentId)
            } else {
                _shoppingList[index] = item.copy(quantity = newQuantity)
                saveShoppingList(documentId)
            }
        }
    }
}
