package com.example.polibee_v2

import android.content.Intent
import android.os.Bundle
import android.os.Parcel
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
import android.os.Parcelable // Certifique-se de que está importado

// --- Cores e Fontes Comuns ---
val PolibeeOrange = Color(0xFFFFC107)
val PolibeeCategoryBtnBg = Color(0xFFFFB304)
val PolibeeIconColor = Color(0xFF101B15)

val montserratFamily = FontFamily(
    Font(R.font.montserrat_regular),
    Font(R.font.montserrat_bold, FontWeight.Bold)
)

// Data class para item do carrossel (Parcelable para passar via Intent)
@Parcelize
data class OfferItem(
    val id: Int,
    val imageResId: Int,
    val companyName: String,
    val location: String,
    val rating: Float,
    var isFavorite: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readFloat(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(imageResId)
        parcel.writeString(companyName)
        parcel.writeString(location)
        parcel.writeFloat(rating)
        parcel.writeByte(if (isFavorite) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OfferItem> {
        override fun createFromParcel(parcel: Parcel): OfferItem {
            return OfferItem(parcel)
        }

        override fun newArray(size: Int): Array<OfferItem?> {
            return arrayOfNulls(size)
        }
    }
}

annotation class Parcelize

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

    // Dados de exemplo para o carrossel de ofertas (estado mutável para o favorito)
    val carouselItems = remember {
        mutableStateListOf(
            OfferItem(
                id = 1,
                imageResId = R.drawable.placeholder_image,
                companyName = "MELIPROA",
                location = "São Paulo - SP",
                rating = 4.8f,
                isFavorite = false
            ),
            OfferItem(
                id = 2,
                imageResId = R.drawable.placeholder_image,
                companyName = "Jardim das Abelhas",
                location = "Rio de Janeiro - RJ",
                rating = 4.5f,
                isFavorite = false
            ),
            OfferItem(
                id = 3,
                imageResId = R.drawable.placeholder_image,
                companyName = "Doce Mel Colmeias",
                location = "Belo Horizonte - MG",
                rating = 4.9f,
                isFavorite = false
            ),
            OfferItem(
                id = 4,
                imageResId = R.drawable.placeholder_image,
                companyName = "Apiário Central",
                location = "Brasília - DF",
                rating = 4.2f,
                isFavorite = false
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
                        .size(100.dp, 30.dp) // Ajuste o tamanho conforme sua imagem
                        .clickable { context.startActivity(Intent(context, PremiumBenefitsActivity::class.java)) } // <-- ALTERADO AQUI
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
                        painter = painterResource(id = R.drawable.sacola),
                        contentDescription = "Carrinho",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        },
        bottomBar = {
            // --- Barra de Navegação Inferior (Menu) - INTEGRADA AQUI ---
            val items = listOf("Home", "Histórico", "Favoritos", "Perfil")
            val icons = listOf(
                R.drawable.home,
                R.drawable.clock,
                R.drawable.heart,
                R.drawable.profile
            )

            NavigationBar(
                containerColor = PolibeeDarkGreen, // Fundo verde escuro
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp) // Altura da barra
                    .padding(horizontal = 16.dp, vertical = 8.dp) // Padding para o efeito "flutuante"
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)) // Bordas superiores arredondadas
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedBottomNavItem == index,
                        onClick = {
                            selectedBottomNavItem = index
                            // Lógica de navegação para as telas correspondentes
                            when (index) {
                                0 -> { /* Já está na Home */ }
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
                    onClick = { context.startActivity(Intent(context, AluguelActivity::class.java)) }
                )
                CategoryButton(
                    iconResId = R.drawable.cesto,
                    text = "Marketplace",
                    onClick = { context.startActivity(Intent(context, MarketplaceActivity::class.java)) }
                )
                CategoryButton(
                    iconResId = R.drawable.check,
                    text = "Certificação",
                    onClick = { context.startActivity(Intent(context, CertificacaoActivity::class.java)) }
                )
            }

            // Seção de Carrossel de Ofertas
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Ofertas de Aluguéis de Colmeias",
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
                items(carouselItems) { item ->
                    OfferCard(item = item) { clickedItem ->
                        val index = carouselItems.indexOfFirst { it.id == clickedItem.id }
                        if (index != -1) {
                            carouselItems[index] = clickedItem.copy(isFavorite = !clickedItem.isFavorite)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

// --- Componente para os Botões de Categoria ---
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

// --- Componente para o Cartão de Oferta no Carrossel ---
@Composable
fun OfferCard(item: OfferItem, onFavoriteClick: (OfferItem) -> Unit) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .width(200.dp)
            .height(200.dp)
            .clickable { // TORNA O CARTÃO CLICÁVEL
                val intent = Intent(context, BusinessProfileActivity::class.java)
                intent.putExtra("offerItem", item) // PASSA O OBJETO OFFERITEM PARA A NOVA ACTIVITY
                context.startActivity(intent)
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = item.imageResId),
                contentDescription = "Imagem da Oferta",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Ícone de Coração para Favorito
            IconButton(
                onClick = { onFavoriteClick(item) },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.4f))
            ) {
                Image(
                    painter = painterResource(
                        id = if (item.isFavorite) R.drawable.heart_filled else R.drawable.heart
                    ),
                    contentDescription = "Favorito",
                    modifier = Modifier.size(20.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }

            // Box com Informações da Empresa e Rating
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .padding(8.dp)
            ) {
                Text(
                    text = item.companyName,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.White,
                    maxLines = 1
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = item.location,
                        fontFamily = montserratFamily,
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.8f),
                        maxLines = 1
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.star),
                            contentDescription = "Estrela de Avaliação",
                            modifier = Modifier.size(12.dp),
                            colorFilter = ColorFilter.tint(Color.Yellow)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = item.rating.toString(),
                            fontFamily = montserratFamily,
                            fontSize = 12.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}