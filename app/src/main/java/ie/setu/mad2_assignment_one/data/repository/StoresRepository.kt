package ie.setu.mad2_assignment_one.data.repository

import ie.setu.mad2_assignment_one.data.Store
import kotlinx.coroutines.flow.Flow

interface StoresRepository {
    fun getAllStoresStream(): Flow<List<Store>>
    fun getStoreStream(id: Int): Flow<Store>
    suspend fun insertStore(store: Store)
    suspend fun deleteStore(store: Store)
    suspend fun updateStore(store: Store)
}