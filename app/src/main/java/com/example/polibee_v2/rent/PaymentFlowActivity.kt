// src/main/java/com/example/polibee_v2/PaymentFlowActivity.kt
package com.example.polibee_v2.rent

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import com.example.polibee_v2.nav.FavoritesActivity
import com.example.polibee_v2.nav.HistoryActivity
import com.example.polibee_v2.MainActivity
import com.example.polibee_v2.premium.PaymentOptionButton
import com.example.polibee_v2.PolibeeOrange
import com.example.polibee_v2.PolibeeTopBarWithTitleAndBack
import com.example.polibee_v2.nav.ProfileActivity
import com.example.polibee_v2.R
import com.example.polibee_v2.access.PolibeeDarkGreen
import com.example.polibee_v2.montserratFamily

class PaymentFlowActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Polibee_v2Theme {
                PaymentFlowScreen(
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentFlowScreen(
    onBackClick: () -> Unit,
    onBottomNavItemClick: (Int) -> Unit
) {
    val context = LocalContext.current
    var selectedBottomNavItem by remember { mutableStateOf(-1) }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            PolibeeTopBarWithTitleAndBack(title = "Pagamento", onBackClick = onBackClick)
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
            Text(
                text = "Selecione o método de pagamento",
                fontFamily = montserratFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = PolibeeDarkGreen,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))

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
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}