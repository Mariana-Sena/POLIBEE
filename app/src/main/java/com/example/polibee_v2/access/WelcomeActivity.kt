package com.example.polibee_v2.access

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polibee_v2.R
import com.example.polibee_v2.ui.theme.Polibee_v2Theme

class WelcomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Polibee_v2Theme {
                WelcomeScreen(
                    onStartHereClicked = {
                        startActivity(Intent(this, LoginActivity::class.java))
                    },
                    onAccessibilityClicked = {
                        // startActivity(Intent(this, AccessibilityActivity::class.java))
                    }
                )
            }
        }
    }
}

@Composable
fun WelcomeScreen(
    onStartHereClicked: () -> Unit,
    onAccessibilityClicked: () -> Unit
) {
    // Definir a fonte Montserrat
    val montserratFamily = FontFamily(
        Font(R.font.montserrat_regular),
        Font(R.font.montserrat_bold, FontWeight.Bold)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Imagem de fundo com contentScale ajustado para cobrir toda a tela sem gaps
        Image(
            painter = painterResource(id = R.drawable.img_welcome_ajustada),
            contentDescription = "Background Welcome",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop  // Elimina os gaps
        )

        // Conteúdo sobreposto usando eixos fixos em vez de pesos
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo no topo com posição fixa
            Spacer(modifier = Modifier.height(48.dp)) // Espaço fixo do topo

            Image(
                painter = painterResource(id = R.drawable.polibee_bee),
                contentDescription = "Logo Polibee",
                modifier = Modifier.size(100.dp)
            )

            // Área central mais flexível
            Spacer(modifier = Modifier.weight(10f))

            // Texto "Junte-se à POLIBEE" com tamanho aumentado
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Junte-se à ")
                    }
                    append("POLI")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("BEE")
                    }
                },
                style = TextStyle(
                    fontFamily = montserratFamily,
                    fontSize = 36.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(horizontal = 10.dp)
            )

            // Área flexível antes dos botões (mais peso para empurrar botões para cima)
            Spacer(modifier = Modifier.weight(1.5f))

            // Botões mais para cima - usando padding com peso
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 48.dp) // Fixado a 48dp do fundo
            ) {
                // Botão 'Comece Aqui'
                Button(
                    onClick = onStartHereClicked,
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107))
                ) {
                    Text(
                        text = "Comece Aqui",
                        fontFamily = montserratFamily,
                        color = Color.DarkGray,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Botão de acessibilidade
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable(onClick = onAccessibilityClicked),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.accessibility),
                        contentDescription = "Icone de Acessibilidade",
                        modifier = Modifier.size(80.dp)
                    )
                }
            }
        }
    }
}