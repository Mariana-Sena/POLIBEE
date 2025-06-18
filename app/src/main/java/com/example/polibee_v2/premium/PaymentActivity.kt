// src/main/java/com/example/polibee_v2/PaymentActivity.kt
package com.example.polibee_v2.premium

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polibee_v2.nav.FavoritesActivity
import com.example.polibee_v2.nav.HistoryActivity
import com.example.polibee_v2.MainActivity
import com.example.polibee_v2.PolibeeOrange
import com.example.polibee_v2.PollinatePlusHeader
import com.example.polibee_v2.R
import com.example.polibee_v2.access.PolibeeDarkGreen
import com.example.polibee_v2.nav.ProfileActivity
import com.example.polibee_v2.ui.theme.Polibee_v2Theme

class PaymentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val selectedPlanType = intent.getStringExtra("selectedPlanType") ?: "" // Recupera o tipo de plano

        setContent {
            Polibee_v2Theme {
                PaymentScreen(
                    selectedPlanType = selectedPlanType,
                    onBackClick = { finish() },
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
fun PaymentScreen(
    selectedPlanType: String,
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
            PollinatePlusHeader(showBack = true, onBackClick = onBackClick)
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
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = """Pagamento do Plano "$selectedPlanType"""", // Exibe o tipo de plano selecionado
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = PolibeeDarkGreen,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Selecione o método de pagamento",
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = PolibeeDarkGreen,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(24.dp))

                // Opções de Pagamento
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    PaymentOptionButton(
                        iconResId = R.drawable.bar_code, // Ícone de Boleto
                        text = "Boleto",
                        onClick = { Toast.makeText(context, "Boleto selecionado!", Toast.LENGTH_SHORT).show() }
                    )
                    PaymentOptionButton(
                        iconResId = R.drawable.card, // Ícone de Cartão
                        text = "Cartão",
                        onClick = { Toast.makeText(context, "Cartão selecionado!", Toast.LENGTH_SHORT).show() }
                    )
                    PaymentOptionButton(
                        iconResId = R.drawable.qrcode, // Ícone de QR Code
                        text = "QR Code",
                        onClick = { Toast.makeText(context, "QR Code selecionado!", Toast.LENGTH_SHORT).show() }
                    )
                    PaymentOptionButton(
                        iconResId = R.drawable.pix, // Ícone de Pix
                        text = "Pix",
                        onClick = { Toast.makeText(context, "Pix selecionado!", Toast.LENGTH_SHORT).show() }
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "*Em caso de dúvida clique aqui*",
                    fontFamily = montserratFamily,
                    fontSize = 14.sp,
                    color = PolibeeDarkGreen,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable { Toast.makeText(context, "Abrir ajuda/suporte", Toast.LENGTH_SHORT).show() }
                )
                Spacer(modifier = Modifier.height(24.dp)) // Espaço para o rodapé
            }
        }
    }
}

@Composable
fun PaymentOptionButton(iconResId: Int, text: String, onClick: () -> Unit) {
    val montserratFamily = FontFamily(
        Font(R.font.montserrat_regular),
        Font(R.font.montserrat_bold, FontWeight.Bold)
    )
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = PolibeeDarkGreen), // Fundo verde escuro
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = text,
                modifier = Modifier.size(36.dp),
                colorFilter = ColorFilter.tint(Color.White) // Ícones brancos
            )
            Spacer(modifier = Modifier.width(24.dp))
            Text(
                text = text,
                fontFamily = montserratFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White // Texto branco
            )
        }
    }
}