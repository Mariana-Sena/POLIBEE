package com.example.polibee_v2.marketplace

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import com.example.polibee_v2.R
import com.example.polibee_v2.premium.PaymentActivity
import com.example.polibee_v2.ui.theme.Polibee_v2Theme
import com.example.polibee_v2.access.PolibeeDarkGreen
import com.example.polibee_v2.PolibeeOrange
import com.example.polibee_v2.montserratFamily
import com.example.polibee_v2.MainActivity
import com.example.polibee_v2.nav.HistoryActivity
import com.example.polibee_v2.nav.FavoritesActivity
import com.example.polibee_v2.nav.ProfileActivity
import com.example.polibee_v2.marketplace.CartManager
import com.example.polibee_v2.marketplace.ProductDataSource
import com.example.polibee_v2.marketplace.Product
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class ProductDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val productId = intent.getStringExtra("productId")
        val product = productId?.let { ProductDataSource.getProductById(it) }

        setContent {
            Polibee_v2Theme {
                if (product != null) {
                    ProductDetailsScreen(
                        product = product,
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
                } else {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Produto não encontrado.", fontFamily = montserratFamily)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreen(
    product: Product,
    onBackClick: () -> Unit,
    onBottomNavItemClick: (Int) -> Unit
) {
    val context = LocalContext.current
    val favoriteState = remember { mutableStateOf(product.isFavorite) }
    var showAddedToCartPopup by remember { mutableStateOf(false) }
    var selectedBottomNavItem by remember { mutableStateOf(-1) }

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
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
                        .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                ) {
                    val items = listOf("Home", "Histórico", "Favoritos", "Perfil")
                    val icons = listOf(
                        R.drawable.home,
                        R.drawable.clock,
                        R.drawable.heart,
                        R.drawable.profile
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
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.Black
                    )
                }
            }

            Image(
                painter = painterResource(id = product.imageUrl),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Text(
                    text = product.name.uppercase(Locale.ROOT),
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = Color.Black
                )
                Text(
                    text = product.companyName,
                    fontFamily = montserratFamily,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(product.price),
                            fontFamily = montserratFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            color = Color.Black
                        )
                        Text(
                            text = product.installmentOption,
                            fontFamily = montserratFamily,
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        IconButton(
                            onClick = {
                                ProductDataSource.toggleFavorite(product)
                                favoriteState.value = !favoriteState.value
                            }
                        ) {
                            Icon(
                                imageVector = if (favoriteState.value) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = "Favoritar",
                                tint = if (favoriteState.value) Color.Red else Color.Black,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                        IconButton(onClick = {
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, "Confira este produto: ${product.name} - ${product.description}")
                            }
                            context.startActivity(Intent.createChooser(shareIntent, "Compartilhar produto"))
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Share,
                                contentDescription = "Compartilhar",
                                tint = Color.Black,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        val intent = Intent(context, PaymentActivity::class.java)
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PolibeeOrange),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "COMPRAR",
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = PolibeeDarkGreen
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    onClick = {
                        CartManager.addProduct(product)
                        showAddedToCartPopup = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = PolibeeDarkGreen),
                    border = BorderStroke(1.dp, PolibeeDarkGreen),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "ADICIONAR À SACOLA",
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                ProductDetailsTabs(product = product)
            }
        }

        AnimatedVisibility(
            visible = showAddedToCartPopup,
            enter = slideInVertically(initialOffsetY = { fullHeight -> fullHeight }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { fullHeight -> fullHeight }) + fadeOut()
        ) {
            val coroutineScope = rememberCoroutineScope()
            LaunchedEffect(Unit) {
                coroutineScope.launch {
                    delay(2000)
                    showAddedToCartPopup = false
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable(enabled = false) {},
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    modifier = Modifier
                        .width(280.dp)
                        .height(180.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    color = Color.White
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.sacola),
                            contentDescription = "Adicionado à Sacola",
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Adicionado à Sacola!",
                            fontFamily = montserratFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun ProductDetailsTabs(product: Product) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Infos", "Avaliar", "Pagar")

    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.White,
            contentColor = PolibeeOrange,
            modifier = Modifier.fillMaxWidth(),
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = PolibeeOrange,
                    height = 2.dp
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            fontFamily = montserratFamily,
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Medium,
                            color = if (selectedTabIndex == index) PolibeeOrange else Color.Black,
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (selectedTabIndex) {
            0 -> ProductInformationTab(product = product)
            1 -> ProductReviewsTab(product = product)
            2 -> ProductPaymentMethodsTab()
        }
    }
}

@Composable
fun ProductInformationTab(product: Product) {
    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        Text("Descrição:", fontFamily = montserratFamily, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        Text(product.description, fontFamily = montserratFamily, fontSize = 14.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Especificações:", fontFamily = montserratFamily, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "- Peso: 500g\n- Dimensões: 10x10x15cm\n- Material: Plástico Reciclado\n- Validade: 2 anos",
            fontFamily = montserratFamily,
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductReviewsTab(product: Product) {
    val reviews = remember { ProductDataSource.dummyReviews[product.id] ?: mutableStateListOf() }
    var newComment by remember { mutableStateOf("") }
    var newRating by remember { mutableIntStateOf(0) }

    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        Text("Deixe sua avaliação:", fontFamily = montserratFamily, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            (1..5).forEach { star ->
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Estrela $star",
                    tint = if (star <= newRating) PolibeeOrange else Color.Gray,
                    modifier = Modifier.size(32.dp).clickable { newRating = star }
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = newComment,
            onValueChange = { newComment = it },
            label = { Text("Seu comentário", fontFamily = montserratFamily) },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 4,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = PolibeeDarkGreen,
                unfocusedBorderColor = Color.LightGray,
                cursorColor = PolibeeDarkGreen,
                focusedLabelColor = PolibeeDarkGreen,
                unfocusedLabelColor = Color.Gray,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            textStyle = LocalTextStyle.current.copy(fontFamily = montserratFamily, color = Color.Black)
        )
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (newComment.isNotBlank() && newRating > 0) {
                    val newReview = Review(
                        id = "rev${(reviews.size + 1).toString().padStart(3, '0')}",
                        author = "Você",
                        rating = newRating,
                        comment = newComment,
                        date = "09/06/2025"
                    )
                    reviews.add(newReview)
                    newComment = ""
                    newRating = 0
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = PolibeeDarkGreen),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Publicar Avaliação", fontFamily = montserratFamily, color = Color.White)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Avaliações dos clientes:", fontFamily = montserratFamily, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))

        if (reviews.isEmpty()) {
            Text("Nenhuma avaliação ainda. Seja o primeiro a comentar!", fontFamily = montserratFamily, color = Color.Gray)
        } else {
            LazyColumn(
                userScrollEnabled = false,
                modifier = Modifier.heightIn(max = 400.dp)
            ) {
                items(reviews) { review ->
                    ReviewItem(review = review)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun ReviewItem(review: Review) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(review.author, fontFamily = montserratFamily, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Black)
                Text(review.date, fontFamily = montserratFamily, fontSize = 12.sp, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                repeat(review.rating) {
                    Icon(Icons.Filled.Star, contentDescription = null, tint = PolibeeOrange, modifier = Modifier.size(16.dp))
                }
                repeat(5 - review.rating) {
                    Icon(Icons.Filled.Star, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(review.comment, fontFamily = montserratFamily, fontSize = 14.sp, color = Color.Black)
        }
    }
}

@Composable
fun ProductPaymentMethodsTab() {
    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        Text("Opções de Pagamento:", fontFamily = montserratFamily, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "- Cartão de Crédito/Débito: Aceitamos as principais bandeiras (Visa, Mastercard, Elo, American Express). Parcelamento em até 10x sem juros.",
            fontFamily = montserratFamily,
            fontSize = 14.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "- Boleto Bancário: Pagamento à vista com 5% de desconto.",
            fontFamily = montserratFamily,
            fontSize = 14.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "- Pix: Pagamento instantâneo com 7% de desconto.",
            fontFamily = montserratFamily,
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}
