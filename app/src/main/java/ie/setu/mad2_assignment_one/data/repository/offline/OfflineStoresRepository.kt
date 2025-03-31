package ie.setu.mad2_assignment_one.data.repository.offline

import ie.setu.mad2_assignment_one.data.Store
import ie.setu.mad2_assignment_one.data.dao.StoreDao
import ie.setu.mad2_assignment_one.data.repository.StoresRepository
import kotlinx.coroutines.flow.Flow

class OfflineStoresRepository(private val storeDao: StoreDao): StoresRepository {
    override fun getAllStoresStream(): Flow<List<Store>> = storeDao.getAllStoreItems()

    override fun getStoreStream(id: Int): Flow<Store> = storeDao.getStoreItem(id)

    override suspend fun insertStore(store: Store) = storeDao.insert(store)

    override suspend fun deleteStore(store: Store) = storeDao.delete(store)

    override suspend fun updateStore(store: Store) = storeDao.update(store)
}