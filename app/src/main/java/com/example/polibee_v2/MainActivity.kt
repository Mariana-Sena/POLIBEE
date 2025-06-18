package com.example.polibee_v2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polibee_v2.ui.theme.Polibee_v2Theme
import android.os.Parcelable
import kotlinx.parcelize.Parcelize // Certifique-se de ter o plugin 'kotlin-parcelize' no seu build.gradle.kts

// --- Cores e Fontes Comuns ---
// As cores PolibeeOrange e PolibeeCategoryBtnBg e PolibeeIconColor
// assumimos que estão sendo importadas corretamente ou definidas globalmente.
// Ex: val PolibeeDarkGreen = Color(0xFF0D2016)
// Se PolibeeDarkGreen vem de 'com.example.polibee_v2.access.PolibeeDarkGreen', mantenha o import.
import com.example.polibee_v2.access.PolibeeDarkGreen
import com.example.polibee_v2.marketplace.CartActivity
import com.example.polibee_v2.marketplace.MarketplaceActivity
import com.example.polibee_v2.nav.FavoritesActivity
import com.example.polibee_v2.nav.HistoryActivity
import com.example.polibee_v2.premium.PremiumOverviewActivity
import com.example.polibee_v2.nav.ProfileActivity
import com.example.polibee_v2.rent.ApicultureIntroActivity

val PolibeeOrange = Color(0xFFFFC107)
val PolibeeCategoryBtnBg = Color(0xFFFFB304)
val PolibeeIconColor = Color(0xFF101B15)

val montserratFamily = FontFamily(
    Font(R.font.montserrat_regular),
    Font(R.font.montserrat_bold, FontWeight.Bold)
)

// Data class para Abelhas Nativas (para a nova seção)
@Parcelize
data class NativeBee(
    val id: Int,
    val name: String,
    val imageResId: Int, // Imagem para o card da abelha
    val curiosityText: String // Texto das curiosidades para a página detalhe
) : Parcelable

// --- MainActivity ---
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Polibee_v2Theme {
                MainScreenContent()
            }
        }
    }
}

// --- MainScreenContent Composable ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenContent() {
    val context = LocalContext.current

    // Dados de exemplo para as Abelhas Nativas
    val nativeBees = remember {
        mutableStateListOf(
            NativeBee(
                id = 1,
                name = "Jataí",
                imageResId = R.drawable.jatai, // Certifique-se que esta imagem existe
                curiosityText = "A abelha Jataí (Tetragonisca angustula) é uma das abelhas sem ferrão mais comuns no Brasil. Pequenas e mansas, produzem um mel delicioso e são ótimas polinizadoras. São fáceis de criar em ambientes urbanos."
            ),
            NativeBee(
                id = 2,
                name = "Mandaguari",
                imageResId = R.drawable.mandaguari, // Certifique-se que esta imagem existe
                curiosityText = "A Mandaguari (Scaptotrigona postica) é uma abelha sem ferrão de porte médio, conhecida por seu comportamento mais defensivo. Produz um mel com sabor marcante e possui um invólucro de cera escura em seu ninho."
            ),
            NativeBee(
                id = 3,
                name = "Uruçu",
                imageResId = R.drawable.urucu, // Certifique-se que esta imagem existe
                curiosityText = "A Uruçu (Melipona scutellaris) é uma abelha sem ferrão de grande porte, muito valorizada no nordeste do Brasil. Produz um mel de alta qualidade e sabor suave. Sua criação é importante para a conservação da espécie."
            ),
            NativeBee(
                id = 4,
                name = "Mandaçaia",
                imageResId = R.drawable.mandacaia, // Certifique-se que esta imagem existe
                curiosityText = "A Mandaçaia (Melipona quadrifasciata) é uma abelha sem ferrão robusta, famosa pela beleza de sua coloração e pela qualidade do mel. Possui uma mandíbula que lembra açaí, daí o nome."
            )
        )
    }

    // Estado para o item selecionado na bottom bar
    var selectedBottomNavItem by remember { mutableStateOf(0) } // Home selecionada por padrão

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            // --- Custom TopBar para a tela principal ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp) // Espaçamento do topo da tela
                    .height(64.dp) // Altura do conteúdo da barra superior
                    .background(Color.White) // Fundo branco
            ) {
                // Botão "Seja Premium" (canto esquerdo)
                Image(
                    painter = painterResource(id = R.drawable.sejapremium),
                    contentDescription = "Seja Premium",
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp)
                        .size(100.dp, 30.dp)
                        .clickable { context.startActivity(Intent(context, PremiumOverviewActivity::class.java)) }
                )

                // Logo Polibee (centro)
                Image(
                    painter = painterResource(id = R.drawable.logo_bgwhite),
                    contentDescription = "Polibee Logo",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(width = 120.dp, height = 40.dp)
                )

                // Ícone de sacola (canto direito)
                IconButton(
                    onClick = { context.startActivity(Intent(context, CartActivity::class.java)) },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.sacola), // Certifique-se que esta imagem existe
                        contentDescription = "Carrinho",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        },

        bottomBar = {
            val items = listOf("Home", "Histórico", "Favoritos", "Perfil")
            val icons = listOf(
                R.drawable.home,
                R.drawable.clock,
                R.drawable.heart,
                R.drawable.profile
            )

            // NOVO: Column para garantir que o fundo seja PolibeeDarkGreen até o final
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PolibeeDarkGreen) // AQUI: Garante que toda a área inferior é verde
                    .navigationBarsPadding() // AQUI: Garante que o conteúdo não se sobreponha à barra de navegação do sistema
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)) // <--- AQUI A MUDANÇA: APLIQUE O CLIP APENAS NO COLUMN EXTERNO
            ) {
                NavigationBar(
                    containerColor = PolibeeDarkGreen, // Fundo verde escuro para a barra em si
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp) // Altura da barra
                        .padding(horizontal = 16.dp) // Mantém o padding horizontal para o efeito flutuante
                    // .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)) REMOVIDO: Este clip AGORA DEVE SER REMOVIDO DAQUI
                ) {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedBottomNavItem == index,
                            onClick = {
                                selectedBottomNavItem = index
                                // Lógica de navegação para as telas correspondentes
                                when (index) {
                                    0 -> context.startActivity(Intent(context, MainActivity::class.java))
                                    1 -> context.startActivity(Intent(context, HistoryActivity::class.java))
                                    2 -> context.startActivity(Intent(context, FavoritesActivity::class.java))
                                    3 -> context.startActivity(Intent(context, ProfileActivity::class.java))
                                }
                            },
                            icon = {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Image(
                                        painter = painterResource(id = icons[index]),
                                        contentDescription = item,
                                        modifier = Modifier.size(24.dp), // Tamanho do ícone
                                        colorFilter = ColorFilter.tint(Color.White) // Ícones sempre brancos
                                    )
                                    if (selectedBottomNavItem == index) {
                                        Spacer(modifier = Modifier.height(4.dp)) // Espaço entre o ícone e o ponto
                                        Box(
                                            modifier = Modifier
                                                .size(6.dp) // Tamanho do ponto
                                                .clip(CircleShape)
                                                .background(PolibeeOrange) // Ponto laranja quando selecionado
                                        )
                                    }
                                }
                            },
                            label = null, // Sem texto para o label
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.Unspecified, // Cor controlada pelo ColorFilter
                                unselectedIconColor = Color.Unspecified, // Cor controlada pelo ColorFilter
                                indicatorColor = Color.Transparent // Sem indicador padrão do Material Design
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        // --- Conteúdo Principal da Tela (Scrollable) ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Aplica o padding do Scaffold (para não sobrepor Top/Bottom Bar)
                .verticalScroll(rememberScrollState()) // Torna o conteúdo rolagem vertical
                .background(Color.White)
        ) {
            // Barra de Pesquisa
            Spacer(modifier = Modifier.height(16.dp)) // Espaço abaixo da TopBar customizada
            OutlinedTextField(
                value = "", // Estado para a query de pesquisa (precisaria de um `var searchQuery by remember { mutableStateOf("") }`)
                onValueChange = { /* Atualizar searchQuery */ },
                label = { Text("Pesquisa", fontFamily = montserratFamily) },
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.lupa),
                        contentDescription = "Ícone de Pesquisa",
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(PolibeeDarkGreen)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                singleLine = true,
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = PolibeeDarkGreen,
                    unfocusedBorderColor = PolibeeDarkGreen,
                    focusedLabelColor = PolibeeDarkGreen,
                    unfocusedLabelColor = PolibeeDarkGreen,
                    cursorColor = PolibeeDarkGreen
                )
            )

            // Botões de Categoria
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CategoryButton(
                    iconResId = R.drawable.hive,
                    text = "Aluguel",
                    onClick = { context.startActivity(Intent(context, AluguelActivity::class.java)) } // <-- ALTERADO: Agora AluguelActivity é o novo fluxo
                )
                CategoryButton(
                    iconResId = R.drawable.cesto,
                    text = "Marketplace",
                    onClick = { context.startActivity(Intent(context, MarketplaceActivity::class.java)) }
                )
                CategoryButton(
                    iconResId = R.drawable.locarvender,
                    text = "Locar ou Vender",
                    onClick = { context.startActivity(Intent(context, ApicultureIntroActivity::class.java)) }
                )
            }

            // Seção de Abelhas Nativas
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Conheça Nossas Abelhas Nativas", // Título da nova seção
                fontFamily = montserratFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = PolibeeDarkGreen,
                modifier = Modifier.padding(start = 24.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 24.dp)
            ) {
                items(nativeBees) { bee ->
                    NativeBeeCard(bee = bee) { clickedBee ->
                        // Navega para a tela de curiosidades da abelha
                        val intent = Intent(context, BeeCuriosityActivity::class.java).apply {
                            putExtra("nativeBee", clickedBee)
                        }
                        context.startActivity(intent)
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

// --- Componente para os Botões de Categoria (mantido) ---
@Composable
fun CategoryButton(
    iconResId: Int,
    text: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(PolibeeCategoryBtnBg),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = text,
                modifier = Modifier.size(36.dp),
                colorFilter = ColorFilter.tint(PolibeeIconColor)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text,
            fontFamily = montserratFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = PolibeeDarkGreen
        )
    }
}

// --- NOVO Componente para o Cartão de Abelha Nativa ---
@Composable
fun NativeBeeCard(bee: NativeBee, onClick: (NativeBee) -> Unit) {
    Card(
        modifier = Modifier
            .width(180.dp) // Largura ajustada para o card da abelha
            .height(160.dp) // Altura ajustada
            .clickable { onClick(bee) },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = bee.imageResId),
                contentDescription = bee.name,
                modifier = Modifier
                    .size(80.dp) // Tamanho da imagem da abelha
                    .clip(CircleShape), // Imagem circular
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = bee.name,
                fontFamily = montserratFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = PolibeeDarkGreen,
                maxLines = 1
            )
        }
    }
}