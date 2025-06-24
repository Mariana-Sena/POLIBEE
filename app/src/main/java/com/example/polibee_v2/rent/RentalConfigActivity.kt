package com.example.polibee_v2.rent

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polibee_v2.ui.theme.Polibee_v2Theme
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import com.example.polibee_v2.nav.FavoritesActivity
import com.example.polibee_v2.nav.HistoryActivity
import com.example.polibee_v2.MainActivity
import com.example.polibee_v2.PolibeeOrange
import com.example.polibee_v2.nav.ProfileActivity
import com.example.polibee_v2.R
import com.example.polibee_v2.access.PolibeeDarkGreen
import com.example.polibee_v2.montserratFamily
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.input.ImeAction
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RentalConfigActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Polibee_v2Theme {
                RentalConfigScreen(
                    onBackClick = { finish() },
                    onAdvanceClick = {
                        startActivity(Intent(this, CompanyListActivity::class.java))
                    },
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
fun RentalConfigScreen(
    onBackClick: () -> Unit,
    onAdvanceClick: () -> Unit,
    onBottomNavItemClick: (Int) -> Unit
) {
    var selectedBottomNavItem by remember { mutableStateOf(-1) }
    var rentalDuration by remember { mutableStateOf("") }
    var numHiveBoxes by remember { mutableStateOf("") }

    // Para Tamanho da Caixa
    val boxSizes = listOf("12x12", "15x15", "20x20", "Outro")
    var selectedBoxSize by remember { mutableStateOf(boxSizes[0]) }
    var expandedBoxSize by remember { mutableStateOf(false) }

    // Para Entrega em (DatePicker)
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    var deliveryDate by remember { mutableStateOf("") }

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(selectedYear, selectedMonth, selectedDayOfMonth)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            deliveryDate = dateFormat.format(selectedCalendar.time)
        }, year, month, day
    )

    val beeTypes = listOf("Jataí", "Mandaguari", "Uruçu", "Apis Mellifera", "Outra")
    var selectedBeeType by remember { mutableStateOf(beeTypes[0]) }
    var expandedBeeType by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Image(
                painter = painterResource(id = R.drawable.favo),
                contentDescription = "Padrão Favo de Mel",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = 100.dp, y = 50.dp)
                    .size(200.dp)
                    .alpha(0.15f)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.seta_voltar),
                            contentDescription = "Voltar",
                            tint = Color.Black,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Image(
                        painter = painterResource(id = R.drawable.logo_bgwhite),
                        contentDescription = "Polibee Logo",
                        modifier = Modifier
                            .width(170.dp)
                            .padding(start = 70.dp)
                    )

                    Spacer(Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Configurar o Aluguel",
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                OutlinedTextField(
                    value = rentalDuration,
                    onValueChange = { newValue ->
                        if (newValue.all { it.isDigit() }) {
                            rentalDuration = newValue
                        }
                    },
                    label = { Text("Período de Polinização (Dias)", fontFamily = montserratFamily) },
                    placeholder = { Text("20", color = Color.LightGray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                    shape = RoundedCornerShape(12.dp),
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

                OutlinedTextField(
                    value = numHiveBoxes,
                    onValueChange = { newValue ->
                        if (newValue.all { it.isDigit() }) {
                            numHiveBoxes = newValue
                        }
                    },
                    label = { Text("Quantidade de Caixas", fontFamily = montserratFamily) },
                    placeholder = { Text("10", color = Color.LightGray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                    shape = RoundedCornerShape(12.dp),
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

                ExposedDropdownMenuBox(
                    expanded = expandedBoxSize,
                    onExpandedChange = { expandedBoxSize = !expandedBoxSize },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {
                    OutlinedTextField(
                        value = selectedBoxSize,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Tamanho da Caixa", fontFamily = montserratFamily) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedBoxSize) },
                        shape = RoundedCornerShape(12.dp),
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
                    ExposedDropdownMenu(
                        expanded = expandedBoxSize,
                        onDismissRequest = { expandedBoxSize = false }
                    ) {
                        boxSizes.forEach { size ->
                            DropdownMenuItem(
                                text = { Text(size) },
                                onClick = {
                                    selectedBoxSize = size
                                    expandedBoxSize = false
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = deliveryDate,
                    onValueChange = { /* readOnly */ },
                    readOnly = true,
                    label = { Text("Entrega em", fontFamily = montserratFamily) },
                    placeholder = { Text("DD/MM/AAAA", color = Color.LightGray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .clickable { datePickerDialog.show() },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = PolibeeDarkGreen,
                        unfocusedBorderColor = PolibeeDarkGreen,
                        focusedLabelColor = PolibeeDarkGreen,
                        unfocusedLabelColor = PolibeeDarkGreen,
                        cursorColor = PolibeeDarkGreen,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    trailingIcon = {
                        IconButton(onClick = { datePickerDialog.show() }) { // Abre o DatePicker no click do ícone
                            // Assumindo que você tem um drawable para o ícone de calendário
                            Icon(
                                painter = painterResource(id = R.drawable.calendar_icon),
                                contentDescription = "Selecionar Data",
                                tint = PolibeeDarkGreen
                            )
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Tipo de Abelha (Dropdown)
                ExposedDropdownMenuBox(
                    expanded = expandedBeeType,
                    onExpandedChange = { expandedBeeType = !expandedBeeType },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {
                    OutlinedTextField(
                        value = selectedBeeType,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Tipo de Abelha", fontFamily = montserratFamily) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedBeeType) },
                        shape = RoundedCornerShape(12.dp),
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
                    ExposedDropdownMenu(
                        expanded = expandedBeeType,
                        onDismissRequest = { expandedBeeType = false }
                    ) {
                        beeTypes.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type) },
                                onClick = {
                                    selectedBeeType = type
                                    expandedBeeType = false
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = onAdvanceClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(horizontal = 24.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PolibeeOrange)
                ) {
                    Text("Avançar", color = PolibeeDarkGreen, fontFamily = montserratFamily, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}