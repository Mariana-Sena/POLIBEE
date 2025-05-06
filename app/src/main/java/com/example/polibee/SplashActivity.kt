package com.example.polibee

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.polibee.ui.theme.PolibeeTheme
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PolibeeTheme {
                SplashScreen {
                    // Verificar APENAS se está logado - alterado conforme solicitado
                    val sharedPreferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
                    val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

                    // Decisão simplificada: logado -> MainActivity, não logado -> WelcomeActivity
                    if (isLoggedIn) {
                        // Usuário já logado - ir direto para MainActivity
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        // Não está logado - ir para Welcome page sempre
                        startActivity(Intent(this, WelcomeActivity::class.java))
                    }
                    finish()
                }
            }
        }
    }
}

@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {
    // Animação de rotação com velocidade 2000ms = 2 segundos
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotate"
    )

    // Tempo de exibição da splash screen
    LaunchedEffect(key1 = true) {
        delay(3000)
        onSplashFinished()
    }

    // Layout da splash screen usando Box para posicionamento absoluto
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFC107)),
    ) {
        // Logo girando no centro da tela
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.flower_logo),
                contentDescription = "Logo Polibee",
                modifier = Modifier
                    .size(200.dp)
                    .rotate(angle)
            )
        }

        // Logo de texto posicionado na parte inferior
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 100.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.polibee_sp),
                contentDescription = "Nome Polibee",
                modifier = Modifier
                    .width(200.dp)
                    .height(80.dp)
            )
        }
    }
}