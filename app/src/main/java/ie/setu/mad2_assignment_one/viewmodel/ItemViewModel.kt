package ie.setu.mad2_assignment_one.viewmodel
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import ie.setu.mad2_assignment_one.data.ShoppingItem
import ie.setu.mad2_assignment_one.data.repository.ShoppingItemsRepository

class ItemViewModel() : ViewModel() {
    // A state to hold the selected item
    private val _selectedItem = mutableStateOf<ShoppingItem?>(null)
    val selectedItem: State<ShoppingItem?> = _selectedItem

    // Function to set the selected item
    fun selectItem(item: ShoppingItem) {
        _selectedItem.value = item
    }
}
