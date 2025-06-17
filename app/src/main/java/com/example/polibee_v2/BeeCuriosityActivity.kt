// src/main/java/com/example/polibee_v2/BeeCuriosityActivity.kt
package com.example.polibee_v2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
import com.example.polibee_v2.ui.theme.Polibee_v2Theme
import com.example.polibee_v2.access.PolibeeDarkGreen // Exemplo de importação
import com.example.polibee_v2.profile.ProfileActivity

// val PolibeeOrange = Color(0xFFFFC107) // Se necessário

class BeeCuriosityActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Recupera o objeto NativeBee passado via Intent
        val nativeBee = intent.getParcelableExtra<NativeBee>("nativeBee")

        setContent {
            Polibee_v2Theme {
                if (nativeBee != null) {
                    BeeCuriosityScreen(
                        bee = nativeBee,
                        onBackClick = { finish() },
                        onBottomNavItemClick = { index ->
                            // Lógica de navegação do menu inferior
                            when (index) {
                                0 -> startActivity(Intent(this, MainActivity::class.java))
                                1 -> startActivity(Intent(this, HistoryActivity::class.java))
                                2 -> startActivity(Intent(this, FavoritesActivity::class.java))
                                3 -> startActivity(Intent(this, ProfileActivity::class.java))
                            }
                            finish() // Finaliza esta activity se navegar para outra tela principal
                        }
                    )
                } else {
                    // Lidar com o caso de nativeBee ser nulo (erro)
                    Toast.makeText(this, "Erro: Dados da abelha não encontrados.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeeCuriosityScreen(
    bee: NativeBee,
    onBackClick: () -> Unit,
    onBottomNavItemClick: (Int) -> Unit
) {
    val context = LocalContext.current
    val montserratFamily = FontFamily(
        Font(R.font.montserrat_regular),
        Font(R.font.montserrat_bold, FontWeight.Bold)
    )
    var selectedBottomNavItem by remember { mutableStateOf(-1) }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .background(Color.White)
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.seta_voltar),
                        contentDescription = "Voltar",
                        modifier = Modifier.size(30.dp),
                        colorFilter = ColorFilter.tint(PolibeeDarkGreen)
                    )
                }
                Text(
                    text = bee.name, // Nome da abelha no título da barra
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = PolibeeDarkGreen,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
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
            // Favos Laterais (mantidos para consistência visual)
            Image(
                painter = painterResource(id = R.drawable.favo),
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
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                Image(
                    painter = painterResource(id = bee.imageResId),
                    contentDescription = bee.name,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .background(PolibeeCategoryBtnBg), // Fundo para a imagem da abelha
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Curiosidades sobre a ${bee.name}",
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = PolibeeDarkGreen,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = bee.curiosityText,
                    fontFamily = montserratFamily,
                    fontSize = 16.sp,
                    color = PolibeeDarkGreen,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}