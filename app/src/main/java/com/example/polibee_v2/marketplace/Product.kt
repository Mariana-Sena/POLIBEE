package com.example.polibee_v2.marketplace

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: String,
    val name: String,
    val companyName: String,
    val price: Double,
    val installmentOption: String,
    val description: String,
    val imageUrl: Int,
    var isFavorite: Boolean = false
) : Parcelable

@Parcelize
data class Review(
    val id: String,
    val author: String,
    val rating: Int,
    val comment: String,
    val date: String
) : Parcelable


@Parcelize
data class CartItem(
    val product: Product,
    var quantity: Int
) : Parcelable