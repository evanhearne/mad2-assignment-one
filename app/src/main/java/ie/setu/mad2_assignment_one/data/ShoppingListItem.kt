package ie.setu.mad2_assignment_one.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.serialization.Serializable

@Entity(tableName = "shoppingListItems")
@Serializable
@TypeConverters(Converters::class)
data class ShoppingListItem(
    @PrimaryKey val id: Int = 0,
    val shoppingItem: ShoppingItem = ShoppingItem(),
    var quantity: Int = 0
)