// src/main/java/com/example/polibee_v2/MarketplaceActivity.kt
package com.example.polibee_v2.marketplace

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu // Ícone de filtro (3 riscos)
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polibee_v2.R
import com.example.polibee_v2.ui.theme.Polibee_v2Theme
import com.example.polibee_v2.ui.theme.PolibeeDarkGreen // Verifique se estas cores estão definidas
import com.example.polibee_v2.ui.theme.PolibeeOrange // Verifique se estas cores estão definidas
import com.example.polibee_v2.ui.theme.montserratFamily // Verifique se esta fonte está definida
import java.text.NumberFormat
import java.util.Locale

class MarketplaceActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Polibee_v2Theme {
                MarketplaceScreen(onBackClick = { finish() })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketplaceScreen(onBackClick: () -> Unit) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Logo (assumindo que você tem um drawable chamado polibee_logo_flower)
                        Image(
                            painter = painterResource(id = R.drawable.logo_pollinate), // Substitua pelo seu logo
                            contentDescription = "Polibee Logo",
                            modifier = Modifier
                                .size(40.dp)
                                .offset(x = (-16).dp) // Ajuste para centralizar visualmente
                        )
                        Spacer(modifier = Modifier.weight(1f)) // Empurra a sacola para a direita
                        IconButton(onClick = {
                            val intent = Intent(context, CartActivity::class.java)
                            context.startActivity(intent)
                        }) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Filled.ShoppingCart,
                                    contentDescription = "Sacola",
                                    tint = Color.Black
                                )
                                Text(
                                    text = "Sacola",
                                    fontFamily = montserratFamily,
                                    fontSize = 16.sp,
                                    color = Color.Black
                                )
                            }
                        }
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
        ) {
            // Barra de Busca (simulada)
            OutlinedTextField(
                value = "", // Não funcional por enquanto
                onValueChange = { /* Não faz nada */ },
                placeholder = { Text("Pesquisar", fontFamily = montserratFamily, color = Color.Gray) },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Pesquisar") },
                trailingIcon = {
                    IconButton(onClick = { /* Ação do filtro */ }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Filtrar")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PolibeeDarkGreen,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = PolibeeDarkGreen,
                    unfocusedLabelColor = Color.Gray
                ),
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(fontFamily = montserratFamily)
            )

            Text(
                text = "PRODUTOS PARA POLINIZAÇÃO",
                fontFamily = montserratFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = PolibeeOrange,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 16.dp)
            )

            // Grid de Produtos
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(ProductDataSource.allProducts) { product ->
                    ProductCard(product = product) {
                        val intent = Intent(context, ProductDetailsActivity::class.java).apply {
                            putExtra("productId", product.id)
                        }
                        context.startActivity(intent)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCard(product: Product, onClick: () -> Unit) {
    val favoriteState = remember { mutableStateOf(product.isFavorite) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray), // Fundo placeholder
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = product.imageUrl),
                    contentDescription = product.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                IconButton(
                    onClick = {
                        ProductDataSource.toggleFavorite(product)
                        favoriteState.value = !favoriteState.value // Atualiza o estado local
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                ) {
                    Icon(
                        imageVector = if (favoriteState.value) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Favoritar",
                        tint = Color.Red,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.name,
                fontFamily = montserratFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = Color.Black,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(product.price),
                fontFamily = montserratFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = PolibeeDarkGreen,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}