package ie.setu.mad2_assignment_one.data

import androidx.annotation.DrawableRes

data class ShoppingItem(
    @DrawableRes val imageRes: Int,  // Resource ID for images
    val name: String,
    val description: String,
    val price: Double,
    val availability: Boolean
)
