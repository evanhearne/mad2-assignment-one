package ie.setu.mad2_assignment_one.data.repository.offline

import ie.setu.mad2_assignment_one.data.ShoppingItem
import ie.setu.mad2_assignment_one.data.dao.ShoppingItemDao
import ie.setu.mad2_assignment_one.data.repository.ShoppingItemsRepository
import kotlinx.coroutines.flow.Flow

class OfflineShoppingItemsRepository(private val shoppingItemDao: ShoppingItemDao): ShoppingItemsRepository {
    override fun getAllShoppingItemsStream(): Flow<List<ShoppingItem>> = shoppingItemDao.getAllShoppingItems()
    override fun getShoppingItemStream(id: Int): Flow<ShoppingItem?> = shoppingItemDao.getShoppingItem(id)
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) = shoppingItemDao.insert(shoppingItem)
    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) = shoppingItemDao.delete(shoppingItem)
    override suspend fun updateShoppingItem(shoppingItem: ShoppingItem) = shoppingItemDao.update(shoppingItem)
}