package ie.setu.mad2_assignment_one.data

import androidx.room.Entity
import kotlinx.serialization.Serializable

@Entity(tableName = "shoppingListItems")
@Serializable
data class ShoppingListItem(
    val id: Int = 0,
    val shoppingItem: ShoppingItem = ShoppingItem(),
    var quantity: Int = 0
)