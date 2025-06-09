// src/main/java/com/example/polibee_v2/PremiumOverviewActivity.kt
package com.example.polibee_v2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polibee_v2.ui.theme.Polibee_v2Theme
import com.example.polibee_v2.access.PolibeeDarkGreen // Verifique se este import está correto


class PremiumOverviewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Polibee_v2Theme {
                PremiumOverviewScreen(
                    onBackClick = { finish() },
                    onSelectPlan = { planType ->
                        val intent = Intent(this, PaymentActivity::class.java).apply {
                            putExtra("selectedPlanType", planType)
                        }
                        startActivity(intent)
                    },
                    onBottomNavItemClick = { index ->
                        // Lógica de navegação do menu inferior (igual às outras Activities)
                        when (index) {
                            0 -> startActivity(Intent(this, MainActivity::class.java))
                            1 -> startActivity(Intent(this, HistoryActivity::class.java))
                            2 -> startActivity(Intent(this, FavoritesActivity::class.java))
                            3 -> startActivity(Intent(this, ProfileActivity::class.java))
                        }
                        finish() // Finaliza esta activity se navegar para outra tela principal
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PremiumOverviewScreen(
    onBackClick: () -> Unit,
    onSelectPlan: (String) -> Unit, // Callback para quando um plano é selecionado ou "Seja Premium" clicado
    onBottomNavItemClick: (Int) -> Unit
) {
    var selectedBottomNavItem by remember { mutableStateOf(-1) }

    Scaffold(
        containerColor = Color.Transparent, // Fundo transparente para o Scaffold
        topBar = {
            // --- Custom TopBar conforme o protótipo: Logo POLIBEE e "MELHOR PREÇO" ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp) // Altura expandida para abrigar a logo e badge
                    .background(PolibeeDarkGreen) // Cor de fundo da barra superior
                    .clip(RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)) // Cantos inferiores arredondados
            ) {
                // Botão de Voltar (canto superior esquerdo)
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.align(Alignment.TopStart).padding(start = 16.dp, top = 24.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.seta_voltar), // Certifique-se que 'seta_voltar' existe
                        contentDescription = "Voltar",
                        modifier = Modifier.size(30.dp),
                        colorFilter = ColorFilter.tint(Color.White) // Seta branca
                    )
                }

                // Logo POLIBEE (texto com cores diferentes e ícone) centralizada
                Row(
                    modifier = Modifier.align(Alignment.Center).padding(top = 24.dp), // Ajuste o padding superior para centralizar melhor a Row
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_pollinate), // Certifique-se que 'polibee_icon' (a abelha) existe
                        contentDescription = "Polibee Logo",
                        modifier = Modifier.size(50.dp) // Ajuste o tamanho do ícone
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = PolibeeOrange)) { append("POLI") }
                            withStyle(style = SpanStyle(color = Color.White)) { append("BEE") }
                        },
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 36.sp // Fonte maior para a logo
                    )
                }

                // Badge "MELHOR PREÇO" (canto superior direito)
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 24.dp, end = 16.dp)
                        .clip(RoundedCornerShape(bottomStart = 8.dp, topEnd = 8.dp)) // Forma do badge
                        .background(PolibeeOrange)
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "MELHOR PREÇO",
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }
            }
        },
        bottomBar = {
            // --- Barra de Navegação Inferior (Menu) ---
            val items = listOf("Home", "Histórico", "Favoritos", "Perfil")
            val icons = listOf(R.drawable.home, R.drawable.clock, R.drawable.heart, R.drawable.profile)
            NavigationBar(
                containerColor = PolibeeDarkGreen,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedBottomNavItem == index,
                        onClick = {
                            selectedBottomNavItem = index
                            onBottomNavItemClick(index)
                        },
                        icon = {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Image(
                                    painter = painterResource(id = icons[index]),
                                    contentDescription = item,
                                    modifier = Modifier.size(24.dp),
                                    colorFilter = ColorFilter.tint(Color.White)
                                )
                                if (selectedBottomNavItem == index) {
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Box(
                                        modifier = Modifier
                                            .size(6.dp)
                                            .clip(CircleShape)
                                            .background(PolibeeOrange)
                                    )
                                }
                            }
                        },
                        label = null,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Unspecified,
                            unselectedIconColor = Color.Unspecified,
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White) // Fundo branco para o conteúdo principal
        ) {
            // Favos Laterais (background decorativo)
            Image(
                painter = painterResource(id = R.drawable.favo), // Certifique-se que 'honeycomb_left' existe
                contentDescription = "Decorativo",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .offset(x = (-80).dp) // Deslocamento para fora da tela, ajuste conforme sua imagem
                    .size(200.dp, 300.dp), // Tamanho da imagem, ajuste conforme sua imagem
                contentScale = ContentScale.Fit
            )
            Image(
                painter = painterResource(id = R.drawable.favo_direito), // Certifique-se que 'honeycomb_right' existe
                contentDescription = "Decorativo",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .offset(x = 80.dp) // Deslocamento para fora da tela, ajuste conforme sua imagem
                    .size(200.dp, 300.dp), // Tamanho da imagem, ajuste conforme sua imagem
                contentScale = ContentScale.Fit
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp)) // Espaçamento abaixo da topBar customizada

                // Seção de Preços (Cards Mensal e Anual)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp), // Padding lateral para os cards
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Card Mensal
                    PremiumPlanCard(
                        planType = "Mensal",
                        price = "R$ 34,90",
                        isAnnual = false,
                        onClick = { onSelectPlan("Mensal") }
                    )
                    // Card Anual (com badge de melhor preço e borda laranja)
                    PremiumPlanCard(
                        planType = "Anual",
                        price = "R$ 26,66",
                        totalPrice = "Total R$ 320,00 no ano", // Texto adicional para o plano anual
                        isAnnual = true,
                        onClick = { onSelectPlan("Anual") }
                    )
                }

                // Seção de Benefícios do Plano Premium
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "Benefícios do Plano Premium",
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = PolibeeDarkGreen,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp) // Espaçamento entre os tópicos
                ) {
                    BenefitRow(text = "Acesso prioritário a novas áreas de locação de colmeias")
                    BenefitRow(text = "Descontos exclusivos em produtos apícolas da nossa loja")
                    BenefitRow(text = "Consultoria especializada para otimização da produção de mel")
                    BenefitRow(text = "Relatórios detalhados de saúde das colmeias e visitas programadas")
                    BenefitRow(text = "Suporte 24/7 para emergências e dúvidas")
                    BenefitRow(text = "Participação em workshops e eventos exclusivos para membros premium")
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Botão "Seja Premium"
                Button(
                    onClick = { onSelectPlan("Indefinido") }, // Você pode ajustar isso para um plano padrão ou forçar a seleção nos cards
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(horizontal = 24.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PolibeeOrange)
                ) {
                    Text(
                        "Seja Premium",
                        color = Color.White,
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
                Spacer(modifier = Modifier.height(24.dp)) // Espaço para o rodapé
            }
        }
    }
}

// Composable para os Cards de Plano Premium (Mensal/Anual)
@Composable
fun PremiumPlanCard(
    planType: String, // "Mensal" ou "Anual"
    price: String, // "R$ 34,90"
    totalPrice: String = "", // "Total R$ 320,00 no ano" (apenas para Anual)
    isAnnual: Boolean, // Booleano para aplicar a borda laranja e o badge
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(160.dp) // Largura exata conforme protótipo
            .height(180.dp) // Altura exata conforme protótipo
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp), // Cantos arredondados do card
        colors = CardDefaults.cardColors(containerColor = Color.White), // Fundo branco do card
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), // Sombra
        border = if (isAnnual) BorderStroke(2.dp, PolibeeOrange) else null // Borda laranja para o plano anual
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp), // Padding interno para o texto
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center // Centraliza o conteúdo verticalmente
            ) {
                Text(
                    text = planType,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = PolibeeDarkGreen // Cor do texto "Mensal"/"Anual"
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = price + "/Mês", // Adiciona "/Mês" ao preço
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.ExtraBold, // Mais negrito para o preço
                    fontSize = 24.sp, // Tamanho maior para o preço
                    color = PolibeeOrange, // Cor laranja para o preço
                    textAlign = TextAlign.Center
                )
                if (totalPrice.isNotBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = totalPrice,
                        fontFamily = montserratFamily,
                        fontSize = 12.sp,
                        color = Color.Gray // Cor cinza para o total
                    )
                }
            }
            if (isAnnual) {
                // Badge "MELHOR PREÇO" posicionado no canto superior direito do card, saindo um pouco para fora
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 10.dp, y = (-10).dp) // Deslocamento para fora do card, ajuste conforme o protótipo
                        .clip(RoundedCornerShape(bottomStart = 8.dp, topEnd = 8.dp)) // Forma do badge
                        .background(PolibeeOrange) // Fundo laranja do badge
                        .padding(horizontal = 8.dp, vertical = 4.dp) // Padding interno do badge
                ) {
                    Text(
                        text = "MELHOR PREÇO",
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        color = Color.White // Texto branco do badge
                    )
                }
            }
        }
    }
}

// Composable para cada linha de benefício com ícone
@Composable
fun BenefitRow(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.check_mark), // Certifique-se que 'check_mark' existe
            contentDescription = "Check Mark",
            modifier = Modifier.size(24.dp), // Tamanho do ícone de check
            colorFilter = ColorFilter.tint(PolibeeOrange) // Cor laranja para o ícone
        )
        Spacer(modifier = Modifier.width(16.dp)) // Espaçamento entre ícone e texto
        Text(
            text = text,
            fontFamily = montserratFamily,
            fontSize = 16.sp,
            color = PolibeeDarkGreen // Cor do texto do benefício
        )
    }
}