package ie.setu.mad2_assignment_one.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import ie.setu.mad2_assignment_one.data.ShoppingListItem
import ie.setu.mad2_assignment_one.data.ShoppingListItemList
import ie.setu.mad2_assignment_one.data.repository.ShoppingListItemListsRepository
import kotlinx.coroutines.flow.first
import androidx.compose.runtime.State

class ShoppingListViewModel(private val shoppingListItemListsRepository: ShoppingListItemListsRepository) : ViewModel() {

    private val _shoppingList = mutableStateListOf<ShoppingListItem>()
    val shoppingList: SnapshotStateList<ShoppingListItem> = _shoppingList

    private val _note = mutableStateOf("")
    val note: State<String> = _note

    suspend fun loadShoppingList(documentId: String = "default") {
        var savedList = ShoppingListItemList.readFromFirestore(documentId)
        if (savedList != null)
            savedList.let {
                _shoppingList.addAll(it.list)
                _note.value = it.note
            }
        else {
            savedList = shoppingListItemListsRepository.getShoppingListItemListStream(0).first()
            _shoppingList.addAll(savedList.list)
            _note.value = savedList.note
        }
    }

    suspend fun saveShoppingList(documentId: String = "default") {
        shoppingListItemListsRepository.clear()
        shoppingListItemListsRepository.insertShoppingListItemList(ShoppingListItemList(0, _shoppingList.toList(), _note.value))
        ShoppingListItemList.saveToFirestore(ShoppingListItemList(0, _shoppingList.toList(), _note.value), documentId)
    }

    suspend fun editNote(note: String, documentId: String = "default") {
        _note.value = note
        saveShoppingList(documentId)
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
