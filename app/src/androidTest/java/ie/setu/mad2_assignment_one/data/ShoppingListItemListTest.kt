package ie.setu.mad2_assignment_one.data

import androidx.test.runner.AndroidJUnit4
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

// Note that in order to run these tests, you must set up a firestore emulator on your device.
// Info on how to do this can be found at https://cloud.google.com/firestore/docs/emulator .
// Run the firestore emulator on 127.0.0.1:8080 .

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
            // 10.0.2.2 is Android's IP address equivalent to localhost on host machine when emulating.
            // i.e. 10.0.2.2 (Android) <- 127.0.0.1 (Host PC // Machine we run instrument tests on)
            firestore.useEmulator("10.0.2.2", 8080)
        } catch(e: IllegalStateException) {
            // Catch is left empty on purpose...
            // The try-catch block is here to allow the emulator to be set up just once.
            // On multiple attempts it will throw an error, as the emulator is already set up.
            // We can ignore the errors within this abyss... :0
        }

        val settings = FirebaseFirestoreSettings.Builder()
            .setLocalCacheSettings(MemoryCacheSettings.newBuilder().build()) // Use in-memory cache
            .build()

        firestore.firestoreSettings = settings
        testList = ShoppingListItemList(0,
            listOf(
                ShoppingListItem(0,ShoppingItem(0,0, "Milk", "Lovely milk", 2.30, true), 1),
                ShoppingListItem(0,ShoppingItem(0,0, "Bread", "Today's bread, tomorrow!", 1.50, false), 1)
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
