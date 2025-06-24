package com.example.polibee_v2.rent

import android.content.Intent
import android.os.Bundle
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polibee_v2.ui.theme.Polibee_v2Theme
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import com.example.polibee_v2.nav.FavoritesActivity
import com.example.polibee_v2.nav.HistoryActivity
import com.example.polibee_v2.MainActivity
import com.example.polibee_v2.PolibeeOrange
import com.example.polibee_v2.access.PolibeeDarkGreen
import com.example.polibee_v2.montserratFamily
import com.example.polibee_v2.R
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import com.example.polibee_v2.nav.ProfileActivity

class PropertyInfoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Polibee_v2Theme {
                PropertyInfoScreen(
                    onBackClick = { finish() },
                    onAdvanceClick = {
                        startActivity(Intent(this, RentalConfigActivity::class.java))
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

class CepVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length >= 8) text.text.substring(0..7) else text.text
        var out = ""
        for (i in trimmed.indices) {
            out += trimmed[i]
            if (i == 4 && i < trimmed.length - 1) out += "-"
        }

        val offsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 4) return offset
                if (offset <= 8) return offset + 1
                return 9
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 5) return offset
                if (offset <= 9) return offset - 1
                return 8
            }
        }

        return TransformedText(AnnotatedString(out), offsetTranslator)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertyInfoScreen(
    onBackClick: () -> Unit,
    onAdvanceClick: () -> Unit,
    onBottomNavItemClick: (Int) -> Unit
) {
    var selectedBottomNavItem by remember { mutableStateOf(-1) }
    var propertyName by remember { mutableStateOf("") }
    var cep by remember { mutableStateOf("") }
    var cultivatedArea by remember { mutableStateOf("") }

    val landMeasures = listOf("Ha", "m²", "Km²", "Acres")
    var selectedLandMeasure by remember { mutableStateOf(landMeasures[0]) }
    var expandedLandMeasure by remember { mutableStateOf(false) }

    val cropTypes = listOf("Morango", "Milho", "Soja", "Café", "Outra")
    var selectedCropType by remember { mutableStateOf(cropTypes[0]) }
    var expandedCropType by remember { mutableStateOf(false) }
    var otherCropText by remember { mutableStateOf("") }
    val showOtherCropField = selectedCropType == "Outra"

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
                        .padding(top = 30.dp),
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

                    Spacer(modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Informe a Propriedade",
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                OutlinedTextField(
                    value = propertyName,
                    onValueChange = { propertyName = it },
                    label = { Text("Nome da Propriedade (Opcional)", fontFamily = montserratFamily) },
                    placeholder = { Text("Fazenda Boa Esperança", color = Color.LightGray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedTextField(
                        value = cep,
                        onValueChange = { newValue ->
                            if (newValue.length <= 8 && newValue.all { it.isDigit() }) {
                                cep = newValue
                            }
                        },
                        label = { Text("CEP", fontFamily = montserratFamily) },
                        placeholder = { Text("07263-015", color = Color.LightGray) },
                        modifier = Modifier
                            .weight(0.65f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                        visualTransformation = CepVisualTransformation(),
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
                    Spacer(modifier = Modifier.width(8.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = cultivatedArea,
                        onValueChange = { newValue ->
                            if (newValue.all { it.isDigit() }) {
                                cultivatedArea = newValue
                            }
                        },
                        label = { Text("Tamanho da Área", fontFamily = montserratFamily) },
                        placeholder = { Text("30", color = Color.LightGray) },
                        modifier = Modifier
                            .weight(0.5f),
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
                    Spacer(modifier = Modifier.width(8.dp))
                    ExposedDropdownMenuBox(
                        expanded = expandedLandMeasure,
                        onExpandedChange = { expandedLandMeasure = !expandedLandMeasure },
                        modifier = Modifier.weight(0.5f)
                    ) {
                        OutlinedTextField(
                            value = selectedLandMeasure,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Medida de Terra", fontFamily = montserratFamily) },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedLandMeasure) },
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
                            expanded = expandedLandMeasure,
                            onDismissRequest = { expandedLandMeasure = false }
                        ) {
                            landMeasures.forEach { measure ->
                                DropdownMenuItem(
                                    text = { Text(measure) },
                                    onClick = {
                                        selectedLandMeasure = measure
                                        expandedLandMeasure = false
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                ExposedDropdownMenuBox(
                    expanded = expandedCropType,
                    onExpandedChange = { expandedCropType = !expandedCropType },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {
                    OutlinedTextField(
                        value = selectedCropType,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Tipo de Cultura", fontFamily = montserratFamily) },
                        placeholder = { Text("Morango", color = Color.LightGray) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCropType) },
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
                        expanded = expandedCropType,
                        onDismissRequest = { expandedCropType = false }
                    ) {
                        cropTypes.forEach { crop ->
                            DropdownMenuItem(
                                text = { Text(crop) },
                                onClick = {
                                    selectedCropType = crop
                                    expandedCropType = false
                                }
                            )
                        }
                    }
                }

                if (showOtherCropField) {
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = otherCropText,
                        onValueChange = { newValue ->
                            if (newValue.all { it.isLetter() || it.isWhitespace() }) {
                                otherCropText = newValue
                            }
                        },
                        label = { Text("Qual outra cultura?", fontFamily = montserratFamily) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
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
