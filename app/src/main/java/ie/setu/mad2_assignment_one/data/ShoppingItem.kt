package ie.setu.mad2_assignment_one.data

import android.content.Context
import androidx.annotation.DrawableRes
import ie.setu.mad2_assignment_one.R
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class ShoppingItem(
    @DrawableRes val imageRes: Int = 0,  // Resource ID for images
    val name: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val availability: Boolean = false,
    val storeId: String = ""
)

fun loadShoppingItems(context: Context): List<ShoppingItem> {
    val rawId = R.raw.shopping_items // JSON file for shopping items
    if (rawId == 0) return emptyList()

    val jsonString = context.resources.openRawResource(rawId).bufferedReader().use { it.readText() }
    return Json.decodeFromString(jsonString)
}