package com.example.polibee_v2.rent

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polibee_v2.MainActivity
import com.example.polibee_v2.PolibeeOrange
import com.example.polibee_v2.R
import com.example.polibee_v2.access.PolibeeDarkGreen
import com.example.polibee_v2.nav.FavoritesActivity
import com.example.polibee_v2.nav.HistoryActivity
import com.example.polibee_v2.nav.ProfileActivity
import com.example.polibee_v2.ui.theme.Polibee_v2Theme
import com.example.polibee_v2.montserratFamily

class ChatActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val companyName = intent.getStringExtra("companyName") ?: "Empresa Desconhecida"
        val companyImageRes = intent.getIntExtra("companyImageRes", R.drawable.placeholder_image)

        setContent {
            Polibee_v2Theme {
                ChatScreen(
                    companyName = companyName,
                    companyImageRes = companyImageRes,
                    onBackClick = { finish() },
                    onBottomNavItemClick = { index ->
                        when (index) {
                            0 -> startActivity(Intent(this, MainActivity::class.java))
                            1 -> startActivity(Intent(this, HistoryActivity::class.java))
                            2 -> startActivity(Intent(this, FavoritesActivity::class.java))
                            3 -> startActivity(Intent(this, ProfileActivity::class.java))
                        }
                        finish() // Finaliza a ChatActivity se navegar para outra tela principal
                    }
                )
            }
        }
    }
}

data class ChatMessage(val text: String, val isUser: Boolean, val timestamp: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    companyName: String,
    companyImageRes: Int,
    onBackClick: () -> Unit,
    onBottomNavItemClick: (Int) -> Unit
) {
    val context = LocalContext.current

    var messageInput by remember { mutableStateOf("") }
    var selectedBottomNavItem by remember { mutableStateOf(-1) } // -1 para não selecionar nenhum por padrão

    val chatMessages = remember {
        mutableStateListOf(
            ChatMessage("Oi, Boa Noite. Fiquei interessado em suas colmeias como podemos negociar?", false, "12:25"), // Inverti para vir do outro lado
            ChatMessage("Boa noite Gabriel, qual seria sua necessidade no momento?", true, "12:25"),
            ChatMessage("Eu preciso de 3 colmeias para um projeto de polinização de curto prazo. Vocês oferecem aluguel por projeto?", false, "12:26"),
            ChatMessage("Sim, oferecemos. Para projetos de curta duração, temos planos personalizados. Você poderia me dar mais detalhes sobre a duração e o local?", true, "12:27"),
            ChatMessage("Claro! O projeto seria por aproximadamente 2 meses, na região de Campinas.", false, "12:28")
        )
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = companyImageRes),
                            contentDescription = "Foto da Empresa",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            companyName,
                            fontFamily = montserratFamily,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Image(
                            painter = painterResource(id = R.drawable.seta_voltar),
                            contentDescription = "Voltar",
                            modifier = Modifier.size(30.dp),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PolibeeDarkGreen)
            )
        },
        bottomBar = {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { Toast.makeText(context, "Anexar arquivo em breve!", Toast.LENGTH_SHORT).show() }) {
                        Image(
                            painter = painterResource(id = R.drawable.clip),
                            contentDescription = "Anexar",
                            modifier = Modifier.size(24.dp),
                            colorFilter = ColorFilter.tint(PolibeeDarkGreen)
                        )
                    }
                    OutlinedTextField(
                        value = messageInput,
                        onValueChange = { messageInput = it },
                        placeholder = { Text("Digite sua mensagem...", fontFamily = montserratFamily) },
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                        singleLine = true,
                        shape = RoundedCornerShape(24.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = PolibeeDarkGreen,
                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                            cursorColor = PolibeeDarkGreen,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Send)
                    )
                    IconButton(
                        onClick = {
                            if (messageInput.isNotBlank()) {
                                chatMessages.add(ChatMessage(messageInput, true, "Agora"))
                                messageInput = ""
                            }
                        },
                        enabled = messageInput.isNotBlank()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.enviar),
                            contentDescription = "Enviar",
                            modifier = Modifier.size(24.dp),
                            colorFilter = ColorFilter.tint(if (messageInput.isNotBlank()) PolibeeOrange else Color.Gray) // Botão de envio laranja quando ativo
                        )
                    }
                }
                val items = listOf("Home", "Histórico", "Favoritos", "Perfil")
                val icons = listOf(
                    R.drawable.home,
                    R.drawable.clock,
                    R.drawable.heart,
                    R.drawable.profile
                )

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
                            .padding(horizontal = 0.dp)
                            .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
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
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 8.dp)
        ) {
            items(chatMessages) { message ->
                ChatBubble(message = message, montserratFamily = montserratFamily)
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage, montserratFamily: FontFamily) {
    val bubbleColor = if (message.isUser) Color(0xFFFFFFFF) else Color(0xFFE5B532)
    val textColor = Color.Black
    val alignment = if (message.isUser) Alignment.End else Alignment.Start

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = bubbleColor),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = message.text,
                    fontFamily = montserratFamily,
                    fontSize = 16.sp,
                    color = textColor
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = message.timestamp,
                    fontFamily = montserratFamily,
                    fontSize = 10.sp,
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}