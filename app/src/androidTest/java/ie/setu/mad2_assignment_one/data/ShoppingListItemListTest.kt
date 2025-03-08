package ie.setu.mad2_assignment_one.data

import android.content.Context
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ShoppingListItemListTest {

    private lateinit var context: Context
    private lateinit var testList: ShoppingListItemList
    private val testFileName = "shopping_list.json"

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        testList = ShoppingListItemList(
            listOf(
                ShoppingListItem(ShoppingItem(0, "Milk", "Lovely milk", 2.30, true), 1),
                ShoppingListItem(ShoppingItem(0, "Bread", "Today's bread, tomorrow!", 1.50, false), 1)
            )
        )
    }

    @Test
    fun testSaveToStorage_createsFileWithCorrectContent() {
        // Save the shopping list to storage
        ShoppingListItemList.saveToStorage(context, testList)

        // Check if file exists
        val file = File(context.filesDir, testFileName)
        assertTrue("File should exist after saving", file.exists())

        // Check file content
        val savedJson = file.readText()
        val expectedJson = Json.encodeToString(testList)
        assertEquals("Saved JSON should match expected JSON", expectedJson, savedJson)
    }

    @Test
    fun testReadFromStorage_returnsCorrectData() {
        // Save the shopping list first
        ShoppingListItemList.saveToStorage(context, testList)

        // Read back the data
        val loadedList = ShoppingListItemList.readFromStorage(context)

        // Check if the data matches
        assertNotNull("Loaded list should not be null", loadedList)
        assertEquals("Loaded list should match the saved list", testList.list, loadedList?.list)
    }

    @Test
    fun testReadFromStorage_returnsNullWhenFileDoesNotExist() {
        // Ensure file does not exist before the test
        val file = File(context.filesDir, testFileName)
        if (file.exists()) file.delete()

        // Try to read from storage
        val loadedList = ShoppingListItemList.readFromStorage(context)

        // It should return null
        assertNull("Should return null when file does not exist", loadedList)
    }

    @Test
    fun testReadFromStorage_handlesCorruptedFileGracefully() {
        // Write invalid JSON to the file
        val file = File(context.filesDir, testFileName)
        file.writeText("{ invalid json }")

        // Try to read from storage
        val loadedList = ShoppingListItemList.readFromStorage(context)

        // It should return null
        assertNull("Should return null when file contains invalid JSON", loadedList)
    }
}
