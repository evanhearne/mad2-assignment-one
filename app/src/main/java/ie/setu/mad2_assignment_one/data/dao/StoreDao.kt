package ie.setu.mad2_assignment_one.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ie.setu.mad2_assignment_one.data.Store
import kotlinx.coroutines.flow.Flow

@Dao
interface StoreDao {
    @Query("SELECT * from storeItems where id = :id")
    suspend fun getStoreItem(id: Int): Flow<Store>

    @Query("SELECT * from storeItems ORDER BY name")
    suspend fun getAllStoreItems(): Flow<List<Store>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(storeDao: StoreDao)

    @Update
    suspend fun update(store: Store)

    @Delete
    suspend fun delete(store: Store)
}