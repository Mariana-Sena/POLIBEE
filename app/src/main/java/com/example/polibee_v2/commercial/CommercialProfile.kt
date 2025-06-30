package com.example.polibee_v2.commercial

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommercialProfile(
    val id: String, // Um ID único para o perfil
    val imageUri: String?, // URI da imagem do perfil (nullable, pois é opcional/futuro)
    val companyName: String,
    val cpfCnpj: String,
    val description: String
) : Parcelable