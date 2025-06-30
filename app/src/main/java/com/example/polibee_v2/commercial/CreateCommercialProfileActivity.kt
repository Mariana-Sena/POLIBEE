package com.example.polibee_v2.commercial

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polibee_v2.R
import com.example.polibee_v2.access.PolibeeDarkGreen
import com.example.polibee_v2.commercial.CommercialProfileRepository
import com.example.polibee_v2.commercial.CommercialProfile
import com.example.polibee_v2.ui.theme.Polibee_v2Theme
import com.example.polibee_v2.PolibeeOrange // Importa sua cor PolibeeOrange
import com.example.polibee_v2.montserratFamily // Importa sua fonte montserratFamily
import java.util.UUID // Importa para gerar o ID único
import androidx.compose.foundation.layout.navigationBarsPadding // Importa navigationBarsPadding
import com.example.polibee_v2.MainActivity
import com.example.polibee_v2.nav.FavoritesActivity
import com.example.polibee_v2.nav.HistoryActivity
import com.example.polibee_v2.nav.ProfileActivity

class CreateCommercialProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Polibee_v2Theme {
                CreateCommercialProfileScreen(
                    onBottomNavItemClick = { index ->
                        when (index) {
                            0 -> startActivity(Intent(this, MainActivity::class.java))
                            1 -> startActivity(Intent(this, HistoryActivity::class.java))
                            2 -> startActivity(Intent(this, FavoritesActivity::class.java))
                            3 -> startActivity(Intent(this, ProfileActivity::class.java))
                        }
                        finish()
                    }
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCommercialProfileScreen(onBottomNavItemClick: (Int) -> Unit) {
    val context = LocalContext.current

    var selectedBottomNavItem by remember { mutableStateOf(0) }
    var companyName by remember { mutableStateOf("") }
    var cpfCnpj by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<String?>(null) } // Para armazenar URI da imagem selecionada

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Crie seu perfil comercial",
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.Bold,
                        color = PolibeeDarkGreen,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(start = 16.dp, top = 20.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { (context as? ComponentActivity)?.finish() }) {
                        Image(
                            painter = painterResource(id = R.drawable.seta_voltar), // Certifique-se que você tem este drawable
                            contentDescription = "Voltar",
                            modifier = Modifier.padding(start = 16.dp, top = 20.dp).size(50.dp),
                            colorFilter = ColorFilter.tint(PolibeeDarkGreen)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = { // BARRA DE NAVEGAÇÃO ADICIONADA AQUI!
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
                    val items = listOf("Home", "Histórico", "Favoritos", "Perfil")
                    val icons = listOf(
                        R.drawable.home, R.drawable.clock, R.drawable.heart, R.drawable.profile
                    )
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
        },
        containerColor = Color.White // Fundo branco para a tela
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Placeholder para seleção de imagem
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE0E0E0)) // Fundo cinza claro
                    .border(1.dp, Color.Gray, CircleShape)
                    .clickable {
                        // TODO: Aqui você implementaria a lógica para abrir a galeria/câmera
                        Toast.makeText(context, "Adicionar Foto clicado! (Implementar picker)", Toast.LENGTH_SHORT).show()
                    },
                contentAlignment = Alignment.Center
            ) {
                if (imageUri != null) {
                    // Aqui você carregaria a imagem selecionada usando Coil/Glide se tiver a URI
                    // Ex: Image(painter = rememberImagePainter(imageUri), ...)
                    // Usando ícone placeholder por enquanto
                    Image(
                        painter = painterResource(id = R.drawable.camera), // Certifique-se que você tem este drawable
                        contentDescription = "Foto do perfil",
                        modifier = Modifier.size(60.dp),
                        colorFilter = ColorFilter.tint(Color.Gray)
                    )
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(id = R.drawable.camera), // Certifique-se que você tem este drawable
                            contentDescription = "Adicionar Foto",
                            modifier = Modifier.size(60.dp),
                            colorFilter = ColorFilter.tint(Color.Gray)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Adicionar Foto",
                            fontFamily = montserratFamily,
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Campo "Nome da Empresa"
            OutlinedTextField(
                value = companyName,
                onValueChange = { companyName = it },
                label = { Text("Nome da Empresa", fontFamily = montserratFamily) },
                textStyle = LocalTextStyle.current.copy(fontFamily = montserratFamily),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = PolibeeDarkGreen,
                    unfocusedBorderColor = PolibeeDarkGreen,
                    focusedLabelColor = PolibeeDarkGreen,
                    unfocusedLabelColor = PolibeeDarkGreen,
                    cursorColor = PolibeeDarkGreen,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo "CPF ou CNPJ"
            OutlinedTextField(
                value = cpfCnpj,
                onValueChange = { newValue ->
                    cpfCnpj = newValue.filter { it.isDigit() } // Permite apenas dígitos
                },
                label = { Text("CPF ou CNPJ", fontFamily = montserratFamily) },
                textStyle = LocalTextStyle.current.copy(fontFamily = montserratFamily),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = PolibeeDarkGreen,
                    unfocusedBorderColor = PolibeeDarkGreen,
                    focusedLabelColor = PolibeeDarkGreen,
                    unfocusedLabelColor = PolibeeDarkGreen,
                    cursorColor = PolibeeDarkGreen,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo "Descrição sobre a empresa ou produção"
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descreva sua empresa ou produção", fontFamily = montserratFamily) },
                textStyle = LocalTextStyle.current.copy(fontFamily = montserratFamily),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp), // Campo multi-linha
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = PolibeeDarkGreen,
                    unfocusedBorderColor = PolibeeDarkGreen,
                    focusedLabelColor = PolibeeDarkGreen,
                    unfocusedLabelColor = PolibeeDarkGreen,
                    cursorColor = PolibeeDarkGreen,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(45.dp))

            // Botão "Salvar e Continuar"
            Button(
                onClick = {
                    if (companyName.isBlank() || cpfCnpj.isBlank() || description.isBlank()) {
                        Toast.makeText(context, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
                    } else {
                        // Reusa o ID existente se o perfil já estiver carregado (para edição), senão gera um novo
                        val profileId = CommercialProfileRepository.getProfile()?.id ?: UUID.randomUUID().toString()
                        val newProfile = CommercialProfile(
                            id = profileId,
                            imageUri = imageUri,
                            companyName = companyName,
                            cpfCnpj = cpfCnpj,
                            description = description
                        )
                        CommercialProfileRepository.saveProfile(newProfile) // Salva o único perfil
                        Toast.makeText(context, "Perfil comercial salvo com sucesso!", Toast.LENGTH_SHORT).show()
                        context.startActivity(Intent(context, WhatToDoActivity::class.java)) // Vai para WhatToDo
                        (context as? ComponentActivity)?.finish() // Finaliza esta Activity
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PolibeeOrange),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "Salvar e Continuar",
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = PolibeeDarkGreen
                )
            }
        }
    }
}