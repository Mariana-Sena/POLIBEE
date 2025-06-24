// src/main/java/com/example/polibee_v2/Company.kt
package com.example.polibee_v2

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Company(
    val id: Int,
    val name: String,
    val imageResId: Int,
    val location: String,
    val rating: Float,
    var isFavorite: Boolean = false,
    val pricePerHour: Float,
    val description: String,
    val availableHives: Int,
    val beeType: String
) : Parcelable
