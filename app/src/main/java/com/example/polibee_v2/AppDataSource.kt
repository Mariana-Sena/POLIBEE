package com.example.polibee_v2


import androidx.compose.runtime.mutableStateListOf
import com.example.polibee_v2.rent.Avaliacao

object AppDataSource {

    val allCompanies = mutableStateListOf(
        Company(
            id = 1,
            name = "Meliproa",
            imageResId = R.drawable.placeholder_image,
            location = "São Paulo - SP",
            rating = 4.8f,
            isFavorite = false,
            pricePerHour = 10.0f,
            description = "Fornecedor especializado em abelhas Jataí para polinização e meliponicultura.",
            availableHives = 100,
            beeType = "Jataí",
            avaliacoes = listOf(
                Avaliacao(1, "Ana B.", R.drawable.moca5, 5.0f, "Há 2 semanas", "Serviço impecável! As colmeias de Jataí chegaram saudáveis e o suporte foi excelente. Minha produção de morangos agradece."),
                Avaliacao(2, "Carlos Souza", R.drawable.homem1, 4.5f, "Há 3 semanas", "Muito bom, as abelhas são bem ativas. Apenas um pequeno atraso na entrega, mas a qualidade compensou.")
            )
        ),

        Company(
            id = 2,
            name = "Melipôlis",
            imageResId = R.drawable.melipolis,
            location = "Campinas - SP",
            rating = 4.5f,
            isFavorite = false,
            pricePerHour = 12.0f,
            description = "Oferecemos colmeias de Uruçu e Mandaçaia para polinização em larga escala.",
            availableHives = 80,
            beeType = "Uruçu",
            avaliacoes = listOf(
                Avaliacao(4, "Fernando Braga", R.drawable.homem2, 4.5f, "Há 2 meses", "As colmeias de Uruçu são fantásticas. Tive um aumento significativo na produtividade do meu maracujá.")
            )
        ),
        Company(
            id = 3,
            name = "Néctar Bravio",
            imageResId = R.drawable.bravio,
            location = "São Paulo - SP",
            rating = 4.8f,
            isFavorite = false,
            pricePerHour = 10.0f,
            description = "Referência em abelhas Jataí para produtores que buscam desempenho na polinização e sucesso na meliponicultura.",
            availableHives = 100,
            beeType = "Jataí",
            avaliacoes = listOf(
                Avaliacao(1, "Roberto Almeida", R.drawable.homem3, 5.0f, "Há 2 meses", "O diferencial foi o atendimento. Tiraram todas as minhas dúvidas sobre o manejo das Mandaçaias. A equipe é muito experiente e o resultado na lavoura de abacate foi ótimo."),
                Avaliacao(2, "Camila Ribeiro", R.drawable.moca1, 4.8f, "Há 1 semana", "Experiência excelente! Abelhas fortes e saudáveis. Minha horta agradece. Recomendo!"),
            )
        ),

        Company(
            id = 4,
            name = "Meliflor",
            imageResId = R.drawable.meliflor,
            location = "Sorocaba - SP",
            rating = 4.7f,
            isFavorite = false,
            pricePerHour = 11.0f,
            description = "Especialistas em Mandaçaia para a polinização de cafezais. Aumente a qualidade e o volume dos seus grãos com nossas colmeias fortes e bem manejadas.",
            availableHives = 120,
            beeType = "Mandaçaia",
            avaliacoes = listOf(
                Avaliacao(5, "Juliana Costa", R.drawable.moca2, 5.0f, "Há 1 mês", "Contratei para a florada do meu café. O resultado foi surpreendente, grãos mais uniformes e cheios. Atendimento nota 10."),
                Avaliacao(6, "Ricardo Alves", R.drawable.homem4, 4.5f, "Há 1 mês", "Colmeias de Mandaçaia muito boas. O preço é um pouco acima da média, mas a qualidade das abelhas justifica o investimento.")
            )
        ),

        Company(
            id = 5,
            name = "EcoColmeia",
            imageResId = R.drawable.ecocolmeia,
            location = "Ribeirão Preto - SP",
            rating = 4.9f,
            isFavorite = false,
            pricePerHour = 15.0f,
            description = "Soluções em polinização para estufas e hortas urbanas. Trabalhamos com Jataí e Mirim, ideais para espaços menores e culturas delicadas como tomate e pimentão.",
            availableHives = 75,
            beeType = "Mirim",
            avaliacoes = listOf(
                Avaliacao(7, "Lúcia Martins", R.drawable.moca3, 5.0f, "Há 3 semanas", "Perfeito para minha horta em estufa! As abelhas Mirim são dóceis e fizeram um trabalho incrível nos meus tomateiros. Recomendo muito!"),
                Avaliacao(8, "Pedro Gonçalves", R.drawable.homem5, 4.8f, "Há 5 semanas", "Atendimento diferenciado. Eles realmente entendem do assunto e me ajudaram a escolher a melhor abelha para o meu espaço. Resultados visíveis.")
            )
        ),

        Company(
            id = 6,
            name = "Essência Silvestre",
            imageResId = R.drawable.essencia,
            location = "Atibaia - SP",
            rating = 4.6f,
            isFavorite = false,
            pricePerHour = 9.50f,
            description = "Polinização sustentável para produtores de morango e flores. Nossas abelhas Uruçu-Amarela garantem frutos maiores e mais doces, respeitando o meio ambiente.",
            availableHives = 150,
            beeType = "Uruçu-Amarela",
            avaliacoes = listOf(
                Avaliacao(9, "Mariana Lima", R.drawable.moca4, 5.0f, "Há 1 semana", "Parceria de sucesso! As 'guardiãs' são incríveis. Meus morangos nunca estiveram tão bonitos e saborosos. Contratarei de novo na próxima safra."),
                Avaliacao(10, "Jorge Andrade", R.drawable.homem6, 4.0f, "Há 4 semanas", "Bom serviço e as abelhas são de qualidade. A comunicação poderia ser um pouco mais proativa, mas o resultado final foi positivo.")
            )
        ),
    )

    val companyHistory = mutableStateListOf<Company>()

    fun addCompanyToHistory(company: Company) {
        companyHistory.removeIf { it.id == company.id }
        companyHistory.add(0, company)
    }

    fun toggleFavoriteStatus(companyId: Int) {
        val companyIndex = allCompanies.indexOfFirst { it.id == companyId }
        if (companyIndex != -1) {
            val oldCompany = allCompanies[companyIndex]
            val newCompany = oldCompany.copy(isFavorite = !oldCompany.isFavorite)
            allCompanies[companyIndex] = newCompany
        }
    }
}