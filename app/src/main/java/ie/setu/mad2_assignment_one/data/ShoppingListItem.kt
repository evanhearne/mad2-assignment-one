package ie.setu.mad2_assignment_one.data

import kotlinx.serialization.Serializable

@Serializable
data class ShoppingListItem(
    val shoppingItem: ShoppingItem = ShoppingItem(),
    var quantity: Int = 0
)