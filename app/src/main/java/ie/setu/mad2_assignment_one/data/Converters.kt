package ie.setu.mad2_assignment_one.data

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun fromCategory(category: Category): String = category.name

    @TypeConverter
    fun toCategory(categoryName: String): Category = Category.valueOf(categoryName)

    // ShoppingItem
    @TypeConverter
    fun fromShoppingItem(shoppingItem: ShoppingItem): String {
        return Json.encodeToString(shoppingItem)
    }
    @TypeConverter
    fun toShoppingItem(shoppingItem: String): ShoppingItem {
        return Json.decodeFromString(shoppingItem);
    }
    // ShoppingListItem
    @TypeConverter
    fun fromShoppingListItem(shoppingListItem: ShoppingListItem): String {
        return Json.encodeToString(shoppingListItem)
    }
    @TypeConverter
    fun toShoppingListItem(shoppingListItem: String): ShoppingListItem {
        return Json.decodeFromString(shoppingListItem)
    }
    //ShoppingListItemList
    @TypeConverter
    fun fromShoppingListItemList(shoppingListItemList: ShoppingListItemList): String {
        return Json.encodeToString(shoppingListItemList)
    }
    @TypeConverter
    fun toShoppingListItemList(shoppingListItemList: String): ShoppingListItemList {
        return Json.decodeFromString(shoppingListItemList)
    }
    // Store
    @TypeConverter
    fun fromStore(store: Store):String {
        return Json.encodeToString(store)
    }
    @TypeConverter
    fun toStore(store: String): Store {
        return Json.decodeFromString(store)
    }
    // ShoppingList
    @TypeConverter
    fun fromShoppingList(shoppingList: List<ShoppingListItem>): String {
        return Json.encodeToString(shoppingList)
    }
    @TypeConverter
    fun toShoppingList(json: String): List<ShoppingListItem> {
        return Json.decodeFromString(json)
    }
    // OpeningHours
    @TypeConverter
    fun fromOpeningHours(openingHours: List<String>): String {
        return Json.encodeToString(openingHours)
    }
    @TypeConverter
    fun toOpeningHours(openingHours: String): List<String> {
        return Json.decodeFromString(openingHours)
    }

}