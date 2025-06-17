// src/main/java/com/example/polibee_v2/PremiumBenefitsActivity.kt
package com.example.polibee_v2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polibee_v2.access.PolibeeDarkGreen
import com.example.polibee_v2.profile.ProfileActivity
import com.example.polibee_v2.ui.theme.Polibee_v2Theme

class PremiumBenefitsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Polibee_v2Theme {
                PremiumBenefitsScreen(
                    onBackClick = { finish() },
                    onAdvanceClick = {
                        startActivity(Intent(this, PremiumPlansActivity::class.java))
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

//Composable Reutilizável para o Cabeçalho (Logo e Botão Voltar)
//Para evitar duplicação, criamos um Composable para o cabeçalho "POLLINATE+" com o botão de voltar.

@Composable
fun PollinatePlusHeader(
    modifier: Modifier = Modifier,
    showBack: Boolean,
    onBackClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp) // Altura para o cabeçalho
            .background(Color.Transparent) // Pode ser transparente ou branco, dependendo do design
    ) {
        if (showBack) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.seta_voltar),
                    contentDescription = "Voltar",
                    modifier = Modifier.size(30.dp),
                    colorFilter = ColorFilter.tint(PolibeeDarkGreen) // Cor da seta
                )
            }
        }
        Row(
            modifier = Modifier.align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_bgwhite), // Sua logo
                contentDescription = "Pollinate+ Logo",
                modifier = Modifier.size(width = 40.dp, height = 40.dp) // Ajuste o tamanho
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "POLLINATE+",
                fontFamily = montserratFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = PolibeeDarkGreen
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PremiumBenefitsScreen(
    onBackClick: () -> Unit,
    onAdvanceClick: () -> Unit,
    onBottomNavItemClick: (Int) -> Unit
) {
    val context = LocalContext.current
    val montserratFamily = FontFamily(
        Font(R.font.montserrat_regular),
        Font(R.font.montserrat_bold, FontWeight.Bold)
    )
    var selectedBottomNavItem by remember { mutableStateOf(-1) } // Nenhum item selecionado por padrão

    val benefits = listOf(
        "Acesso prioritário a novas áreas de locação de colmeias",
        "Descontos exclusivos em produtos apícolas da nossa loja",
        "Consultoria especializada para otimização da produção de mel",
        "Relatórios detalhados de saúde das colmeias e visitas programadas",
        "Suporte 24/7 para emergências e dúvidas",
        "Participação em workshops e eventos exclusivos para membros premium"
    )

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
                painter = painterResource(id = R.drawable.favo_cortado), // Favo esquerdo
                contentDescription = "Decorativo",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .offset(x = (-80).dp) // Ajuste a posição conforme necessário
                    .size(200.dp, 300.dp), // Ajuste o tamanho conforme a imagem
                contentScale = ContentScale.Fit
            )
            Image(
                painter = painterResource(id = R.drawable.favo_direito), // Favo direito
                contentDescription = "Decorativo",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .offset(x = 80.dp) // Ajuste a posição conforme necessário
                    .size(200.dp, 300.dp), // Ajuste o tamanho conforme a imagem
                contentScale = ContentScale.Fit
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Benefícios do Plano Premium",
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = PolibeeDarkGreen,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(24.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    benefits.forEach { benefit ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.check_mark),
                                contentDescription = "Check Mark",
                                modifier = Modifier.size(24.dp),
                                colorFilter = ColorFilter.tint(PolibeeOrange)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = benefit,
                                fontFamily = montserratFamily,
                                fontSize = 16.sp,
                                color = PolibeeDarkGreen
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = onAdvanceClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PolibeeOrange)
                ) {
                    Text(
                        "Avançar",
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