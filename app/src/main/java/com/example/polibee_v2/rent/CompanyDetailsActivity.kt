package com.example.polibee_v2.rent

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polibee_v2.ui.theme.Polibee_v2Theme
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.text.style.TextAlign
import com.example.polibee_v2.AppDataSource
import com.example.polibee_v2.Company
import com.example.polibee_v2.nav.FavoritesActivity
import com.example.polibee_v2.nav.HistoryActivity
import com.example.polibee_v2.MainActivity
import com.example.polibee_v2.PolibeeOrange
import com.example.polibee_v2.nav.ProfileActivity
import com.example.polibee_v2.R
import com.example.polibee_v2.access.PolibeeDarkGreen
import com.example.polibee_v2.montserratFamily
import androidx.compose.ui.draw.alpha
import androidx.compose.foundation.layout.navigationBarsPadding
import java.util.Locale

class CompanyDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val company = intent.getParcelableExtra<Company>("company")

        company?.let {
            AppDataSource.addCompanyToHistory(it)
        }

        setContent {
            Polibee_v2Theme {
                if (company != null) {
                    CompanyDetailsScreen(
                        company = company,
                        onBackClick = { finish() },
                        onChatClick = { companyName, companyImageRes ->
                            val intent = Intent(this, ChatActivity::class.java).apply {
                                putExtra("companyName", companyName)
                                putExtra("companyImageRes", companyImageRes)
                            }
                            startActivity(intent)
                        },
                        onRentClick = {
                            startActivity(Intent(this, AvailabilityActivity::class.java))
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
                } else {
                    Toast.makeText(this, "Erro: Dados da empresa não encontrados.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyDetailsScreen(
    company: Company,
    onBackClick: () -> Unit,
    onChatClick: (String, Int) -> Unit,
    onRentClick: () -> Unit,
    onBottomNavItemClick: (Int) -> Unit
) {
    val context = LocalContext.current
    var isFavorite by remember { mutableStateOf(company.isFavorite) }
    var selectedBottomNavItem by remember { mutableStateOf(-1) }

    Scaffold(
        containerColor = PolibeeDarkGreen,
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Button(
                    onClick = onRentClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 24.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PolibeeOrange)
                ) {
                    Text(
                        "Alugar",
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
                    .offset(x = 100.dp, y = (-150).dp)
                    .size(200.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp),
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

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = company.name,
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .offset(x = (-24).dp)
                    )

                    Spacer(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .width(400.dp)
                        .height(300.dp)
                        .padding(horizontal = 24.dp)
                        .clip(RoundedCornerShape(20.dp))
                ) {
                    Image(
                        painter = painterResource(id = company.imageResId),
                        contentDescription = "Imagem da Empresa",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(16.dp)
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.4f))
                            .clickable {
                                isFavorite = !isFavorite
                                AppDataSource.toggleFavoriteStatus(company.id)
                                Toast.makeText(context, if (isFavorite) "Adicionado aos favoritos!" else "Removido dos favoritos!", Toast.LENGTH_SHORT).show()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(
                                id = if (isFavorite) R.drawable.heart_filled else R.drawable.heart
                            ),
                            contentDescription = "Favoritar",
                            modifier = Modifier.size(24.dp),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }

                    Card(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 16.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.7f)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = company.name,
                                fontFamily = montserratFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.location_pin),
                                        contentDescription = "Localização",
                                        tint = Color.White,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = company.location,
                                        fontFamily = montserratFamily,
                                        fontSize = 16.sp,
                                        color = Color.White
                                    )
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Image(
                                        painter = painterResource(id = R.drawable.star),
                                        contentDescription = "Estrela de Avaliação",
                                        modifier = Modifier.size(16.dp),
                                        colorFilter = ColorFilter.tint(Color.Yellow)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = company.rating.toString(),
                                        fontFamily = montserratFamily,
                                        fontSize = 16.sp,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "R$ ${String.format(Locale.getDefault(), "%.2f", company.pricePerHour)} / Hora",
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = PolibeeDarkGreen,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { onChatClick(company.name, company.imageResId) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(horizontal = 24.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PolibeeDarkGreen)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.enviar),
                            contentDescription = "Enviar Mensagem",
                            modifier = Modifier.size(24.dp),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Conversar",
                            color = Color.White,
                            fontFamily = montserratFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = company.description,
                    fontFamily = montserratFamily,
                    fontSize = 16.sp,
                    color = PolibeeDarkGreen,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
                Spacer(modifier = Modifier.height(180.dp))
            }
        }
    }
}