package com.example.polibee_v2.commercial.registration

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polibee_v2.MainActivity
import com.example.polibee_v2.R
import com.example.polibee_v2.access.PolibeeDarkGreen
import com.example.polibee_v2.nav.FavoritesActivity
import com.example.polibee_v2.nav.HistoryActivity
import com.example.polibee_v2.nav.ProfileActivity
import com.example.polibee_v2.ui.theme.Polibee_v2Theme
import com.example.polibee_v2.PolibeeOrange
import com.example.polibee_v2.montserratFamily
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

class RegisterHiveActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Polibee_v2Theme {
                RegisterHiveScreen(
                    onBackClick = { finish() },
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
fun RegisterHiveScreen(onBackClick: () -> Unit, onBottomNavItemClick: (Int) -> Unit) {
    val context = LocalContext.current
    var selectedBottomNavItem by remember { mutableStateOf(-1) }

    var selectedSpecies by remember { mutableStateOf("Selecione a Espécie") }
    val speciesOptions = listOf("Uruçu", "Jataí", "Mandaguari", "Mandaçaia", "Tiúba", "Bugia", "Guarupu")
    var speciesExpanded by remember { mutableStateOf(false) }

    var quantityOfHives by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    var selectedHiveSize by remember { mutableStateOf("Selecione o Tamanho") }
    val hiveSizeOptions = listOf("Pequeno (P)", "Médio (M)", "Grande (G)", "Extra Grande (XG)")
    var hiveSizeExpanded by remember { mutableStateOf(false) }

    var unitValue by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<String?>(null) } // Para imagem da colmeia

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Cadastrar Colmeia",
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = PolibeeDarkGreen
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = PolibeeDarkGreen
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
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
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Placeholder para imagem da colmeia
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color(0xFFE0E0E0)) // Fundo cinza claro
                    .border(1.dp, Color.Gray, RoundedCornerShape(15.dp))
                    .clickable {
                        Toast.makeText(context, "Adicionar Imagem da Colmeia clicado! (Implementar picker)", Toast.LENGTH_SHORT).show()
                    },
                contentAlignment = Alignment.Center
            ) {
                if (imageUri != null) {
                    // Se tiver imagem, carregaria aqui
                    // Image(painter = rememberImagePainter(imageUri), ...)
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.camera), // Ícone de câmera como placeholder
                        contentDescription = "Adicionar Imagem da Colmeia",
                        modifier = Modifier.size(60.dp),
                        colorFilter = ColorFilter.tint(Color.Gray)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Dropdown de Espécie da Abelha
            ExposedDropdownMenuBox(
                expanded = speciesExpanded,
                onExpandedChange = { speciesExpanded = !speciesExpanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedSpecies,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Espécie da Abelha", fontFamily = montserratFamily) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = speciesExpanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    textStyle = LocalTextStyle.current.copy(fontFamily = montserratFamily, color = Color.Black),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = PolibeeDarkGreen, unfocusedBorderColor = PolibeeDarkGreen,
                        focusedLabelColor = PolibeeDarkGreen, unfocusedLabelColor = PolibeeDarkGreen,
                        cursorColor = PolibeeDarkGreen, focusedTextColor = Color.Black, unfocusedTextColor = Color.Black
                    )
                )
                ExposedDropdownMenu(
                    expanded = speciesExpanded,
                    onDismissRequest = { speciesExpanded = false }
                ) {
                    speciesOptions.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption, fontFamily = montserratFamily, color = Color.White) },
                            onClick = {
                                selectedSpecies = selectionOption
                                speciesExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = quantityOfHives,
                onValueChange = { quantityOfHives = it.filter { char -> char.isDigit() } },
                label = { Text("Quantidade de Colmeias", fontFamily = montserratFamily) },
                textStyle = LocalTextStyle.current.copy(fontFamily = montserratFamily, color = Color.Black),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = PolibeeDarkGreen, unfocusedBorderColor = PolibeeDarkGreen,
                    focusedLabelColor = PolibeeDarkGreen, unfocusedLabelColor = PolibeeDarkGreen,
                    cursorColor = PolibeeDarkGreen, focusedTextColor = Color.Black, unfocusedTextColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Localização", fontFamily = montserratFamily) },
                textStyle = LocalTextStyle.current.copy(fontFamily = montserratFamily, color = Color.Black),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = PolibeeDarkGreen, unfocusedBorderColor = PolibeeDarkGreen,
                    focusedLabelColor = PolibeeDarkGreen, unfocusedLabelColor = PolibeeDarkGreen,
                    cursorColor = PolibeeDarkGreen, focusedTextColor = Color.Black, unfocusedTextColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Dropdown de Tamanho da Colmeia
            ExposedDropdownMenuBox(
                expanded = hiveSizeExpanded,
                onExpandedChange = { hiveSizeExpanded = !hiveSizeExpanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedHiveSize,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Tamanho da Colmeia", fontFamily = montserratFamily) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = hiveSizeExpanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    textStyle = LocalTextStyle.current.copy(fontFamily = montserratFamily, color = Color.Black),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = PolibeeDarkGreen, unfocusedBorderColor = PolibeeDarkGreen,
                        focusedLabelColor = PolibeeDarkGreen, unfocusedLabelColor = PolibeeDarkGreen,
                        cursorColor = PolibeeDarkGreen, focusedTextColor = Color.Black, unfocusedTextColor = Color.Black
                    )
                )
                ExposedDropdownMenu(
                    expanded = hiveSizeExpanded,
                    onDismissRequest = { hiveSizeExpanded = false }
                ) {
                    hiveSizeOptions.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption, fontFamily = montserratFamily, color = Color.White) },
                            onClick = {
                                selectedHiveSize = selectionOption
                                hiveSizeExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = unitValue,
                onValueChange = { unitValue = it.filter { char -> char.isDigit() || char == ',' || char == '.' } },
                label = { Text("Valor da Unidade", fontFamily = montserratFamily) },
                textStyle = LocalTextStyle.current.copy(fontFamily = montserratFamily, color = Color.Black),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = PolibeeDarkGreen, unfocusedBorderColor = PolibeeDarkGreen,
                    focusedLabelColor = PolibeeDarkGreen, unfocusedLabelColor = PolibeeDarkGreen,
                    cursorColor = PolibeeDarkGreen, focusedTextColor = Color.Black, unfocusedTextColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (selectedSpecies == "Selecione a Espécie" || quantityOfHives.isBlank() || location.isBlank() ||
                        selectedHiveSize == "Selecione o Tamanho" || unitValue.isBlank()) {
                        Toast.makeText(context, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Colmeia Publicada!", Toast.LENGTH_SHORT).show()
                        // TODO: Implementar lógica para salvar a colmeia
                        onBackClick() // Volta para a tela anterior após publicar
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PolibeeOrange),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "Publicar",
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = PolibeeDarkGreen // Texto do botão em verde escuro
                )
            }
        }
    }
}