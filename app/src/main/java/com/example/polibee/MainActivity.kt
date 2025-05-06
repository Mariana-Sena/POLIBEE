package com.example.polibee

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polibee.ui.theme.PolibeeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PolibeeTheme {
                MainScreen(
                    onLogoutClicked = {
                        // Implementação do logout
                        logout()
                    }
                )
            }
        }
    }

    // Função para realizar o logout
    private fun logout() {
        // Alterar o status de login para false nas SharedPreferences
        val sharedPreferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("isLoggedIn", false).apply()

        // Navegar de volta para WelcomeActivity (não LoginActivity diretamente)
        val intent = Intent(this, WelcomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onLogoutClicked: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Polibee") },
                actions = {
                    // Botão de logout na barra superior
                    IconButton(onClick = onLogoutClicked) {
                        Icon(
                            imageVector = Icons.Filled.ExitToApp,
                            contentDescription = "Sair"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFFC107),
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        // Conteúdo principal da tela
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Bem-vindo à Polibee",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFC107)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Você está logado com sucesso!",
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Botão de logout alternativo (além do ícone na AppBar)
                Button(
                    onClick = onLogoutClicked,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFC107)
                    )
                ) {
                    Text(
                        text = "Sair da conta",
                        color = Color.White
                    )
                }
            }
        }
    }
}