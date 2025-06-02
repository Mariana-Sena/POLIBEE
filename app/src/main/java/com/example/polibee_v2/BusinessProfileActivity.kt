// src/main/java/com/example/polibee_v2/BusinessProfileActivity.kt
package com.example.polibee_v2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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

class BusinessProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val offerItem = intent.getParcelableExtra<OfferItem>("offerItem")

        setContent {
            Polibee_v2Theme {
                if (offerItem != null) {
                    BusinessProfileScreen(
                        item = offerItem,
                        onBackClick = { finish() },
                        onChatClick = { companyName, companyImageRes ->
                            val intent = Intent(this, ChatActivity::class.java).apply {
                                putExtra("companyName", companyName)
                                putExtra("companyImageRes", companyImageRes)
                            }
                            startActivity(intent)
                        },
                        onRentClick = {
                            Toast.makeText(this, "Simulando aluguel de colmeia!", Toast.LENGTH_SHORT).show()
                        }
                    )
                } else {
                    Toast.makeText(this, "Erro: Dados da oferta não encontrados.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusinessProfileScreen(
    item: OfferItem,
    onBackClick: () -> Unit,
    onChatClick: (String, Int) -> Unit,
    onRentClick: () -> Unit
) {
    val context = LocalContext.current
    var isFavorite by remember { mutableStateOf(item.isFavorite) }
    val montserratFamily = FontFamily(
        Font(R.font.montserrat_regular),
        Font(R.font.montserrat_bold, FontWeight.Bold)
    )

    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = {
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
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .background(Color.White)
        ) {
            // Seção Superior
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                Image(
                    painter = painterResource(id = item.imageResId),
                    contentDescription = "Imagem da Empresa",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Botão de Voltar
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.seta_voltar),
                        contentDescription = "Voltar",
                        modifier = Modifier.size(30.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }

                // Ícone de Coração para Favorito
                IconButton(
                    onClick = { isFavorite = !isFavorite },
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
                        ),
                        contentDescription = "Favorito",
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }

                // Informações da Empresa (Nome, Localização, Rating)
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .padding(16.dp)
                ) {
                    Text(
                        text = item.companyName,
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
                                painter = painterResource(id = R.drawable.localizador),
                                contentDescription = "Localização",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = item.location,
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
                                text = item.rating.toString(),
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
                text = "R$ 100 / Mês ou negociar",
                fontFamily = montserratFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = PolibeeDarkGreen,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Botão Conversar
            Button(
                onClick = { onChatClick(item.companyName, item.imageResId) },
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

            // Descrição da Colmeia
            Text(
                text = "Esta colmeia oferece um ambiente ideal para a polinização e produção de mel de alta qualidade. Nossas abelhas são criadas de forma sustentável, garantindo um impacto positivo no ecossistema local. Perfeita para agricultura, jardins ou projetos de conservação ambiental. Oferecemos suporte completo e manutenção durante o período de aluguel. Contate-nos para mais detalhes e opções de personalização para suas necessidades.",
                fontFamily = montserratFamily,
                fontSize = 16.sp,
                color = PolibeeDarkGreen,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}