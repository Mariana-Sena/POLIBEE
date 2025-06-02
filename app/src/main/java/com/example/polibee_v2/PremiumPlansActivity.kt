// src/main/java/com/example/polibee_v2/ChoosePlanActivity.kt
package com.example.polibee_v2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polibee_v2.ui.theme.Polibee_v2Theme

class PremiumPlansActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Polibee_v2Theme {
                ChoosePlanScreen(
                    onBackClick = { finish() },
                    onPlanSelected = { planType ->
                        val intent = Intent(this, PaymentActivity::class.java).apply {
                            putExtra("selectedPlanType", planType) // Passa o tipo de plano selecionado
                        }
                        startActivity(intent)
                    },
                    onBottomNavItemClick = { index ->
                        // Lógica de navegação do menu inferior (igual à MainActivity)
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
fun ChoosePlanScreen(
    onBackClick: () -> Unit,
    onPlanSelected: (String) -> Unit,
    onBottomNavItemClick: (Int) -> Unit
) {
    val context = LocalContext.current
    val montserratFamily = FontFamily(
        Font(R.font.montserrat_regular),
        Font(R.font.montserrat_bold, FontWeight.Bold)
    )
    var selectedBottomNavItem by remember { mutableStateOf(-1) }

    // Estado para controlar a animação de entrada dos cards
    var showCards by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        showCards = true // Ativa a animação quando a tela é composta
    }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            PollinatePlusHeader(showBack = true, onBackClick = onBackClick)
        },
        bottomBar = {
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
                .background(Color.White)
        ) {
            // Favos Laterais
            Image(
                painter = painterResource(id = R.drawable.favo_cortado),
                contentDescription = "Decorativo",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .offset(x = (-80).dp)
                    .size(200.dp, 300.dp),
                contentScale = ContentScale.Fit
            )
            Image(
                painter = painterResource(id = R.drawable.favo_direito),
                contentDescription = "Decorativo",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .offset(x = 80.dp)
                    .size(200.dp, 300.dp),
                contentScale = ContentScale.Fit
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Escolha seu Plano",
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = PolibeeDarkGreen,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Plano Anual (surge de cima para baixo)
                    AnimatedVisibility(
                        visible = showCards,
                        enter = slideInVertically(
                            initialOffsetY = { -it }, // Começa do topo
                            animationSpec = tween(durationMillis = 800)
                        ) + fadeIn(animationSpec = tween(durationMillis = 800))
                    ) {
                        PlanCard(
                            planType = "Anual",
                            pricePerMonth = "R$ 26,66",
                            totalPrice = "Total R$ 320,00",
                            discountText = "Desconto de 5%!",
                            discountBgColor = Color(0xFF4CAF50), // Verde
                            flowerResId = R.drawable.flower_blur,
                            onClick = { onPlanSelected("Anual") }
                        )
                    }

                    // Plano Mensal (surge de baixo para cima)
                    AnimatedVisibility(
                        visible = showCards,
                        enter = slideInVertically(
                            initialOffsetY = { it }, // Começa de baixo
                            animationSpec = tween(durationMillis = 800, delayMillis = 200) // Pequeno atraso
                        ) + fadeIn(animationSpec = tween(durationMillis = 800, delayMillis = 200))
                    ) {
                        PlanCard(
                            planType = "Mensal",
                            pricePerMonth = "R$ 34,90",
                            totalPrice = "", // Sem total para mensal
                            discountText = "Sem desconto :(",
                            discountBgColor = Color(0xFF9E9E9E), // Cinza
                            flowerResId = R.drawable.flower_blur,
                            onClick = { onPlanSelected("Mensal") }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PlanCard(
    planType: String,
    pricePerMonth: String,
    totalPrice: String,
    discountText: String,
    discountBgColor: Color,
    flowerResId: Int,
    onClick: () -> Unit
) {
    val montserratFamily = FontFamily(
        Font(R.font.montserrat_regular),
        Font(R.font.montserrat_bold, FontWeight.Bold)
    )

    Card(
        modifier = Modifier
            .width(160.dp) // Largura do cartão
            .height(220.dp) // Altura do cartão
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = PolibeeDarkGreen), // Fundo verde escuro
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Imagem de flor no canto (superior para anual, inferior para mensal - simulado por modificador)
            Image(
                painter = painterResource(id = flowerResId),
                contentDescription = "Flor Decorativa",
                modifier = Modifier
                    .size(80.dp)
                    .align(if (planType == "Anual") Alignment.TopEnd else Alignment.BottomStart), // Posiciona a flor
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(PolibeeOrange) // Cor da flor
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween // Para espaçar o conteúdo
            ) {
                // Conteúdo do Plano
                Text(
                    text = planType,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.White
                )
                Text(
                    text = pricePerMonth + "/Mês",
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.sp,
                    color = PolibeeOrange,
                    textAlign = TextAlign.Center
                )
                if (totalPrice.isNotBlank()) {
                    Text(
                        text = totalPrice,
                        fontFamily = montserratFamily,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }

                // Caixa de Desconto
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(discountBgColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = discountText,
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}