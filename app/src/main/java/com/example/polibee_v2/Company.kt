// src/main/java/com/example/polibee_v2/Company.kt
package com.example.polibee_v2

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Company(
    val id: Int,
    val name: String,
    val imageResId: Int, // Imagem da empresa ou colmeia
    val location: String,
    val rating: Float,
    var isFavorite: Boolean = false,
    val pricePerHour: Float, // Exemplo de preço
    val description: String,
    val availableHives: Int, // Quantidade de colmeias disponíveis
    val beeType: String // Tipo de abelha que a empresa oferece
) : Parcelable