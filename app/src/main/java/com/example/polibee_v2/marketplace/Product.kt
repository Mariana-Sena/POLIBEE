// src/main/java/com/example/polibee_v2/marketplace/Product.kt
package com.example.polibee_v2.marketplace

import android.os.Parcelable
import kotlinx.parcelize.Parcelize // Adicione esta linha

// Product data class
@Parcelize
data class Product(
    val id: String,
    val name: String,
    val companyName: String,
    val price: Double,
    val installmentOption: String, // e.g., "em até 10x de R$78,60 sem juros"
    val description: String,
    val imageUrl: Int, // R.drawable.product_image_name
    var isFavorite: Boolean = false // This will be managed
) : Parcelable



// Review data class (for product reviews)
@Parcelize
data class Review(
    val id: String,
    val author: String,
    val rating: Int, // 1 to 5 stars
    val comment: String,
    val date: String // e.g., "DD/MM/AAAA"
) : Parcelable

// CartItem data class
@Parcelize
data class CartItem(
    val product: Product,
    var quantity: Int // Mutable
) : Parcelable