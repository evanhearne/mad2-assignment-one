package ie.setu.mad2_assignment_one.data

import android.content.Context
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
data class ShoppingListItemList(
    var list: List<ShoppingListItem>
) {
    companion object {
        private const val FILE_NAME = "shopping_list.json"

        // Read from internal storage
        fun readFromStorage(context: Context): ShoppingListItemList? {
            val file = File(context.filesDir, FILE_NAME)
            return if (file.exists()) {
                try {
                    val jsonString = file.readText()
                    Json.decodeFromString(jsonString)
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            } else {
                null
            }
        }

        // Save to internal storage
        fun saveToStorage(context: Context, shoppingList: ShoppingListItemList) {
            try {
                val jsonString = Json.encodeToString(shoppingList)
                val file = File(context.filesDir, FILE_NAME)
                file.writeText(jsonString)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}