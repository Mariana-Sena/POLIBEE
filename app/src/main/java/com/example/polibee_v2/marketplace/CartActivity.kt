package com.example.polibee_v2.marketplace

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polibee_v2.ui.theme.Polibee_v2Theme
import com.example.polibee_v2.ui.theme.PolibeeDarkGreen
import com.example.polibee_v2.ui.theme.PolibeeOrange
import com.example.polibee_v2.ui.theme.montserratFamily
import java.text.NumberFormat
import java.util.Locale


class CartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Polibee_v2Theme {
                CartScreen(onBackClick = { finish() })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(onBackClick: () -> Unit) {
    val context = LocalContext.current
    val cartItems = CartManager.cartItems

    // Valores dummy para frete e desconto
    val shippingCost = 15.00
    val discount = 10.00
    val subtotal = CartManager.calculateSubtotal()
    val total = subtotal + shippingCost - discount

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Sacola",
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            if (cartItems.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Sua sacola está vazia.\nAdicione produtos para continuar!",
                        fontFamily = montserratFamily,
                        fontSize = 18.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f) // Ocupa o espaço disponível
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(cartItems) { cartItem ->
                        CartItemRow(cartItem = cartItem)
                    }
                }

                // Seção de Frete e Total
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(Color.White, RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Calcular Frete",
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = "12345-678", // CEP dummy
                        onValueChange = { /* Não funcional */ },
                        label = { Text("CEP", fontFamily = montserratFamily) },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = LocalTextStyle.current.copy(fontFamily = montserratFamily),
                        readOnly = true // Conforme protótipo, é exibido, não para entrada
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Receber em casa",
                            fontFamily = montserratFamily,
                            fontSize = 16.sp
                        )
                        Text(
                            text = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(shippingCost),
                            fontFamily = montserratFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                    CartSummaryRow(label = "Subtotal de produtos:", value = subtotal)
                    CartSummaryRow(label = "Frete:", value = shippingCost)
                    CartSummaryRow(label = "Desconto:", value = -discount, isDiscount = true)
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Valor Total:",
                            fontFamily = montserratFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = PolibeeDarkGreen
                        )
                        Text(
                            text = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(total),
                            fontFamily = montserratFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = PolibeeDarkGreen
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val intent = Intent(context, PaymentActivity::class.java)
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PolibeeOrange),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "IR PARA PAGAMENTO",
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun CartItemRow(cartItem: CartItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = cartItem.product.imageUrl),
                contentDescription = cartItem.product.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = cartItem.product.name,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(cartItem.product.price),
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = PolibeeDarkGreen
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                IconButton(onClick = { CartManager.updateQuantity(cartItem.product, cartItem.quantity - 1) }) {
                    Icon(Icons.Filled.RemoveCircle, contentDescription = "Diminuir quantidade", tint = PolibeeOrange)
                }
                Text(
                    text = cartItem.quantity.toString(),
                    fontFamily = montserratFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                IconButton(onClick = { CartManager.updateQuantity(cartItem.product, cartItem.quantity + 1) }) {
                    Icon(Icons.Filled.AddCircle, contentDescription = "Aumentar quantidade", tint = PolibeeOrange)
                }
            }
        }
    }
}

@Composable
fun CartSummaryRow(label: String, value: Double, isDiscount: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontFamily = montserratFamily,
            fontSize = 14.sp,
            color = Color.Gray
        )
        Text(
            text = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(value),
            fontFamily = montserratFamily,
            fontSize = 14.sp,
            color = if (isDiscount) PolibeeDarkGreen else Color.Gray // Verde para desconto
        )
    }
}