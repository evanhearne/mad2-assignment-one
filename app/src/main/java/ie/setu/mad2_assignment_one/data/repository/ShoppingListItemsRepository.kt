package ie.setu.mad2_assignment_one.data.repository

import ie.setu.mad2_assignment_one.data.ShoppingListItem
import kotlinx.coroutines.flow.Flow

interface ShoppingListItemsRepository {
    fun getAllShoppingListItemsStream(): Flow<List<ShoppingListItem>>
    fun getShoppingListItemStream(id: Int): Flow<ShoppingListItem>
    suspend fun insertShoppingListItem(shoppingListItem: ShoppingListItem)
    suspend fun deleteShoppingListItem(shoppingListItem: ShoppingListItem)
    suspend fun updateShoppingListItem(shoppingListItem: ShoppingListItem)
}