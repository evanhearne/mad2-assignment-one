package ie.setu.mad2_assignment_one.data.repository

import ie.setu.mad2_assignment_one.data.ShoppingItem
import kotlinx.coroutines.flow.Flow

interface ShoppingItemsRepository {
    fun getAllShoppingItemsStream(): Flow<List<ShoppingItem>>
    fun getShoppingItemStream(id: Int): Flow<ShoppingItem?>
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)
    suspend fun updateShoppingItem(shoppingItem: ShoppingItem)
}