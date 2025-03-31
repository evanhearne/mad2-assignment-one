package ie.setu.mad2_assignment_one.data.repository.offline

import ie.setu.mad2_assignment_one.data.ShoppingListItem
import ie.setu.mad2_assignment_one.data.dao.ShoppingListItemDao
import ie.setu.mad2_assignment_one.data.repository.ShoppingListItemsRepository
import kotlinx.coroutines.flow.Flow

class OfflineShoppingListItemsRepository(private val shoppingListItemDao: ShoppingListItemDao): ShoppingListItemsRepository {
    override fun getAllShoppingListItemsStream(): Flow<List<ShoppingListItem>> = shoppingListItemDao.getAllShoppingListItems()

    override fun getShoppingListItemStream(id: Int): Flow<ShoppingListItem> = shoppingListItemDao.getShoppingListItem(id)

    override suspend fun insertShoppingListItem(shoppingListItem: ShoppingListItem) = shoppingListItemDao.insert(shoppingListItem)

    override suspend fun deleteShoppingListItem(shoppingListItem: ShoppingListItem) = shoppingListItemDao.delete(shoppingListItem)

    override suspend fun updateShoppingListItem(shoppingListItem: ShoppingListItem) = shoppingListItemDao.update(shoppingListItem)

}