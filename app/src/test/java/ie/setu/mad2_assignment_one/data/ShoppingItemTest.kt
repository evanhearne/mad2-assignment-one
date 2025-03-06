package ie.setu.mad2_assignment_one.data

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ShoppingItemTest {

    private lateinit var shoppingItem: ShoppingItem

    @BeforeEach
    fun setUp() {
        shoppingItem = ShoppingItem(
            imageRes = 123,
            name = "Sample Item",
            description = "This is a test item.",
            price = 19.99,
            availability = true
        )
    }

    @Test
    fun getImageRes() {
        assertEquals(123, shoppingItem.imageRes)
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
    fun getAvailability() {
        assertTrue(shoppingItem.availability)
    }

    @Test
    fun testCopy() {
        val newItem = shoppingItem.copy(price = 25.99, availability = false)
        assertEquals(123, newItem.imageRes)
        assertEquals("Sample Item", newItem.name)
        assertEquals("This is a test item.", newItem.description)
        assertEquals(25.99, newItem.price, 0.01)
        assertFalse(newItem.availability)
    }

    @Test
    fun testToString() {
        val expectedString = "ShoppingItem(imageRes=123, name=Sample Item, description=This is a test item., price=19.99, availability=true)"
        assertEquals(expectedString, shoppingItem.toString())
    }

    @Test
    fun testHashCode() {
        val identicalItem = ShoppingItem(123, "Sample Item", "This is a test item.", 19.99, true)
        assertEquals(shoppingItem.hashCode(), identicalItem.hashCode())
    }

    @Test
    fun testEquals() {
        val identicalItem = ShoppingItem(123, "Sample Item", "This is a test item.", 19.99, true)
        assertEquals(shoppingItem, identicalItem)

        val differentItem = ShoppingItem(456, "Other Item", "Different description.", 9.99, false)
        assertNotEquals(shoppingItem, differentItem)
    }
}