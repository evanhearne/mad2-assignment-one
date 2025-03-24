package ie.setu.mad2_assignment_one.data.repository

import ie.setu.mad2_assignment_one.data.ShoppingListItemList
import kotlinx.coroutines.flow.Flow

interface ShoppingListItemListsRepository {
    fun getAllShoppingListItemListsStream(): Flow<List<ShoppingListItemList>>
    fun getShoppingListItemListStream(id: Int): Flow<ShoppingListItemList>
    suspend fun insertShoppingListItemList(shoppingListItemList: ShoppingListItemList)
    suspend fun deleteShoppingListItemList(shoppingListItemList: ShoppingListItemList)
    suspend fun updateShoppingListItemList(shoppingListItemList: ShoppingListItemList)
}