package ie.setu.mad2_assignment_one.data

import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ie.setu.mad2_assignment_one.R
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Entity(tableName = "storeItems")
@Serializable
@TypeConverters(Converters::class)
data class Store(
    @PrimaryKey val storeId: String = "",
    val name: String = "",
    val address: String = "",
    // store location
    val longitude: Double = 0.0,
    val latitude: Double = 0.0,
    @TypeConverters(Converters::class) val openingHours: List<String> = listOf(),
    val phoneNumber: String = "",
)
    fun loadStores(context: Context) :List<Store> {
        val rawId = R.raw.stores // JSON file for shopping items
        if (rawId == 0) return emptyList()

        val jsonString = context.resources.openRawResource(rawId).bufferedReader().use { it.readText() }
        return Json.decodeFromString(jsonString)
    }
