// src/main/java/com/example/polibee_v2/ProfileActivity.kt
package com.example.polibee_v2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Polibee_v2Theme {
                ProfileScreen(
                    onBackClick = { finish() }, // Volta para a tela anterior
                    onBottomNavItemClick = { index ->
                        // Lógica de navegação do menu inferior (igual à MainActivity)
                        when (index) {
                            0 -> startActivity(Intent(this, MainActivity::class.java))
                            1 -> startActivity(Intent(this, HistoryActivity::class.java))
                            2 -> startActivity(Intent(this, FavoritesActivity::class.java))
                            3 -> { /* Já está no Perfil */ }
                        }
                        // Finaliza a ProfileActivity se navegar para outra tela principal
                        // (com exceção do próprio Perfil)
                        if (index != 3) finish()
                    }
                )
            }
        }
    }
}

@Composable
fun ProfileScreen(
    onBackClick: () -> Unit,
    onBottomNavItemClick: (Int) -> Unit
) {
    val context = LocalContext.current
    val montserratFamily = FontFamily(
        Font(R.font.montserrat_regular),
        Font(R.font.montserrat_bold, FontWeight.Bold)
    )

    var selectedBottomNavItem by remember { mutableStateOf(3) } // Perfil selecionado por padrão

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            // TopBar Customizada com Botão de Voltar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, start = 16.dp, end = 16.dp)
                    .height(64.dp)
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.seta_voltar),
                        contentDescription = "Voltar",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        },
        bottomBar = {
            // --- Barra de Navegação Inferior (Menu) - INTEGRADA AQUI ---
            val items = listOf("Home", "Histórico", "Favoritos", "Perfil")
            val icons = listOf(
                R.drawable.home,
                R.drawable.clock,
                R.drawable.heart,
                R.drawable.profile
            )

            NavigationBar(
                containerColor = PolibeeDarkGreen,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            ) {
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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp)) // Espaço abaixo da TopBar

            // Seção de Foto e Nome do Usuário
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.placeholder_image), // Foto do usuário
                    contentDescription = "Foto do Usuário",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color.Gray), // Fundo para a bolinha
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Gabriel Lindo", // Nome do Usuário
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = PolibeeDarkGreen
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Bloco do Menu de Opções
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = PolibeeDarkGreen) // Fundo verde escuro
            ) {
                Column(modifier = Modifier.padding(vertical = 16.dp)) {
                    ProfileMenuItem(
                        iconResId = R.drawable.customizeprofile, // Ícone de Personalizar Perfil
                        text = "Personalizar Perfil",
                        onClick = { Toast.makeText(context, "Personalizar Perfil", Toast.LENGTH_SHORT).show() }
                    )
                    ProfileMenuItem(
                        iconResId = R.drawable.privacysecurity, // Ícone de Privacidade & Segurança
                        text = "Privacidade & Segurança",
                        onClick = { context.startActivity(Intent(context, PrivacySecurityActivity::class.java)) }
                    )
                    ProfileMenuItem(
                        iconResId = R.drawable.mydata, // Ícone de Meus Dados
                        text = "Meus Dados",
                        onClick = { context.startActivity(Intent(context, MyDataActivity::class.java)) }
                    )
                    ProfileMenuItem(
                        iconResId = R.drawable.serviceshistory, // Ícone de Histórico de Serviços
                        text = "Histórico de Serviços",
                        onClick = { context.startActivity(Intent(context, HistoryActivity::class.java)) } // Já existe
                    )
                    ProfileMenuItem(
                        iconResId = R.drawable.mycards, // Ícone de Meus Cartões
                        text = "Meus Cartões",
                        onClick = { context.startActivity(Intent(context, MyCardsActivity::class.java)) }
                    )
                    ProfileMenuItem(
                        iconResId = R.drawable.rentorsell, // Ícone de Locar ou Vender
                        text = "Locar ou Vender",
                        onClick = { context.startActivity(Intent(context, RentOrSellActivity::class.java)) }
                    )
                    ProfileMenuItem(
                        iconResId = R.drawable.accessibility, // Ícone de Acessibilidade
                        text = "Acessibilidade",
                        onClick = { context.startActivity(Intent(context, AccessibilityActivity::class.java)) }
                    )
                    ProfileMenuItem(
                        iconResId = R.drawable.polibeesupport, // Ícone de Suporte POLIBEE
                        text = "Suporte POLIBEE",
                        onClick = { context.startActivity(Intent(context, PolibeeSupportActivity::class.java)) }
                    )
                    // Botão Sair da Conta (Logout)
                    ProfileMenuItem(
                        iconResId = R.drawable.logout, // Ícone de Sair da Conta
                        text = "Sair da Conta",
                        onClick = {
                            Toast.makeText(context, "Saindo da conta...", Toast.LENGTH_SHORT).show()
                            // Lógica de logout: limpar SharedPreferences, etc.
                            val intent = Intent(context, WelcomeActivity::class.java).apply {
                                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                            }
                            context.startActivity(intent)
                            // Não precisa do finish() aqui, pois o flag já limpa a pilha.
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(30.dp)) // Espaçamento para o final da tela
        }
    }
}

@Composable
fun ProfileMenuItem(iconResId: Int, text: String, onClick: () -> Unit) {
    val montserratFamily = FontFamily(
        Font(R.font.montserrat_regular),
        Font(R.font.montserrat_bold, FontWeight.Bold)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = text,
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(PolibeeOrange) // Ícones em amarelo
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                fontFamily = montserratFamily,
                fontSize = 16.sp,
                color = PolibeeOrange // Texto em amarelo
            )
        }
    }
}