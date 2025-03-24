package ie.setu.mad2_assignment_one.data

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.serialization.Serializable
import android.util.Log
import androidx.room.Entity
import kotlinx.coroutines.tasks.await

@Entity(tableName = "shoppingListItemLists")
@Serializable
data class ShoppingListItemList(
    val id: Int = 0,
    var list: List<ShoppingListItem> = emptyList()
) {
    companion object {
        private const val COLLECTION_NAME = "shoppingLists" // Choose a name for your collection

        // Read from Firestore (replace "yourDocumentId" with the actual ID)
        suspend fun readFromFirestore(documentId: String): ShoppingListItemList? = try {
            val documentSnapshot = Firebase.firestore.collection(COLLECTION_NAME).document(documentId).get().await()
            if (documentSnapshot.exists()) {
                documentSnapshot.toObject(ShoppingListItemList::class.java) // Use Firestore's built-in conversion
            } else {
                null
            }
        } catch (e: Exception) {
            Log.w("Firestore", "Error reading from Firestore", e)
            null
        }

        // Save to Firestore
        suspend fun saveToFirestore(shoppingList: ShoppingListItemList, documentId: String) = try {
            Firebase.firestore.collection(COLLECTION_NAME).document(documentId).set(shoppingList).await()
        } catch (e: Exception) {
            Log.w("Firestore", "Error saving to Firestore", e)
        }
    }
}
