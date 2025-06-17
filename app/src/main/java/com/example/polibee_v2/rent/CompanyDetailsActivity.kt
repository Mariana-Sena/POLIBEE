// src/main/java/com/example/polibee_v2/CompanyDetailsActivity.kt
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
import com.example.polibee_v2.ChatActivity
import com.example.polibee_v2.Company
import com.example.polibee_v2.FavoritesActivity
import com.example.polibee_v2.HistoryActivity
import com.example.polibee_v2.MainActivity
import com.example.polibee_v2.PolibeeOrange
import com.example.polibee_v2.profile.ProfileActivity
import com.example.polibee_v2.R
import com.example.polibee_v2.access.PolibeeDarkGreen
import com.example.polibee_v2.montserratFamily

class CompanyDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val company = intent.getParcelableExtra<Company>("company")

        company?.let {
            AppDataSource.addCompanyToHistory(it) // <--- ADICIONE ESTA LINHA
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
                            startActivity(Intent(this, AvailabilityActivity::class.java)) // Navega para a próxima página
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
        containerColor = Color.Transparent,
        topBar = {
            // Reutilizando o TopBar com o nome da empresa como título
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
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
                    text = company.name,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = PolibeeDarkGreen,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(0.8f)
                )
            }
        },
        bottomBar = {
            // Botão "Alugar" fixo no rodapé, como no protótipo original da Meliproa
            Button(
                onClick = onRentClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PolibeeOrange)
            ) {
                Text(
                    "Alugar",
                    color = Color.White,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            // NOTA: Se você quiser o menu inferior aqui TAMBÉM, adicione-o abaixo do botão "Alugar"
            // mas o protótipo original da Meliproa não o mostrava.
            // Para manter a consistência com o protótipo fornecido para a página da Meliproa,
            // esta tela não terá o menu de navegação inferior padrão, apenas o botão "Alugar".
            // Se precisar do menu, podemos adaptar.
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .background(Color.White)
        ) {
            // Seção Superior com imagem da empresa e overlay
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                Image(
                    painter = painterResource(id = company.imageResId),
                    contentDescription = "Imagem da Empresa",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Ícone de Coração para Favorito
                IconButton(
                    onClick = {
                        // isFavorite é o estado local para o ícone
                        isFavorite = !isFavorite
                        // Atualiza o estado global da empresa via AppDataSource
                        AppDataSource.toggleFavoriteStatus(company.id)
                    },
                        modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.4f))
                ) {
                    Image(
                        painter = painterResource(
                            id = if (isFavorite) R.drawable.heart_filled else R.drawable.heart
                        ), // Certifique-se que heart_filled e heart existem
                        contentDescription = "Favorito",
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }

                // Informações da Empresa (Nome, Localização, Rating) - Overlay na imagem
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.5f))
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
                                painter = painterResource(id = R.drawable.location_pin), // Certifique-se que esta imagem existe
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

            // Valor do Aluguel
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "R$ ${String.format("%.2f", company.pricePerHour)} / Hora",
                fontFamily = montserratFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = PolibeeDarkGreen,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Botão Conversar
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
                        painter = painterResource(id = R.drawable.enviar), // Certifique-se que esta imagem existe
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

            // Descrição da Colmeia
            Text(
                text = company.description,
                fontFamily = montserratFamily,
                fontSize = 16.sp,
                color = PolibeeDarkGreen,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}