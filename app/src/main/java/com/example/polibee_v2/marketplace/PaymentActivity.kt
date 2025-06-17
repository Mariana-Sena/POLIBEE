// src/main/java/com/example/polibee_v2/PaymentActivity.kt
package com.example.polibee_v2.marketplace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polibee_v2.R
import com.example.polibee_v2.ui.theme.Polibee_v2Theme
import com.example.polibee_v2.ui.theme.PolibeeOrange
import com.example.polibee_v2.ui.theme.montserratFamily

class PaymentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Polibee_v2Theme {
                PaymentScreen(onBackClick = { finish() })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Pagamento",
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar")
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Fundo com padrão de colmeia para o cabeçalho (simulado)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(PolibeeOrange, RoundedCornerShape(12.dp)) // Usando PolibeeOrange como placeholder
                    .clip(RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Escolha sua forma de pagamento",
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            // Métodos de Pagamento
            PaymentMethodCard(
                iconResId = R.drawable.bar_code, // Ícone placeholder
                methodName = "Boleto",
                description = "Pagamento à vista com 5% de desconto"
            )
            Spacer(modifier = Modifier.height(16.dp))
            PaymentMethodCard(
                iconResId = R.drawable.card, // Ícone placeholder
                methodName = "Cartão de Crédito/Débito",
                description = "Em até 10x sem juros"
            )
            Spacer(modifier = Modifier.height(16.dp))
            PaymentMethodCard(
                iconResId = R.drawable.qrcode, // Ícone placeholder
                methodName = "QR Code",
                description = "Pagamento via aplicativo"
            )
            Spacer(modifier = Modifier.height(16.dp))
            PaymentMethodCard(
                iconResId = R.drawable.pix, // Ícone placeholder
                methodName = "Pix",
                description = "Pagamento instantâneo com 7% de desconto"
            )

            Spacer(modifier = Modifier.weight(1f)) // Empurra a mensagem de segurança para baixo

            Text(
                text = "Nosso sistema de pagamento é 100% seguro e eficaz. Frete para você!",
                fontFamily = montserratFamily,
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentMethodCard(iconResId: Int, methodName: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        onClick = { /* Lógica de seleção do método de pagamento */ },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = methodName,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = methodName,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Text(
                    text = description,
                    fontFamily = montserratFamily,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}