package ie.setu.mad2_assignment_one.data

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ShoppingItemTest {

    private lateinit var shoppingItem: ShoppingItem

    @BeforeEach
    fun setUp() {
        shoppingItem = ShoppingItem(
            id = 0,
            imageRes = "/shopping_items/test_item.png",
            name = "Sample Item",
            description = "This is a test item.",
            price = 19.99,
            category = Category.GROCERIES,
            availability = true,
            storeId = "NewStore1"
        )
    }

    @Test
    fun getStoreID() {
        assertEquals("NewStore1", shoppingItem.storeId)
    }

    @Test
    fun getImageRes() {
        assertEquals("/shopping_items/test_item.png", shoppingItem.imageRes)
    }

    @Test
    fun getName() {
        assertEquals("Sample Item", shoppingItem.name)
    }

    @Test
    fun getDescription() {
        assertEquals("This is a test item.", shoppingItem.description)
    }

    @Test
    fun getPrice() {
        assertEquals(19.99, shoppingItem.price, 0.01)
    }

    @Test
    fun getCategory() {
        assertEquals(Category.GROCERIES, shoppingItem.category)
    }

    @Test
    fun getAvailability() {
        assertTrue(shoppingItem.availability)
    }

    @Test
    fun testCopy() {
        val newItem = shoppingItem.copy(price = 25.99, availability = false)
        assertEquals("/shopping_items/test_item.png", newItem.imageRes)
        assertEquals("Sample Item", newItem.name)
        assertEquals("This is a test item.", newItem.description)
        assertEquals(25.99, newItem.price, 0.01)
        assertFalse(newItem.availability)
    }

    @Test
    fun testToString() {
        val expectedString = "ShoppingItem(id=0, imageRes=/shopping_items/test_item.png, name=Sample Item, description=This is a test item., price=19.99, category=GROCERIES, availability=true, storeId=NewStore1)"
        assertEquals(expectedString, shoppingItem.toString())
    }

    @Test
    fun testHashCode() {
        val identicalItem = ShoppingItem(
            id = 0,
            imageRes = "/shopping_items/test_item.png",
            name = "Sample Item",
            description = "This is a test item.",
            price = 19.99,
            category = Category.GROCERIES,
            availability = true,
            storeId = "NewStore1"
        )
        assertEquals(shoppingItem.hashCode(), identicalItem.hashCode())
    }

    @Test
    fun testEquals() {
        val identicalItem = ShoppingItem(
            id = 0,
            imageRes = "/shopping_items/test_item.png",
            name = "Sample Item",
            description = "This is a test item.",
            price = 19.99,
            category = Category.GROCERIES,
            availability = true,
            storeId = "NewStore1"
        )
        assertEquals(shoppingItem, identicalItem)

        val differentItem = ShoppingItem(imageRes = "/shopping_items/test_item.png", name="Other Item", description = "Different description.", price=9.99, category = Category.ELECTRONICS, availability = false)
        assertNotEquals(shoppingItem, differentItem)
    }
}