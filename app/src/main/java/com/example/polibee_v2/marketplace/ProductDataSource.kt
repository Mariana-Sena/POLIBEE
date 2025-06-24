package com.example.polibee_v2.marketplace

import com.example.polibee_v2.R
import androidx.compose.runtime.mutableStateListOf

object ProductDataSource {
    val allProducts = mutableStateListOf<Product>(
        Product(
            id = "prod001",
            name = "Fertilizante de Polinização Premium",
            companyName = "BioPolen Solutions",
            price = 125.50,
            installmentOption = "em até 10x de R$12,55 sem juros",
            description = "Fertilizante orgânico de alta performance, enriquecido com microelementos essenciais para otimizar a polinização assistida e a saúde das abelhas. Aumenta a produção de frutos e sementes.",
            imageUrl = R.drawable.produto,
            isFavorite = false
        ),
        Product(
            id = "prod002",
            name = "Kit de Ferramentas para Apicultura",
            companyName = "Apicultura Moderna",
            price = 350.00,
            installmentOption = "em até 10x de R$35,00 sem juros",
            description = "Conjunto completo de ferramentas para apicultores iniciantes e experientes, incluindo fumigador, raspador de mel, luvas e véu de proteção. Essencial para o manejo seguro e eficiente das colmeias.",
            imageUrl = R.drawable.produto,
            isFavorite = false
        ),
        Product(
            id = "prod003",
            name = "Sementes de Flores Atrativas (Mix)",
            companyName = "Jardim Polinizador",
            price = 45.90,
            installmentOption = "em até 5x de R$9,18 sem juros",
            description = "Mix selecionado de sementes de flores que atraem abelhas e outros polinizadores. Ideal para criar um ambiente favorável à polinização em jardins e lavouras. Floresce em diversas estações.",
            imageUrl = R.drawable.produto,
            isFavorite = false
        ),
        Product(
            id = "prod004",
            name = "Manual de Polinização Assistida",
            companyName = "EducaPolen",
            price = 89.90,
            installmentOption = "em até 5x de R$17,98 sem juros",
            description = "Guia completo com técnicas e melhores práticas para a polinização assistida, abordando desde a biologia das abelhas até a implementação de programas de polinização em larga escala. Para produtores e estudantes.",
            imageUrl = R.drawable.produto,
            isFavorite = false
        ),
        Product(
            id = "prod005",
            name = "Caixa de Colmeia Modelo Langstroth",
            companyName = "Lar das Abelhas",
            price = 580.00,
            installmentOption = "em até 10x de R$58,00 sem juros",
            description = "Colmeia padrão Langstroth, fabricada com madeira de reflorestamento e acabamento de alta qualidade. Design otimizado para o desenvolvimento saudável das colônias e fácil manuseio.",
            imageUrl = R.drawable.produto,
            isFavorite = false
        ),
        Product(
            id = "prod006",
            name = "Alimentador para Abelhas (Xarope)",
            companyName = "Doce Vida Apícola",
            price = 75.00,
            installmentOption = "em até 3x de R$25,00 sem juros",
            description = "Alimentador prático e durável para suprir abelhas com xarope ou água, essencial em períodos de escassez de néctar e pólen. Fácil de limpar e reabastecer.",
            imageUrl = R.drawable.produto,
            isFavorite = false
        ),
        Product(
            id = "prod007",
            name = "Macacão de Proteção para Apicultor",
            companyName = "Segurança Apícola",
            price = 280.00,
            installmentOption = "em até 10x de R$28,00 sem juros",
            description = "Macacão de proteção integral, com véu acoplado e zíperes reforçados. Confeccionado em tecido respirável e resistente, oferece máxima segurança e conforto durante o manejo das abelhas.",
            imageUrl = R.drawable.produto,
            isFavorite = false
        ),
        Product(
            id = "prod008",
            name = "Pólen Apícola Desidratado (250g)",
            companyName = "Saúde da Colmeia",
            price = 99.90,
            installmentOption = "em até 5x de R$19,98 sem juros",
            description = "Pólen apícola 100% natural e desidratado, rico em proteínas, vitaminas e minerais. Ideal para consumo humano como superalimento ou suplemento para abelhas em épocas de menor florada.",
            imageUrl = R.drawable.produto,
            isFavorite = false
        ),
        Product(
            id = "prod009",
            name = "Fumaça Líquida para Fumigador",
            companyName = "Fumaça Segura",
            price = 60.00,
            installmentOption = "em até 3x de R$20,00 sem juros",
            description = "Frasco de fumaça líquida concentrada para fumigadores de apicultura. Produz fumaça densa e duradoura, calmando as abelhas sem prejudicá-las. Livre de aditivos nocivos.",
            imageUrl = R.drawable.produto,
            isFavorite = false
        ),
        Product(
            id = "prod010",
            name = "Extrator de Mel Manual (3 Quadros)",
            companyName = "Mel Dourado Equipamentos",
            price = 950.00,
            installmentOption = "em até 10x de R$95,00 sem juros",
            description = "Extrator de mel manual em aço inoxidável, com capacidade para 3 quadros. Ideal para apicultores de pequeno e médio porte. Fácil de operar e limpar, garante a extração higiênica do mel.",
            imageUrl = R.drawable.produto,
            isFavorite = false
        ),
        Product(
            id = "prod011",
            name = "Rainha Apis Mellifera Ligustica",
            companyName = "Rainhas Selecionadas",
            price = 180.00,
            installmentOption = "em até 3x de R$60,00 sem juros",
            description = "Rainha jovem da espécie Apis Mellifera Ligustica, de linhagem selecionada para alta produtividade e temperamento dócil. Essencial para o fortalecimento e expansão da sua colmeia.",
            imageUrl = R.drawable.produto,
            isFavorite = false
        ),
        Product(
            id = "prod012",
            name = "Cera Alveolada para Abelhas (10un)",
            companyName = "Cera Pura Apícola",
            price = 110.00,
            installmentOption = "em até 5x de R$22,00 sem juros",
            description = "Pacote com 10 folhas de cera alveolada pura, pronta para ser utilizada nos quadros da colmeia. Estimula a construção de favos e o armazenamento de mel e pólen pelas abelhas.",
            imageUrl = R.drawable.produto,
            isFavorite = false
        )
    )

    val dummyReviews = mapOf(
        "prod001" to mutableStateListOf(
            Review("rev001", "Ana Silva", 5, "Excelente fertilizante, minhas plantas nunca estiveram tão produtivas!", "01/05/2024"),
            Review("rev002", "João Souza", 4, "Bom produto, mas o cheiro é um pouco forte. O resultado compensa.", "15/04/2024")
        ),
        "prod002" to mutableStateListOf(
            Review("rev003", "Maria Oliveira", 5, "Kit super completo e de ótima qualidade, recomendo para todos apicultores.", "20/05/2024"),
            Review("rev004", "Carlos Santos", 3, "As luvas são um pouco apertadas, mas as outras ferramentas são boas.", "10/05/2024")
        )
    )

    fun toggleFavorite(product: Product) {
        val index = allProducts.indexOfFirst { it.id == product.id }
        if (index != -1) {
            allProducts[index] = allProducts[index].copy(isFavorite = !allProducts[index].isFavorite)
        }
    }

    fun getProductById(id: String): Product? {
        return allProducts.find { it.id == id }
    }
}
