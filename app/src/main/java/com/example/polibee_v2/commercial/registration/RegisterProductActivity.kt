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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polibee_v2.MainActivity
import com.example.polibee_v2.R
import com.example.polibee_v2.access.PolibeeDarkGreen
import com.example.polibee_v2.nav.FavoritesActivity
import com.example.polibee_v2.nav.HistoryActivity
import com.example.polibee_v2.nav.ProfileActivity
import com.example.polibee_v2.ui.theme.Polibee_v2Theme
import com.example.polibee_v2.PolibeeOrange // Importa sua cor PolibeeOrange
import com.example.polibee_v2.montserratFamily // Importa sua fonte montserratFamily
import androidx.compose.foundation.layout.navigationBarsPadding // Importa navigationBarsPadding
import androidx.compose.foundation.text.KeyboardOptions

class RegisterProductActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Polibee_v2Theme {
                RegisterProductScreen(
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
fun RegisterProductScreen(onBackClick: () -> Unit, onBottomNavItemClick: (Int) -> Unit) {
    val context = LocalContext.current
    var selectedBottomNavItem by remember { mutableStateOf(-1) }

    var productName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Selecione a Categoria") }
    val categories = listOf("Mel", "Pólen", "Própolis", "Cera", "Apitoxina", "Subprodutos", "Equipamentos")
    var categoryExpanded by remember { mutableStateOf(false) }

    var unitOfSale by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var quantityInStock by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<String?>(null) } // Para imagem do produto

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Cadastrar Produto",
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
            // Placeholder para imagem do produto
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color(0xFFE0E0E0)) // Fundo cinza claro
                    .border(1.dp, Color.Gray, RoundedCornerShape(15.dp))
                    .clickable {
                        Toast.makeText(context, "Adicionar Imagem clicado! (Implementar picker)", Toast.LENGTH_SHORT).show()
                    },
                contentAlignment = Alignment.Center
            ) {
                if (imageUri != null) {
                    // Se tiver imagem, carregaria aqui
                    // Image(painter = rememberImagePainter(imageUri), ...)
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.camera), // Ícone de câmera como placeholder
                        contentDescription = "Adicionar Imagem do Produto",
                        modifier = Modifier.size(60.dp),
                        colorFilter = ColorFilter.tint(Color.Gray)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = productName,
                onValueChange = { productName = it },
                label = { Text("Nome do Produto", fontFamily = montserratFamily) },
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

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descrição", fontFamily = montserratFamily) },
                textStyle = LocalTextStyle.current.copy(fontFamily = montserratFamily, color = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = PolibeeDarkGreen, unfocusedBorderColor = PolibeeDarkGreen,
                    focusedLabelColor = PolibeeDarkGreen, unfocusedLabelColor = PolibeeDarkGreen,
                    cursorColor = PolibeeDarkGreen, focusedTextColor = Color.Black, unfocusedTextColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Dropdown de Categoria
            ExposedDropdownMenuBox(
                expanded = categoryExpanded,
                onExpandedChange = { categoryExpanded = !categoryExpanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedCategory,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Categoria", fontFamily = montserratFamily) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded) },
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
                    expanded = categoryExpanded,
                    onDismissRequest = { categoryExpanded = false }
                ) {
                    categories.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption, fontFamily = montserratFamily, color = Color.White) },
                            onClick = {
                                selectedCategory = selectionOption
                                categoryExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = unitOfSale,
                onValueChange = { unitOfSale = it },
                label = { Text("Unidade de Venda (ex: kg, unidade, litro)", fontFamily = montserratFamily) },
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it.filter { char -> char.isDigit() || char == ',' || char == '.' } },
                    label = { Text("Preço", fontFamily = montserratFamily) },
                    textStyle = LocalTextStyle.current.copy(fontFamily = montserratFamily, color = Color.Black),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = PolibeeDarkGreen, unfocusedBorderColor = PolibeeDarkGreen,
                        focusedLabelColor = PolibeeDarkGreen, unfocusedLabelColor = PolibeeDarkGreen,
                        cursorColor = PolibeeDarkGreen, focusedTextColor = Color.Black, unfocusedTextColor = Color.Black
                    )
                )
                Spacer(modifier = Modifier.width(16.dp))
                OutlinedTextField(
                    value = quantityInStock,
                    onValueChange = { quantityInStock = it.filter { char -> char.isDigit() } },
                    label = { Text("Qtd. em Estoque", fontFamily = montserratFamily) },
                    textStyle = LocalTextStyle.current.copy(fontFamily = montserratFamily, color = Color.Black),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = PolibeeDarkGreen, unfocusedBorderColor = PolibeeDarkGreen,
                        focusedLabelColor = PolibeeDarkGreen, unfocusedLabelColor = PolibeeDarkGreen,
                        cursorColor = PolibeeDarkGreen, focusedTextColor = Color.Black, unfocusedTextColor = Color.Black
                    )
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (productName.isBlank() || description.isBlank() || selectedCategory == "Selecione a Categoria" ||
                        unitOfSale.isBlank() || price.isBlank() || quantityInStock.isBlank()) {
                        Toast.makeText(context, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Produto Publicado!", Toast.LENGTH_SHORT).show()
                        // TODO: Implementar lógica para salvar o produto
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