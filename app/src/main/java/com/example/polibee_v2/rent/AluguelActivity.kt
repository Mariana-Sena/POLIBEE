package com.example.polibee_v2.rent

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import com.example.polibee_v2.ui.theme.Polibee_v2Theme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import com.example.polibee_v2.MainActivity
import com.example.polibee_v2.PolibeeOrange
import com.example.polibee_v2.R
import com.example.polibee_v2.access.PolibeeDarkGreen
import com.example.polibee_v2.montserratFamily
import com.example.polibee_v2.nav.FavoritesActivity
import com.example.polibee_v2.nav.HistoryActivity
import com.example.polibee_v2.nav.ProfileActivity

class AluguelActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Polibee_v2Theme {
                AluguelFlowStarterScreen(
                    onStartClick = {
                        startActivity(Intent(this, ApicultureIntroActivity::class.java))
                    },
                    onBackClick = { finish() },
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

@Composable
fun AluguelFlowStarterScreen(
    onStartClick: () -> Unit,
    onBackClick: () -> Unit,
    onBottomNavItemClick: (Int) -> Unit
) {
    var selectedBottomNavItem by remember { mutableStateOf(-1) }

    Scaffold(
        containerColor = Color.Transparent,
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
                        .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
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
                                        modifier = Modifier.size(24.dp), // Tamanho do ícone
                                        colorFilter = ColorFilter.tint(Color.White) // Ícones sempre brancos
                                    )
                                    if (selectedBottomNavItem == index) {
                                        Spacer(modifier = Modifier.height(4.dp)) // Espaço entre ícone e ponto
                                        Box(
                                            modifier = Modifier
                                                .size(6.dp) // Tamanho do ponto
                                                .clip(CircleShape)
                                                .background(PolibeeOrange) // Ponto laranja quando selecionado
                                        )
                                    }
                                }
                            },
                            label = null, // Sem texto para o rótulo
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.Unspecified, // Cor controlada por ColorFilter
                                unselectedIconColor = Color.Unspecified, // Cor controlada por ColorFilter
                                indicatorColor = Color.Transparent // Sem indicador padrão do Material Design
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Aplica o padding do Scaffold (para BottomBar)
                .background(Color.White) // Fundo branco para a tela inteira
        ) {
            // Seção Superior Personalizada (Seta de Voltar e Título "Aluguel de Colmeias")
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min) // Ajusta a altura ao conteúdo
                    .background(Color.White) // Fundo branco para esta seção
                    .padding(bottom = 16.dp) // Padding para o conteúdo abaixo do cabeçalho
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = 16.dp, start = 16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.seta_voltar),
                        contentDescription = "Voltar",
                        modifier = Modifier.size(30.dp),
                        colorFilter = ColorFilter.tint(Color.Black) // Seta preta
                    )
                }
                Text(
                    "Aluguel de Colmeias",
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = Color.Black, // Cor do título preta
                    textAlign = TextAlign.Center, // Centraliza o texto horizontalmente
                    modifier = Modifier
                        .fillMaxWidth() // Essencial para TextAlign.Center funcionar
                        .align(Alignment.Center) // Centraliza verticalmente no Box
                        .padding(top = 16.dp) // Para alinhar com o padding da seta
                        .padding(start = 30.dp)
                )
            }

            // Restante do conteúdo da tela, centralizado abaixo do cabeçalho
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), // Ocupa o restante do espaço vertical
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(200.dp))

                Text(
                    text = "Inicie sua jornada de aluguel de colmeias!",
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = PolibeeDarkGreen,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
                Spacer(modifier = Modifier.height(50.dp))
                Button(
                    onClick = onStartClick,
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(50.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PolibeeOrange)
                ) {
                    Text(
                        "Começar",
                        color = PolibeeDarkGreen,
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}