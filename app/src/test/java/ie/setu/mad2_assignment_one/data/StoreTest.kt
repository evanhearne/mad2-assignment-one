package ie.setu.mad2_assignment_one.data

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class StoreTest {

    private lateinit var store: Store
    private lateinit var storeCopy: Store

    @BeforeEach
    fun setUp() {
        store = Store(
            storeId = "001",
            name = "SuperMart",
            address = "123 Main Street, Dublin, Ireland",
            longitude = -6.2603,
            latitude = 53.3498,
            openingHours = listOf(
                "Monday-Friday: 08:00 - 20:00",
                "Saturday: 09:00 - 18:00",
                "Sunday: 10:00 - 16:00"
            ),
            phoneNumber = "+353 1 234 5678"
        )
        storeCopy = store.copy()
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun getStoreId() {
        assertEquals("001", store.storeId)
    }

    @Test
    fun getName() {
        assertEquals("SuperMart", store.name)
    }

    @Test
    fun getAddress() {
        assertEquals("123 Main Street, Dublin, Ireland", store.address)
    }

    @Test
    fun getLongitude() {
        assertEquals(-6.2603, store.longitude)
    }

    @Test
    fun getLatitude() {
        assertEquals(53.3498, store.latitude)
    }

    @Test
    fun getOpeningHours() {
        assertEquals(3, store.openingHours.size)
        assertEquals("Monday-Friday: 08:00 - 20:00", store.openingHours[0])
    }

    @Test
    fun getPhoneNumber() {
        assertEquals("+353 1 234 5678", store.phoneNumber)
    }

    @Test
    fun copy() {
        assertEquals(store, storeCopy)
    }

    @Test
    fun testToString() {
        val expectedString = "Store(storeId=001, name=SuperMart, address=123 Main Street, Dublin, Ireland, " +
                "longitude=-6.2603, latitude=53.3498, openingHours=[Monday-Friday: 08:00 - 20:00, " +
                "Saturday: 09:00 - 18:00, Sunday: 10:00 - 16:00], phoneNumber=+353 1 234 5678)"
        assertEquals(expectedString, store.toString())
    }

    @Test
    fun testHashCode() {
        assertEquals(store.hashCode(), storeCopy.hashCode())
    }

    @Test
    fun testEquals() {
        assertTrue(store == storeCopy)
        assertFalse(store == store.copy(storeId = "002"))
    }
}
