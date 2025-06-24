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
import com.example.polibee_v2.nav.ProfileActivity
import com.example.polibee_v2.R
import com.example.polibee_v2.access.PolibeeDarkGreen
import com.example.polibee_v2.montserratFamily
import androidx.compose.ui.draw.alpha

class AvailabilityActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Polibee_v2Theme {
                AvailabilityScreen(
                    onBackClick = { finish() },
                    onAdvanceClick = { startActivity(Intent(this, ConfirmationActivity::class.java)) },
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
fun AvailabilityScreen(
    onBackClick: () -> Unit,
    onAdvanceClick: () -> Unit,
    onBottomNavItemClick: (Int) -> Unit
) {
    var selectedBottomNavItem by remember { mutableStateOf(-1) }

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Button(
                    onClick = onAdvanceClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 24.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PolibeeOrange)
                ) {
                    Text(
                        "Avançar",
                        color = PolibeeDarkGreen,
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

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
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Image(
                painter = painterResource(id = R.drawable.favo),
                contentDescription = "Padrão Favo de Mel",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = (-20).dp, y = 20.dp)
                    .size(200.dp)
                    .alpha(0.15f)
            )

            Image(
                painter = painterResource(id = R.drawable.favo),
                contentDescription = "Padrão Favo de Mel",
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = 20.dp, y = (-20).dp)
                    .size(200.dp)
                    .alpha(0.15f)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.seta_voltar),
                            contentDescription = "Voltar",
                            tint = Color.Black,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Image(
                        painter = painterResource(id = R.drawable.logo_bgwhite),
                        contentDescription = "Polibee Logo",
                        modifier = Modifier
                            .width(170.dp)
                            .padding(start = 70.dp)
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))

                Text(
                    text = "Disponibilidade",
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )

                Spacer(modifier = Modifier.height(40.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    AvailabilityInfoItem(label = "Caixas Disponíveis:", value = "10")
                    Divider(color = Color.LightGray, thickness = 1.dp)
                    AvailabilityInfoItem(label = "Preço:", value = "R$ 1.500")
                    Divider(color = Color.LightGray, thickness = 1.dp)
                    AvailabilityInfoItem(label = "Frete:", value = "R$ 45,00")
                    Divider(color = Color.LightGray, thickness = 1.dp)
                    AvailabilityInfoItem(label = "Abelha:", value = "Jataí")
                    Divider(color = Color.LightGray, thickness = 1.dp)
                    AvailabilityInfoItem(label = "Meliponicultor:", value = "MeliProa")
                }

                Spacer(modifier = Modifier.height(180.dp))
            }
        }
    }
}

@Composable
fun AvailabilityInfoItem(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontFamily = montserratFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = Color.Black
        )
        Text(
            text = value,
            fontFamily = montserratFamily,
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}
