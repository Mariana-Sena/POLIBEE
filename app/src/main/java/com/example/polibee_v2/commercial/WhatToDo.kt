package com.example.polibee_v2.commercial

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.polibee_v2.R
import androidx.compose.foundation.shape.CircleShape
import com.example.polibee_v2.access.PolibeeDarkGreen
import com.example.polibee_v2.commercial.registration.RegisterHiveActivity // Importa a nova Activity
import com.example.polibee_v2.commercial.registration.RegisterProductActivity // Importa a nova Activity
import com.example.polibee_v2.nav.FavoritesActivity
import com.example.polibee_v2.nav.HistoryActivity
import com.example.polibee_v2.nav.ProfileActivity
import com.example.polibee_v2.ui.theme.Polibee_v2Theme
import com.example.polibee_v2.PolibeeOrange // Importa sua cor PolibeeOrange
import com.example.polibee_v2.montserratFamily // Importa sua fonte montserratFamily
import androidx.compose.foundation.layout.navigationBarsPadding // Importa navigationBarsPadding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.polibee_v2.MainActivity

class WhatToDoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Polibee_v2Theme {
                WhatToDoScreen(
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
fun WhatToDoScreen(onBottomNavItemClick: (Int) -> Unit) {
    val context = LocalContext.current
    var selectedBottomNavItem by remember { mutableStateOf(-1) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "O que gostaria de fazer?",
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.Bold,
                        color = PolibeeDarkGreen,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(start = 16.dp, top = 20.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { (context as? ComponentActivity)?.finish() }) {
                        Image(
                            painter = painterResource(id = R.drawable.seta_voltar), // Certifique-se que você tem este drawable
                            contentDescription = "Voltar",
                            modifier = Modifier.padding(start = 16.dp, top = 20.dp).size(50.dp),
                            colorFilter = ColorFilter.tint(PolibeeDarkGreen)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = { // BARRA DE NAVEGAÇÃO ADICIONADA AQUI!
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
                    val items = listOf("Home", "Histórico", "Favoritos", "Perfil")
                    val icons = listOf(
                        R.drawable.home, R.drawable.clock, R.drawable.heart, R.drawable.profile
                    )
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
        },
        containerColor = Color.White // Fundo branco para a tela
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .background(Color.White)
        ) {
            // Desenho de fundo de hexágonos (se você tiver esses drawables)
            // Certifique-se de ter R.drawable.hexagon_pattern_top_right e R.drawable.hexagon_pattern_bottom_left
            // Se não tiver, pode comentar ou remover essas linhas.
            Image(
                painter = painterResource(id = R.drawable.favo), // Favo esquerdo
                contentDescription = "Decorativo Esquerdo",
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = (-60).dp, y = 60.dp)
                    .size(400.dp, 350.dp) // Tamanho
                    .zIndex(-1f),
                contentScale = ContentScale.FillBounds
            )
            Image(
                painter = painterResource(id = R.drawable.favo_direito), // Favo direito
                contentDescription = "Decorativo Direito",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 0.dp, y = (-30).dp)
                    .size(400.dp, 300.dp)
                    .zIndex(1f),
                contentScale = ContentScale.FillBounds
            )


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Botão "Vender"
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clickable {
                            context.startActivity(Intent(context, RegisterProductActivity::class.java))
                            // (context as? ComponentActivity)?.finish() // Não finaliza para permitir voltar aqui
                        },                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.sell), // Certifique-se que você tem este drawable
                            contentDescription = "Vender",
                            modifier = Modifier.size(80.dp),
                            colorFilter = ColorFilter.tint(PolibeeDarkGreen)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Vender",
                            fontFamily = montserratFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp,
                            color = PolibeeDarkGreen,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Botão "Locar"
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clickable {
                            context.startActivity(Intent(context, RegisterHiveActivity::class.java))
                            // (context as? ComponentActivity)?.finish() // Não finaliza para permitir voltar aqui
                        },                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.hive), // Certifique-se que você tem este drawable
                            contentDescription = "Locar",
                            modifier = Modifier.size(80.dp),
                            colorFilter = ColorFilter.tint(PolibeeDarkGreen)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Locar",
                            fontFamily = montserratFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp,
                            color = PolibeeDarkGreen,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}