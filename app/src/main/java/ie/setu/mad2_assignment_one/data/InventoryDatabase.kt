package ie.setu.mad2_assignment_one.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ie.setu.mad2_assignment_one.data.dao.ShoppingItemDao
import ie.setu.mad2_assignment_one.data.dao.ShoppingListItemDao
import ie.setu.mad2_assignment_one.data.dao.ShoppingListItemListDao
import ie.setu.mad2_assignment_one.data.dao.StoreDao

@Database(entities = [ShoppingItem::class, ShoppingListItem::class, ShoppingListItemList:: class, Store:: class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class InventoryDatabase: RoomDatabase() {
    abstract fun shoppingItemDao(): ShoppingItemDao
    abstract fun shoppingListItemDao(): ShoppingListItemDao
    abstract fun shoppingListItemListDao(): ShoppingListItemListDao
    abstract fun storeDao(): StoreDao

    companion object {
        @Volatile
        private var Instance: InventoryDatabase? = null
        fun getDatabase(context: Context): InventoryDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, InventoryDatabase::class.java, "local_database").fallbackToDestructiveMigration().build().also {
                    Instance = it
                }
            }
        }
    }
}