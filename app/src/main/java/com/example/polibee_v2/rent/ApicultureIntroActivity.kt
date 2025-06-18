// src/main/java/com/example/polibee_v2/ApicultureIntroActivity.kt
package com.example.polibee_v2.rent

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import com.example.polibee_v2.ui.theme.Polibee_v2Theme
import androidx.compose.foundation.shape.CircleShape
import com.example.polibee_v2.nav.FavoritesActivity
import com.example.polibee_v2.nav.HistoryActivity
import com.example.polibee_v2.MainActivity
import com.example.polibee_v2.PolibeeOrange
import com.example.polibee_v2.PolibeeTopBarWithTitleAndBack
import com.example.polibee_v2.nav.ProfileActivity
import com.example.polibee_v2.R
import com.example.polibee_v2.access.PolibeeDarkGreen
import com.example.polibee_v2.montserratFamily

class ApicultureIntroActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Polibee_v2Theme {
                ApicultureIntroScreen(
                    onBackClick = { finish() },
                    onAdvanceClick = { startActivity(Intent(this, TipsActivity::class.java)) }, // Navega para a próxima página
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
fun ApicultureIntroScreen(
    onBackClick: () -> Unit,
    onAdvanceClick: () -> Unit,
    onBottomNavItemClick: (Int) -> Unit
) {
    var selectedBottomNavItem by remember { mutableStateOf(-1) }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            PolibeeTopBarWithTitleAndBack(title = "Apicultura", onBackClick = onBackClick)
        },
        bottomBar = {
            val items = listOf("Home", "Histórico", "Favoritos", "Perfil")
            val icons = listOf(
                R.drawable.home,
                R.drawable.clock,
                R.drawable.heart,
                R.drawable.profile
            )
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Image(
                painter = painterResource(id = R.drawable.apicultura), // Certifique-se que esta imagem existe
                contentDescription = "Imagem de Apicultura",
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "A apicultura é a arte e a ciência de criar abelhas, principalmente para a produção de mel e outros produtos como cera, própolis, pólen e geleia real. Vai além da colheita, envolvendo a saúde das colmeias e a polinização.",
                fontFamily = montserratFamily,
                fontSize = 16.sp,
                color = PolibeeDarkGreen,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Tópicos Importantes:",
                fontFamily = montserratFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = PolibeeDarkGreen,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "- Polinização: Essencial para a agricultura e ecossistema.",
                    fontFamily = montserratFamily, fontSize = 16.sp, color = PolibeeDarkGreen
                )
                Text(
                    text = "- Produtos: Mel, própolis, cera, geleia real.",
                    fontFamily = montserratFamily, fontSize = 16.sp, color = PolibeeDarkGreen
                )
                Text(
                    text = "- Tipos de Abelhas: Nativas, africanizadas.",
                    fontFamily = montserratFamily, fontSize = 16.sp, color = PolibeeDarkGreen
                )
                Text(
                    text = "- Sustentabilidade: Práticas que protegem as abelhas e o meio ambiente.",
                    fontFamily = montserratFamily, fontSize = 16.sp, color = PolibeeDarkGreen
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = onAdvanceClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 24.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PolibeeOrange)
            ) {
                Text("Avançar", color = Color.White, fontFamily = montserratFamily, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}