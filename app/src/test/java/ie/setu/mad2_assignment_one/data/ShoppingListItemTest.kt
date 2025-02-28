package ie.setu.mad2_assignment_one.data

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ShoppingListItemTest {

    private lateinit var shoppingItem: ShoppingItem
    private lateinit var shoppingListItem: ShoppingListItem

    @BeforeEach
    fun setUp() {
        // Setup common test data before each test
        shoppingItem = ShoppingItem(
            imageRes = 123,  // Example resource ID
            name = "Apple",
            description = "A fresh red apple",
            price = 0.99,
            availability = true
        )
        shoppingListItem = ShoppingListItem(shoppingItem, 5)
    }

    @Test
    fun getShoppingItem() {
        // Verify that the shoppingItem is correct
        assertEquals(shoppingItem, shoppingListItem.shoppingItem)
    }

    @Test
    fun getQuantity() {
        // Verify that the quantity is correctly initialized
        assertEquals(5, shoppingListItem.quantity)
    }

    @Test
    fun setQuantity() {
        // Test setting the quantity
        shoppingListItem.quantity = 10
        assertEquals(10, shoppingListItem.quantity)
    }

    @Test
    fun testCopy() {
        // Test the copy method
        val newShoppingListItem = shoppingListItem.copy(quantity = 10)
        assertEquals(10, newShoppingListItem.quantity)
        assertEquals(shoppingItem, newShoppingListItem.shoppingItem)
    }

    @Test
    fun testToString() {
        // Verify the string representation of the ShoppingListItem
        val expectedString = "ShoppingListItem(shoppingItem=ShoppingItem(imageRes=123, name=Apple, description=A fresh red apple, price=0.99, availability=true), quantity=5)"
        assertEquals(expectedString, shoppingListItem.toString())
    }

    @Test
    fun testHashCode() {
        // Test that hashCode works correctly
        val anotherItem = shoppingListItem.copy()
        assertEquals(shoppingListItem.hashCode(), anotherItem.hashCode())
    }

    @Test
    fun testEquals() {
        // Verify the equals method is working
        val anotherItem = shoppingListItem.copy()
        assertTrue(shoppingListItem == anotherItem)

        val differentItem = shoppingListItem.copy(quantity = 10)
        assertFalse(shoppingListItem == differentItem)
    }
}
