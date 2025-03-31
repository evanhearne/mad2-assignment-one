package ie.setu.mad2_assignment_one.data.repository.offline

import ie.setu.mad2_assignment_one.data.ShoppingListItemList
import ie.setu.mad2_assignment_one.data.dao.ShoppingListItemListDao
import ie.setu.mad2_assignment_one.data.repository.ShoppingListItemListsRepository
import kotlinx.coroutines.flow.Flow

class OfflineShoppingListItemListsRepository(private val shoppingListItemListDao: ShoppingListItemListDao): ShoppingListItemListsRepository {
    override fun getAllShoppingListItemListsStream(): Flow<List<ShoppingListItemList>> = shoppingListItemListDao.getAllShoppingListItemLists()

    override fun getShoppingListItemListStream(id: Int): Flow<ShoppingListItemList> = shoppingListItemListDao.getShoppingListItemList(id)

    override suspend fun insertShoppingListItemList(shoppingListItemList: ShoppingListItemList) = shoppingListItemListDao.insert(shoppingListItemList)

    override suspend fun deleteShoppingListItemList(shoppingListItemList: ShoppingListItemList) = shoppingListItemListDao.delete(shoppingListItemList)

    override suspend fun updateShoppingListItemList(shoppingListItemList: ShoppingListItemList) = shoppingListItemListDao.update(shoppingListItemList)

}