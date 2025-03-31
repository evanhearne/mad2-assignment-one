package ie.setu.mad2_assignment_one.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ie.setu.mad2_assignment_one.data.ShoppingListItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListItemDao {
    @Query("SELECT * from shoppingListItems where id = :id")
    fun getShoppingListItem(id: Int): Flow<ShoppingListItem>

    @Query("SELECT * from shoppingListItems ORDER BY shoppingItem ASC")
    fun getAllShoppingListItems(): Flow<List<ShoppingListItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(shoppingListItem: ShoppingListItem)

    @Update
    suspend fun update(shoppingListItem: ShoppingListItem)

    @Delete
    suspend fun delete(shoppingListItem: ShoppingListItem)
}