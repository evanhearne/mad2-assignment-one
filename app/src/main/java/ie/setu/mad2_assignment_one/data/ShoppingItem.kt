package ie.setu.mad2_assignment_one.data

import android.content.Context
import androidx.annotation.DrawableRes
import ie.setu.mad2_assignment_one.R
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

// enum Categories
enum class Category {
    GROCERIES,
    ELECTRONICS,
    HOMEWARE,
    CLOTHES
}

@Entity(tableName = "shoppingItems")
@Serializable
@TypeConverters(Converters::class)
data class ShoppingItem(
    @PrimaryKey val id: Int = 0,
    @DrawableRes val imageRes: String = "",  // Resource ID for images
    val name: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val category: Category = Category.GROCERIES,
    val availability: Boolean = false,
    val storeId: String = ""
)

fun loadShoppingItems(context: Context): List<ShoppingItem> {
    val rawId = R.raw.shopping_items // JSON file for shopping items
    if (rawId == 0) return emptyList()

    val jsonString = context.resources.openRawResource(rawId).bufferedReader().use { it.readText() }
    return Json.decodeFromString(jsonString)
}