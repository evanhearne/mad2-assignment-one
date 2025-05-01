package ie.setu.mad2_assignment_one.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ie.setu.mad2_assignment_one.data.ShoppingListItemList
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListItemListDao {
    @Query("SELECT * from shoppingListItemLists where id = :id")
    fun getShoppingListItemList(id: Int): Flow<ShoppingListItemList>

    @Query("SELECT * from shoppingListItemLists ORDER BY list ASC")
    fun getAllShoppingListItemLists(): Flow<List<ShoppingListItemList>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(shoppingListItemList: ShoppingListItemList)

    @Update
    suspend fun update(shoppingListItemList: ShoppingListItemList)

    @Delete
    suspend fun delete(shoppingListItemList: ShoppingListItemList)

    @Query("DELETE FROM shoppingListItemLists")
    suspend fun clear()
}