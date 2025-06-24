package com.example.polibee_v2.premium

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.polibee_v2.nav.FavoritesActivity
import com.example.polibee_v2.nav.HistoryActivity
import com.example.polibee_v2.MainActivity
import com.example.polibee_v2.PolibeeOrange
import com.example.polibee_v2.R
import com.example.polibee_v2.ui.theme.Polibee_v2Theme
import com.example.polibee_v2.access.PolibeeDarkGreen
import com.example.polibee_v2.montserratFamily
import com.example.polibee_v2.nav.ProfileActivity


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
                        when (index) {
                            0 -> startActivity(Intent(this, MainActivity::class.java))
                            1 -> startActivity(Intent(this, HistoryActivity::class.java))
                            2 -> startActivity(Intent(this, FavoritesActivity::class.java))
                            3 -> startActivity(Intent(this, ProfileActivity::class.java))
                        }
                        finish()
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
        containerColor = PolibeeDarkGreen,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp) // Altura expandida para abrigar a logo e os cards de preço
                    .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                    .background(PolibeeDarkGreen)
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 16.dp, top = 24.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.seta_voltar),
                        contentDescription = "Voltar",
                        modifier = Modifier.size(30.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 5.dp, bottom = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(5.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo_pollinate),
                            contentDescription = "Polibee Logo",
                            modifier = Modifier.size(100.dp)
                        )
                        Spacer(modifier = Modifier.width(0.dp))
                    }
                    Text(
                        text = "Selecione uma opção:",
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 0.dp),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    // Seção de Preços (Cards Mensal e Anual)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp), // Padding lateral para os cards
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

                        // Card Anual com Badge "MELHOR PREÇO" acima dele
                        Box(
                            contentAlignment = Alignment.TopCenter,
                            modifier = Modifier.weight(1f) // Garante que o Box ocupe seu espaço na Row
                        ) {
                            Box(
                                modifier = Modifier
                                    .offset(y = (-15).dp)
                                    .clip(RoundedCornerShape(bottomStart = 8.dp, topEnd = 8.dp))
                                    .background(PolibeeOrange)
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                                    .zIndex(1f)
                            ) {
                                Text(
                                    text = "MELHOR PREÇO",
                                    fontFamily = montserratFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp,
                                    color = Color.White
                                )
                            }
                            PremiumPlanCard(
                                planType = "Anual",
                                price = "R$ 26,66",
                                totalPrice = "R$ 320,00 no ano",
                                isAnnual = true,
                                onClick = { onSelectPlan("Anual") }
                            )
                        }
                    }
                }
            }
        },
        bottomBar = {
            val items = listOf("Home", "Histórico", "Favoritos", "Perfil")
            val icons = listOf(
                R.drawable.home,
                R.drawable.clock,
                R.drawable.heart,
                R.drawable.profile
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
            ) {
                NavigationBar(
                    containerColor = PolibeeDarkGreen,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(horizontal = 0.dp)
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
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Image(
                painter = painterResource(id = R.drawable.favo),
                contentDescription = "Decorativo Esquerdo",
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = (-60).dp, y = 60.dp)
                    .size(400.dp, 350.dp)
                    .zIndex(-1f),
                contentScale = ContentScale.FillBounds
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Benefícios do Abelha Rainha",
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = PolibeeDarkGreen,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 37.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    BenefitRow(text = "Selo \"Parceiro Abelha Rainha\" para credibilidade")
                    BenefitRow(text = "5% de taxa em aluguéis e vendas")
                    BenefitRow(text = "Cobertura para perdas de enxames")
                    BenefitRow(text = "Garantia de satisfação de polinização (ou crédito para próxima temporada)")
                    BenefitRow(text = "1 frete grátis por mês para transporte de colmeias ")
                    BenefitRow(text = "Agricultores: parcelamento em até 10x sem juros; melipolicultores: Saque sem taxas")
                    BenefitRow(text = "Cursos curtos sobre:\n" +
                            "- Melhores práticas apícolas;\n" +
                            "- Aumento de produtividade agrícola, dentre outros.")
                    BenefitRow(text = "Grupo fechado com especialistas\n" +
                            "- Eventos trimestrais online;\n" +
                            "- Acesso a Comunidade no WhatsApp.")
                    BenefitRow(text = "Certificado digital de impacto ambiental.")
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun PremiumPlanCard(
    planType: String, // "Mensal" ou "Anual"
    price: String, // "R$ 34,90"
    totalPrice: String = "", // "R$ 320,00 no ano" (apenas para Anual)
    isAnnual: Boolean, // Booleano para aplicar a borda laranja
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
                    color = PolibeeDarkGreen
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = price + "/Mês", // Adiciona "/Mês" ao preço
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.ExtraBold,
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
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun BenefitRow(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.check_mark),
            contentDescription = "Check Mark",
            modifier = Modifier.size(24.dp),
            colorFilter = ColorFilter.tint(PolibeeOrange)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            fontFamily = montserratFamily,
            fontSize = 16.sp,
            color = PolibeeDarkGreen
        )
    }
}