package com.example.polibee_v2.rent

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Avaliacao(
    val id: Int,
    val nomeAutor: String,
    val fotoAutorResId: Int,
    val nota: Float,
    val data: String,
    val comentario: String
) : Parcelable