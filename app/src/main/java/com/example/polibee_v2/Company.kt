// Conteúdo do arquivo: Company.kt
package com.example.polibee_v2

import android.os.Parcelable
import com.example.polibee_v2.rent.Avaliacao
import kotlinx.parcelize.Parcelize

@Parcelize
data class Company(
    val id: Int,
    val name: String,
    val imageResId: Int,
    val location: String,
    val rating: Float,
    var isFavorite: Boolean,
    val pricePerHour: Float,
    val description: String,
    val availableHives: Int,
    val beeType: String,
    val avaliacoes: List<Avaliacao>
) : Parcelable