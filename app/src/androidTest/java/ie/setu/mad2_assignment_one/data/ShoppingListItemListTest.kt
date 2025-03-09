package ie.setu.mad2_assignment_one.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.MemoryCacheSettings
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ShoppingListItemListTest {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var testList: ShoppingListItemList
    private val testDocumentId = "testDocument" // Unique ID for the test document

    @Before
    fun setUp() {
        // set up firebase store emulator
        firestore = FirebaseFirestore.getInstance()
        try {
            firestore.useEmulator("10.0.2.2", 8080)
        } catch (e: IllegalStateException) {
            throw e
        }

        val settings = FirebaseFirestoreSettings.Builder()
            .setLocalCacheSettings(MemoryCacheSettings.newBuilder().build()) // Use in-memory cache
            .build()

        firestore.firestoreSettings = settings
        testList = ShoppingListItemList(
            listOf(
                ShoppingListItem(ShoppingItem(0, "Milk", "Lovely milk", 2.30, true), 1),
                ShoppingListItem(ShoppingItem(0, "Bread", "Today's bread, tomorrow!", 1.50, false), 1)
            )
        )
    }

    @After
    fun tearDown() {
        firestore.collection("shoppingLists").document(testDocumentId).delete()
    }

    @Test
    fun testSaveToFirestore_savesDataCorrectly() = runTest {
        ShoppingListItemList.saveToFirestore(testList, testDocumentId)

        val retrievedList = ShoppingListItemList.readFromFirestore(testDocumentId)
        assertNotNull(retrievedList)
        assertEquals(testList, retrievedList)
    }

    @Test
    fun testReadFromFirestore_returnsNullWhenDocumentDoesNotExist() = runTest {
        val retrievedList = ShoppingListItemList.readFromFirestore(testDocumentId)
        assertNull(retrievedList)
    }

}
