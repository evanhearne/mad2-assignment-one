package ie.setu.mad2_assignment_one.data

import ie.setu.mad2_assignment_one.data.repository.ShoppingItemsRepository
import ie.setu.mad2_assignment_one.data.repository.ShoppingListItemListsRepository
import ie.setu.mad2_assignment_one.data.repository.ShoppingListItemsRepository
import ie.setu.mad2_assignment_one.data.repository.StoresRepository
import ie.setu.mad2_assignment_one.data.repository.offline.OfflineShoppingItemsRepository
import android.content.Context
import ie.setu.mad2_assignment_one.data.repository.offline.OfflineShoppingListItemListsRepository
import ie.setu.mad2_assignment_one.data.repository.offline.OfflineShoppingListItemsRepository
import ie.setu.mad2_assignment_one.data.repository.offline.OfflineStoresRepository

interface AppContainer {
    val shoppingItemsRepository: ShoppingItemsRepository
    val shoppingListItemsRepository: ShoppingListItemsRepository
    val shoppingListItemListsRepository: ShoppingListItemListsRepository
    val storesRepository: StoresRepository
}

class AppDataContainer(private val context: Context): AppContainer {
    override val shoppingItemsRepository: ShoppingItemsRepository by lazy {
        OfflineShoppingItemsRepository(InventoryDatabase.getDatabase(context).shoppingItemDao())
    }
    override val shoppingListItemsRepository: ShoppingListItemsRepository by lazy {
        OfflineShoppingListItemsRepository(InventoryDatabase.getDatabase(context).shoppingListItemDao())
    }
    override val shoppingListItemListsRepository: ShoppingListItemListsRepository by lazy {
        OfflineShoppingListItemListsRepository(InventoryDatabase.getDatabase(context).shoppingListItemListDao())
    }
    override val storesRepository: StoresRepository by lazy {
        OfflineStoresRepository(InventoryDatabase.getDatabase(context).storeDao())
    }
}