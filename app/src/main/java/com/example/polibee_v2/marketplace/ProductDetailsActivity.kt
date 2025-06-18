// src/main/java/com/example/polibee_v2/marketplace/ProductDetailsActivity.kt
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
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polibee_v2.premium.PaymentActivity // Certifique-se que esta importação está correta
import com.example.polibee_v2.R
import com.example.polibee_v2.ui.theme.Polibee_v2Theme
import com.example.polibee_v2.ui.theme.PolibeeDarkGreen // Verifique se estas cores estão definidas
import com.example.polibee_v2.ui.theme.PolibeeOrange // Verifique se estas cores estão definidas
import com.example.polibee_v2.ui.theme.montserratFamily // Verifique se esta fonte está definida
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
                    ProductDetailsScreen(product = product, onBackClick = { finish() })
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
fun ProductDetailsScreen(product: Product, onBackClick: () -> Unit) {
    val context = LocalContext.current
    val favoriteState = remember { mutableStateOf(product.isFavorite) }
    var showAddedToCartPopup by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { }, // Título vazio conforme o protótipo
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        val intent = Intent(context, CartActivity::class.java)
                        context.startActivity(intent)
                    }) {
                        Icon(
                            Icons.Filled.ShoppingCart,
                            contentDescription = "Sacola",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White) // Fundo branco
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
                .verticalScroll(rememberScrollState()) // Permite rolagem para todo o conteúdo
        ) {
            // Imagem do Produto
            Image(
                painter = painterResource(id = product.imageUrl),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
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
                        Spacer(modifier = Modifier.height(8.dp))
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
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        IconButton(
                            onClick = {
                                ProductDataSource.toggleFavorite(product)
                                favoriteState.value = !favoriteState.value // Atualiza o estado local
                            }
                        ) {
                            Icon(
                                imageVector = if (favoriteState.value) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = "Favoritar",
                                tint = Color.Red,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
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

                // Botões Comprar e Adicionar à Sacola
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
                        color = Color.White
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
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = PolibeeOrange // Defina a cor do conteúdo (e da borda) aqui
                    ),
                    // Remova a linha abaixo, pois ela causava o erro:
                    // border = ButtonDefaults.outlinedButtonBorder.copy(color = PolibeeOrange),
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

                // Abas para Informações, Avaliações, Formas de Pagamento
                ProductDetailsTabs(product = product)
            }
        }

        // Pop-up "Adicionado à Sacola"
        AnimatedVisibility(
            visible = showAddedToCartPopup,
            enter = slideInVertically(initialOffsetY = { fullHeight -> fullHeight }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { fullHeight -> fullHeight }) + fadeOut()
        ) {
            val coroutineScope = rememberCoroutineScope()
            LaunchedEffect(Unit) {
                coroutineScope.launch {
                    delay(2000) // Exibe por 2 segundos
                    showAddedToCartPopup = false
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable(enabled = false) {}, // Impede interação com o fundo
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
                            painter = painterResource(id = R.drawable.sacola), // Substitua pelo seu ícone de sino de compra
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
    val tabs = listOf("Informações", "Avaliações", "Formas de Pagamento")

    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.White,
            contentColor = PolibeeOrange, // Cor para o indicador e texto da aba selecionada
            modifier = Modifier.fillMaxWidth()
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            fontFamily = montserratFamily,
                            fontWeight = FontWeight.Medium,
                            color = if (selectedTabIndex == index) PolibeeOrange else Color.Gray
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
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Descrição:",
            fontFamily = montserratFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = product.description,
            fontFamily = montserratFamily,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Especificações:",
            fontFamily = montserratFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "- Peso: 500g\n- Dimensões: 10x10x15cm\n- Material: Plástico Reciclado\n- Validade: 2 anos", // Dados dummy
            fontFamily = montserratFamily,
            fontSize = 14.sp
        )
    }
}

@Composable
fun ProductReviewsTab(product: Product) {
    val reviews = remember { ProductDataSource.dummyReviews[product.id] ?: mutableStateListOf() }
    var newComment by remember { mutableStateOf("") }
    var newRating by remember { mutableIntStateOf(0) } // 0 significa nenhuma estrela selecionada

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Deixe sua avaliação:",
            fontFamily = montserratFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Entrada de classificação por estrelas
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
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { newRating = star }
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Entrada de comentário
        OutlinedTextField(
            value = newComment,
            onValueChange = { newComment = it },
            label = { Text("Seu comentário", fontFamily = montserratFamily) },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 4,
            textStyle = LocalTextStyle.current.copy(fontFamily = montserratFamily)
        )
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (newComment.isNotBlank() && newRating > 0) {
                    val newReview = Review(
                        id = "rev${(reviews.size + 1).toString().padStart(3, '0')}",
                        author = "Você", // Placeholder para o usuário atual
                        rating = newRating,
                        comment = newComment,
                        date = "09/06/2025" // Data atual
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

        Text(
            text = "Avaliações dos clientes:",
            fontFamily = montserratFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (reviews.isEmpty()) {
            Text(
                text = "Nenhuma avaliação ainda. Seja o primeiro a comentar!",
                fontFamily = montserratFamily,
                color = Color.Gray
            )
        } else {
            LazyColumn(
                userScrollEnabled = false, // Para evitar rolagem aninhada conflitante
                modifier = Modifier.heightIn(max = 400.dp) // Limita a altura para rolagem das reviews
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
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0)), // Cor de fundo para o card
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = review.author,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    text = review.date,
                    fontFamily = montserratFamily,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
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
            Text(
                text = review.comment,
                fontFamily = montserratFamily,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun ProductPaymentMethodsTab() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Opções de Pagamento:",
            fontFamily = montserratFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "- Cartão de Crédito/Débito: Aceitamos as principais bandeiras (Visa, Mastercard, Elo, American Express). Parcelamento em até 10x sem juros.",
            fontFamily = montserratFamily,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "- Boleto Bancário: Pagamento à vista com 5% de desconto.",
            fontFamily = montserratFamily,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "- Pix: Pagamento instantâneo com 7% de desconto.",
            fontFamily = montserratFamily,
            fontSize = 14.sp
        )
    }
}