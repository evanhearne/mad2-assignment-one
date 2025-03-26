package ie.setu.mad2_assignment_one.viewmodel
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import ie.setu.mad2_assignment_one.data.Store
import ie.setu.mad2_assignment_one.data.repository.StoresRepository

class StoreViewModel(private val storesRepository: StoresRepository) : ViewModel() {
    // A state to hold the selected item
    private val _selectedStore = mutableStateOf<Store?>(null)
    val selectedStore: State<Store?> = _selectedStore

    // Function to set the selected item
    fun selectStore(store: Store) {
        _selectedStore.value = store
    }
}
