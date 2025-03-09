package ie.setu.mad2_assignment_one.data

import android.content.Context
import ie.setu.mad2_assignment_one.R
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Store(
    val storeId: String,
    val name: String,
    val address: String,
    // store location
    val longitude: Double,
    val latitude: Double,
    val openingHours: List<String>,
    val phoneNumber: String,
) {
    fun loadStores(context: Context) :List<Store> {
        val rawId = R.raw.stores // JSON file for shopping items
        if (rawId == 0) return emptyList()

        val jsonString = context.resources.openRawResource(rawId).bufferedReader().use { it.readText() }
        return Json.decodeFromString(jsonString)
    }
}