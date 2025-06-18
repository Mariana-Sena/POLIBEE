// src/main/java/com/example/polibee_v2/ConfirmationActivity.kt
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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

class ConfirmationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Polibee_v2Theme {
                ConfirmationScreen(
                    onBackClick = { finish() },
                    onConfirmClick = { startActivity(Intent(this, PaymentFlowActivity::class.java)) }, // Navega para a próxima página
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
fun ConfirmationScreen(
    onBackClick: () -> Unit,
    onConfirmClick: () -> Unit,
    onBottomNavItemClick: (Int) -> Unit
) {
    var selectedBottomNavItem by remember { mutableStateOf(-1) }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            PolibeeTopBarWithTitleAndBack(title = "Confirmação", onBackClick = onBackClick)
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
            Spacer(modifier = Modifier.height(32.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ConfirmationInfoRow(label = "Tipo de Cultura:", value = "Morango")
                    ConfirmationInfoRow(label = "Área Cultivada:", value = "30 Hectares")
                    ConfirmationInfoRow(label = "Período de Aluguel:", value = "20 dias")
                    ConfirmationInfoRow(label = "Espécie de Abelha:", value = "Jataí")
                    ConfirmationInfoRow(label = "Fornecedor:", value = "Meliproa")
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Verifique todos os detalhes antes de confirmar seu pedido.",
                fontFamily = montserratFamily,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = onConfirmClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 24.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PolibeeOrange)
            ) {
                Text("Confirmar", color = Color.White, fontFamily = montserratFamily, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun ConfirmationInfoRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            text = label,
            fontFamily = montserratFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = PolibeeDarkGreen
        )
        Text(
            text = value,
            fontFamily = montserratFamily,
            fontSize = 16.sp,
            color = PolibeeDarkGreen
        )
    }
}