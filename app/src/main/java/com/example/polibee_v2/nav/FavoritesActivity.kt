// src/main/java/com/example/polibee_v2/FavoritesActivity.kt
package com.example.polibee_v2.nav

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polibee_v2.ui.theme.Polibee_v2Theme
import androidx.compose.foundation.shape.CircleShape
import com.example.polibee_v2.AppDataSource
import com.example.polibee_v2.Company
import com.example.polibee_v2.MainActivity
import com.example.polibee_v2.PolibeeOrange
import com.example.polibee_v2.PolibeeTopBarWithTitleAndBack
import com.example.polibee_v2.R
import com.example.polibee_v2.access.PolibeeDarkGreen
import com.example.polibee_v2.montserratFamily
import com.example.polibee_v2.rent.CompanyDetailsActivity
import com.example.polibee_v2.rent.CompanyListItem

class FavoritesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Polibee_v2Theme {
                FavoritesScreen(
                    onBackClick = { finish() },
                    onCompanyClick = { company ->
                        val intent = Intent(this, CompanyDetailsActivity::class.java).apply {
                            putExtra("company", company)
                        }
                        startActivity(intent)
                    },
                    onBottomNavItemClick = { index ->
                        when (index) {
                            0 -> startActivity(Intent(this, MainActivity::class.java))
                            1 -> startActivity(Intent(this, HistoryActivity::class.java))
                            2 -> { /* Já está aqui */ }
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
fun FavoritesScreen(
    onBackClick: () -> Unit,
    onCompanyClick: (Company) -> Unit,
    onBottomNavItemClick: (Int) -> Unit
) {
    var selectedBottomNavItem by remember { mutableStateOf(2) } // Favoritos selecionado por padrão

    // Filtra a lista de todas as empresas para obter apenas as favoritas
    val favoriteCompanies = remember(AppDataSource.allCompanies) {
        AppDataSource.allCompanies.filter { it.isFavorite }
    }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            PolibeeTopBarWithTitleAndBack(title = "Minhas Empresas Favoritas", onBackClick = onBackClick)
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
                    .background(PolibeeDarkGreen)
                    .navigationBarsPadding()
            ) {
                NavigationBar(
                    containerColor = PolibeeDarkGreen,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(horizontal = 16.dp)
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            if (favoriteCompanies.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Nenhuma empresa favorita ainda.\nMarque o coração para adicionar!",
                        fontFamily = montserratFamily,
                        fontSize = 18.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(favoriteCompanies) { company ->
                        // Reutiliza o CompanyListItem da CompanyListActivity
                        CompanyListItem(company = company) { onCompanyClick(company) }
                    }
                }
            }
        }
    }
}