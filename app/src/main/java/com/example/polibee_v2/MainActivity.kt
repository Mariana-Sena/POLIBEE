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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polibee_v2.ui.theme.Polibee_v2Theme
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

import com.example.polibee_v2.access.PolibeeDarkGreen
import com.example.polibee_v2.marketplace.CartActivity
import com.example.polibee_v2.marketplace.MarketplaceActivity
import com.example.polibee_v2.nav.FavoritesActivity
import com.example.polibee_v2.nav.HistoryActivity
import com.example.polibee_v2.premium.PremiumOverviewActivity
import com.example.polibee_v2.nav.ProfileActivity
import com.example.polibee_v2.rent.AluguelActivity
import com.example.polibee_v2.rent.ApicultureIntroActivity

val PolibeeOrange = Color(0xFFFFC107)
val PolibeeCategoryBtnBg = Color(0xFFFFB304)
val PolibeeIconColor = Color(0xFF101B15)

@Parcelize
data class NativeBee(
    val id: Int,
    val name: String,
    val imageResId: Int,
    val curiosityText: String
) : Parcelable

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenContent() {
    val context = LocalContext.current

    val nativeBees = remember {
        mutableStateListOf(
            NativeBee(
                id = 1,
                name = "Uruçu",
                imageResId = R.drawable.urucu,
                curiosityText = "A abelha Uruçu é uma polinizadora natural que melhora a produção de várias culturas agrícolas. \n" +
                        "Ao visitar flor por flor, ela espalha o pólen, o que aumenta a quantidade e a qualidade dos frutos.\n" +
                        "Ela é ideal para a polinização de:\n" +
                        "\n" +
                        "\uD83C\uDF53 Morango\n" +
                        "\uD83C\uDF48 Melão e melancia\n" +
                        "\uD83E\uDD6D Manga\n" +
                        "\uD83C\uDF4A Cítricos (laranja, limão, tangerina)\n" +
                        "\uD83E\uDD51 Abacate\n" +
                        "\uD83C\uDF36\uFE0F Pimentão e pimenta\n" +
                        "\uD83C\uDF3D Milho verde (em sistemas agroflorestais)\n" +
                        "\n" +
                        "Com a Uruçu, os frutos ficam mais bem formados, \n" +
                        "maiores e com menos perda. \n" +
                        "Além disso, como é uma abelha sem ferrão e nativa, \n" +
                        "ela se adapta bem ao ambiente e trabalha em harmonia com a natureza.."
            ),
            NativeBee(
                id = 2,
                name = "Mandaguari",
                imageResId = R.drawable.mandaguari,
                curiosityText = "A Mandaguari (Scaptotrigona postica) é uma abelha sem ferrão de porte médio, conhecida por seu comportamento mais defensivo. Produz um mel com sabor marcante e possui um invólucro de cera escura em seu ninho."
            ),
            NativeBee(
                id = 3,
                name = "Jataí",
                imageResId = R.drawable.jatai,
                curiosityText = "A abelha Jataí (Tetragonisca angustula) é uma das abelhas sem ferrão mais comuns no Brasil. Pequenas e mansas, produzem um mel delicioso e são ótimas polinizadoras. São fáceis de criar em ambientes urbanos."
            ),
            NativeBee(
                id = 4,
                name = "Mandaçaia",
                imageResId = R.drawable.mandacaia,
                curiosityText = "A Mandaçaia (Melipona quadrifasciata) é uma abelha sem ferrão robusta, famosa pela beleza de sua coloração e pela qualidade do mel. Possui uma mandíbula que lembra açaí, daí o nome."
            )
        )
    }

    var selectedBottomNavItem by remember { mutableStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .height(64.dp)
                    .background(Color.White)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sejapremium),
                    contentDescription = "Seja Premium",
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 2.dp)
                        .size(105.dp, 80.dp)
                        .clickable { context.startActivity(Intent(context, PremiumOverviewActivity::class.java)) }
                )
                Image(
                    painter = painterResource(id = R.drawable.logo_bgwhite),
                    contentDescription = "Polibee Logo",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(width = 120.dp, height = 45.dp)
                )
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
                .verticalScroll(rememberScrollState())
                .background(Color.White)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Pesquisa", fontFamily = montserratFamily) },
                textStyle = LocalTextStyle.current.copy(fontFamily = montserratFamily),
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
                    cursorColor = PolibeeDarkGreen,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )

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
                    text = "Aluguel\n",
                    onClick = { context.startActivity(Intent(context, AluguelActivity::class.java)) }
                )
                CategoryButton(
                    iconResId = R.drawable.cesto,
                    text = "Marketplace\n",
                    onClick = { context.startActivity(Intent(context, MarketplaceActivity::class.java)) }
                )
                CategoryButton(
                    iconResId = R.drawable.locarvender,
                    text = "Locar ou\nVender",
                    onClick = { context.startActivity(Intent(context, ApicultureIntroActivity::class.java)) }
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Conheça Nossas Abelhas Nativas",
                fontFamily = montserratFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = PolibeeDarkGreen,
                modifier = Modifier.padding(start = 20.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 24.dp)
            ) {
                items(nativeBees) { bee ->
                    NativeBeeCard(bee = bee) { clickedBee ->
                        val intent = Intent(context, BeeCuriosityActivity::class.java).apply {
                            putExtra("nativeBee", clickedBee)
                        }
                        context.startActivity(intent)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

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
            color = PolibeeDarkGreen,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun NativeBeeCard(bee: NativeBee, onClick: (NativeBee) -> Unit) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(260.dp)
            .clickable { onClick(bee) },
        shape = RoundedCornerShape(25.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = bee.imageResId),
                contentDescription = bee.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .align(Alignment.BottomCenter)
                    .background(Color.Black.copy(alpha = 0.4f))
                    .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = bee.name,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                    maxLines = 1
                )
            }
        }
    }
}
