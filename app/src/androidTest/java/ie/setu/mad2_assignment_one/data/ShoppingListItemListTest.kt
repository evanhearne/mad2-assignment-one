package ie.setu.mad2_assignment_one.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.MemoryCacheSettings
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

// To run these tests, we use Firebase CLI.
// **SETUP INSTRUCTIONS**
// 1. Install Firebase CLI here --> https://firebase.google.com/docs/cli#setup_update_cli .
// 2. Log in using this command --> firebase login
// 3. Initialise Firebase CLI using this command --> firebase init
// 3a. Ensure you choose the correct project for firebase (evanhearne-mad2-assignment-one), or modify the project name accordingly.
// 4. Ensure that Firebase CLI has the emulators FireStore and Authentication installed.
// 4a. Ensure they use default ports, or modify the ports used for them below before running tests.
// 5. Run Firebase emulator with this command --> firebase emulators:start .
// 6. Create a new user in `Authentication` --> email -> test@auth.com , password -> abcd1234 . Modify auth below if chosen different credentials.
// 7. Run tests with emulated/real device running.

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ShoppingListItemListTest {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var testList: ShoppingListItemList
    private val testDocumentId = "testDocument" // Unique ID for the test document

    @Before
    fun setUp() = runBlocking {
        // set up firebase store emulator
        firestore = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        // 10.0.2.2 is Android's IP address equivalent to localhost on host machine when emulating.
        // i.e. 10.0.2.2 (Android) <- 127.0.0.1 (Host PC // Machine we run instrument tests on)
        try {
            firebaseAuth.useEmulator("10.0.2.2", 9099)
            firestore.useEmulator("10.0.2.2", 8080)
        } catch (e: Exception) {
            // empty catch block to init emulators
            print(e)
        }
        val settings = FirebaseFirestoreSettings.Builder()
            .setLocalCacheSettings(MemoryCacheSettings.newBuilder().build()) // Use in-memory cache
            .build()
        firestore.firestoreSettings = settings

        firebaseAuth.signInWithEmailAndPassword("test@auth.com", "abcd1234").await()

        testList = ShoppingListItemList(0,
            listOf(
                ShoppingListItem(0,ShoppingItem(0,"shoppingItem/milk.png", "Milk", "Lovely milk", 2.30,
                    Category.GROCERIES, true, "amazingStore1"), 1),
                ShoppingListItem(0,ShoppingItem(0,"shoppingItem/bread.png", "Bread", "Today's bread, tomorrow!", 1.50,
                    Category.GROCERIES, false, "amazingStore2"), 1)
            ),
            "Don't forget to buy some cheese!"
        )
    }

    @After
    fun tearDown() {
        runBlocking {
            firestore.collection("shoppingLists").document(testDocumentId).delete().await()
        }
    }

    @Test
    fun testSaveToFirestore_savesDataCorrectly() = runTest {
        assertNotNull(firebaseAuth.currentUser)
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
