package com.example.polibee_v2.rent

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polibee_v2.ui.theme.Polibee_v2Theme
import androidx.compose.foundation.shape.CircleShape
import com.example.polibee_v2.AppDataSource // <-- IMPORTANTE: Adicione este import
import com.example.polibee_v2.Company // <-- Mantenha este import, pois a Company está no pacote pai
import com.example.polibee_v2.FavoritesActivity
import com.example.polibee_v2.HistoryActivity
import com.example.polibee_v2.MainActivity
import com.example.polibee_v2.PolibeeCategoryBtnBg
import com.example.polibee_v2.PolibeeOrange
import com.example.polibee_v2.PolibeeTopBarWithTitleAndBack
import com.example.polibee_v2.ProfileActivity
import com.example.polibee_v2.R
import com.example.polibee_v2.access.PolibeeDarkGreen
import com.example.polibee_v2.montserratFamily

class CompanyListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Polibee_v2Theme {
                CompanyListScreen(
                    onBackClick = { finish() },
                    onCompanyClick = { company ->
                        // Passa a empresa para CompanyDetailsActivity
                        val intent = Intent(this, CompanyDetailsActivity::class.java).apply {
                            putExtra("company", company)
                        }
                        startActivity(intent)
                    },
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
fun CompanyListScreen(
    onBackClick: () -> Unit,
    onCompanyClick: (Company) -> Unit,
    onBottomNavItemClick: (Int) -> Unit
) {
    var selectedBottomNavItem by remember { mutableStateOf(-1) }

    // NÃO DEFINA MAIS A LISTA DE EMPRESAS AQUI.
    // ELA SERÁ OBTIDA DO AppDataSource.allCompanies

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            PolibeeTopBarWithTitleAndBack(title = "Com Base em suas Necessidades", onBackClick = onBackClick)
        },
        bottomBar = {
            val items = listOf("Home", "Histórico", "Favoritos", "Perfil")
            val icons = listOf(
                R.drawable.home,
                R.drawable.clock,
                R.drawable.heart,
                R.drawable.profile
            )
            // APLIQUE A CORREÇÃO DA BARRA DE NAVEGAÇÃO AQUI TAMBÉM:
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PolibeeDarkGreen)
                    .navigationBarsPadding() // Garante o padding correto para a barra do sistema
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)) // CLIP NO COLUMN EXTERNO
            ) {
                NavigationBar(
                    containerColor = PolibeeDarkGreen,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(horizontal = 16.dp)
                    // REMOVA O CLIP DAQUI: .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
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
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // AGORA VOCÊ PEGA AS EMPRESAS DO AppDataSource.allCompanies
                items(AppDataSource.allCompanies) { company ->
                    CompanyListItem(company = company) { onCompanyClick(company) }
                }
            }
        }
    }
}

// O CompanyListItem Composable permanece o mesmo
@Composable
fun CompanyListItem(company: Company, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                // Use a imagem que está definida na Company (que vem do AppDataSource)
                painter = painterResource(id = company.imageResId),
                contentDescription = company.name,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(PolibeeCategoryBtnBg),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = company.name,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = PolibeeDarkGreen
                )
                Text(
                    text = company.location,
                    fontFamily = montserratFamily,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.star),
                    contentDescription = "Rating",
                    modifier = Modifier.size(16.dp),
                    colorFilter = ColorFilter.tint(Color.Yellow)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = company.rating.toString(),
                    fontFamily = montserratFamily,
                    fontSize = 14.sp,
                    color = PolibeeDarkGreen
                )
            }
        }
    }
}