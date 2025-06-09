// src/main/java/com/example/polibee_v2/AppDataSource.kt
package com.example.polibee_v2

import androidx.compose.runtime.mutableStateListOf

// Este objeto atuará como um "banco de dados" em memória para a sessão do app
// Em um app real, isso seria substituído por Room, Retrofit, etc.
object AppDataSource {

    // Lista canônica de todas as empresas disponíveis para aluguel.
    // É importante que esta lista seja a "fonte da verdade" para o status de favorito.
    val allCompanies = mutableStateListOf(
        Company(
            id = 1,
            name = "Meliproa",
            imageResId = R.drawable.placeholder_image,
            location = "São Paulo - SP",
            rating = 4.8f,
            isFavorite = false, // Status inicial
            pricePerHour = 10.0f,
            description = "Fornecedor especializado em abelhas Jataí para polinização e meliponicultura.",
            availableHives = 100,
            beeType = "Jataí"
        ),
        Company(
            id = 2,
            name = "Néctar Bravio",
            imageResId = R.drawable.placeholder_image,
            location = "Rio de Janeiro - RJ",
            rating = 4.5f,
            isFavorite = false,
            pricePerHour = 12.0f,
            description = "Oferecemos colmeias de Uruçu e Mandaçaia para polinização em larga escala.",
            availableHives = 80,
            beeType = "Uruçu"
        ),
        Company(
            id = 3,
            name = "Colmeias do Sul",
            imageResId = R.drawable.placeholder_image,
            location = "Porto Alegre - RS",
            rating = 4.9f,
            isFavorite = false,
            pricePerHour = 9.5f,
            description = "Especialistas em aluguel de colmeias de Mandaguari para agricultura orgânica.",
            availableHives = 120,
            beeType = "Mandaguari"
        )
    )

    // Histórico de empresas visitadas (mais recente primeiro)
    val companyHistory = mutableStateListOf<Company>()

    fun addCompanyToHistory(company: Company) {
        // Remove a empresa do histórico se já existir para movê-la para o topo
        companyHistory.removeIf { it.id == company.id }
        // Adiciona a empresa ao início da lista
        companyHistory.add(0, company)
    }

    // Alterna o status de favorito de uma empresa pelo ID
    fun toggleFavoriteStatus(companyId: Int) {
        val company = allCompanies.find { it.id == companyId }
        company?.let {
            it.isFavorite = !it.isFavorite // Inverte o status
            // O uso de mutableStateListOf faz com que a UI seja recomposta automaticamente
            // quando um item dentro da lista tem sua propriedade mutável alterada.
        }
    }
}