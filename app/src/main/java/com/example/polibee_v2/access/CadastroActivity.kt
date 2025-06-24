package com.example.polibee_v2.access

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polibee_v2.ui.theme.Polibee_v2Theme
import androidx.compose.ui.graphics.SolidColor
import com.example.polibee_v2.GetStartedScaffold
import com.example.polibee_v2.R
import com.example.polibee_v2.isValidPassword
import com.example.polibee_v2.montserratFamily

// Definição da cor personalizada para reutilização
val PolibeeDarkGreen = Color(0xFF0D2016)

class CadastroActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Polibee_v2Theme {
                CadastroScreen(
                    onBackClicked = { finish() },
                    onRegisterSuccess = {
                        Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                        finish()
                    },
                    onLoginClicked = {
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroScreen(
    onBackClicked: () -> Unit,
    onRegisterSuccess: () -> Unit,
    onLoginClicked: () -> Unit
) {
    val context = LocalContext.current // Obtém o Context para uso nos Toasts

    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var termsAccepted by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    // Cores comuns para os OutlinedTextFields
    val customTextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = PolibeeDarkGreen, // Cor da borda quando focado
        focusedLabelColor = PolibeeDarkGreen,  // Cor do label quando focado
        cursorColor = PolibeeDarkGreen,       // Cor do cursor
        unfocusedBorderColor = PolibeeDarkGreen, // Cor da borda quando não focado
        unfocusedLabelColor = PolibeeDarkGreen, // Cor do label quando não focado
        focusedTextColor = Color.Black,
        unfocusedTextColor = Color.Black
    )

    GetStartedScaffold(
        showBack = true,
        onBackClick = onBackClicked
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
                .padding(innerPadding), // Aplica o padding do Scaffold (para não sobrepor a TopBar)
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Centraliza verticalmente o conteúdo, se houver espaço
        ) {
            // Ajustes nos Spacers para caber tudo na tela
            Spacer(modifier = Modifier.height(10.dp)) // Espaçamento superior ajustado

            Text(
                text = "Cadastre-se",
                fontFamily = montserratFamily,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = PolibeeDarkGreen
            )

            Spacer(modifier = Modifier.height(20.dp)) // Espaçamento reduzido

            OutlinedTextField(
                value = name,
                onValueChange = { name = it.filter { char -> char.isLetter() || char.isWhitespace() } },
                textStyle = LocalTextStyle.current.copy(fontFamily = montserratFamily, color = Color.Black),
                label = { Text("Nome Completo", fontFamily = montserratFamily) },
                singleLine = true,
                isError = false,
                modifier = Modifier.fillMaxWidth(),
                colors = customTextFieldColors
            )

            Spacer(modifier = Modifier.height(12.dp)) // Espaçamento reduzido

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it.filter { char -> char.isDigit() } },
                textStyle = LocalTextStyle.current.copy(fontFamily = montserratFamily, color = Color.Black),
                label = { Text("Número de Celular", fontFamily = montserratFamily) },
                singleLine = true,
                leadingIcon = { Text("+55", color = PolibeeDarkGreen) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = false,
                modifier = Modifier.fillMaxWidth(),
                colors = customTextFieldColors
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                textStyle = LocalTextStyle.current.copy(fontFamily = montserratFamily, color = Color.Black),
                label = { Text("E-mail", fontFamily = montserratFamily) },
                singleLine = true,
                isError = false,
                modifier = Modifier.fillMaxWidth(),
                colors = customTextFieldColors
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                textStyle = LocalTextStyle.current.copy(fontFamily = montserratFamily, color = Color.Black),
                label = { Text("Senha", fontFamily = montserratFamily) },
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (passwordVisible) {
                        painterResource(id = R.drawable.openicon_eye)
                    } else {
                        painterResource(id = R.drawable.closedicon_eye)
                    }
                    Image(
                        painter = icon,
                        contentDescription = if (passwordVisible) "Ocultar Senha" else "Mostrar Senha",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { passwordVisible = !passwordVisible }
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = false,
                modifier = Modifier.fillMaxWidth(),
                colors = customTextFieldColors
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                textStyle = LocalTextStyle.current.copy(fontFamily = montserratFamily, color = Color.Black),
                label = { Text("Confirmar Senha", fontFamily = montserratFamily) },
                singleLine = true,
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (confirmPasswordVisible) {
                        painterResource(id = R.drawable.openicon_eye)
                    } else {
                        painterResource(id = R.drawable.closedicon_eye)
                    }
                    Image(
                        painter = icon,
                        contentDescription = if (confirmPasswordVisible) "Ocultar Senha" else "Mostrar Senha",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { confirmPasswordVisible = !confirmPasswordVisible }
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = false,
                modifier = Modifier.fillMaxWidth(),
                colors = customTextFieldColors
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = termsAccepted,
                    onCheckedChange = { termsAccepted = it },
                    colors = CheckboxDefaults.colors(checkedColor = PolibeeDarkGreen, uncheckedColor = PolibeeDarkGreen)
                )
                Text(
                    text = buildAnnotatedString {
                        append("Aceito ")
                        withStyle(style = SpanStyle(color = Color(0xFFFFC107), textDecoration = TextDecoration.Underline)) {
                            append("Termos de uso & Política de Privacidade")
                        }
                    },
                    modifier = Modifier.clickable {
                        Toast.makeText(context, "Link Termos de Uso em breve!", Toast.LENGTH_SHORT).show()
                    },
                    color = Color.Black,
                    fontFamily = montserratFamily,
                    fontSize = 9.sp, // Fonte um pouco menor para economizar espaço
                )
            }

            Button(
                onClick = {
                    if (termsAccepted && password == confirmPassword && isValidPassword(password)) {
                        onRegisterSuccess()
                    } else if (!termsAccepted) {
                        Toast.makeText(context, "Aceite os Termos de Uso.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Verifique os campos.", Toast.LENGTH_SHORT).show()
                    }
                },
                enabled = termsAccepted,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107))
            ) {
                Text(
                    "Cadastrar-se",
                    color = PolibeeDarkGreen,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = buildAnnotatedString {
                    append("Já tem conta? ")
                    withStyle(style = SpanStyle(color = Color(0xFFFFC107))) {
                        append("Login")
                    }
                },
                modifier = Modifier.clickable(onClick = onLoginClicked),
                color = Color.Black,
                fontFamily = montserratFamily,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(10.dp))


            OutlinedButton(
                onClick = { Toast.makeText(context, "Continue com Google em breve!", Toast.LENGTH_SHORT).show() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = PolibeeDarkGreen,
                    containerColor = Color.White
                ),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    width = 1.dp,
                    brush = SolidColor(PolibeeDarkGreen)
                )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.google_icon),
                        contentDescription = "Google Icon",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Continue com Google",
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp)) // Espaço final para não ficar "colado"
        }
    }
}