package com.example.polibee_v2

import android.content.Context
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
import androidx.compose.ui.platform.LocalContext // Import adicionado
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polibee_v2.ui.theme.Polibee_v2Theme
import com.example.polibee_v2.isValidPassword // Certifique-se de que está importada

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Polibee_v2Theme {
                LoginScreen(
                    onBackClicked = { finish() },
                    onForgotPasswordClick = {
                        // **Ajuste aqui:** Iniciar a ForgotPasswordActivity
                        startActivity(Intent(this, ForgotPasswordActivity::class.java))
                    },
                    onRegisterClick = {
                        startActivity(Intent(this, CadastroActivity::class.java))
                    },
                    onLoginSuccess = {
                        Toast.makeText(this, "Login Realizado!", Toast.LENGTH_SHORT).show()
                        val sharedPreferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
                        sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }
}

// Seu LoginScreen Composable (sem alterações no corpo, apenas o onForgotPasswordClick será chamado)
@Composable
fun LoginScreen(
    onBackClicked: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    val context = LocalContext.current // Adicionado para Toasts dentro do Composable
    val montserratFamily = FontFamily(
        Font(R.font.montserrat_regular),
        Font(R.font.montserrat_bold, FontWeight.Bold)
    )

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }
    val customColor = Color(0xFF243434) // Este customColor é do seu código anterior, mantive.

    GetStartedScaffold(
        showBack = true,
        onBackClick = onBackClicked
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = "Login",
                fontFamily = montserratFamily,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(30.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = !it.contains("@")
                },
                label = { Text("E-mail", fontFamily = montserratFamily) },
                singleLine = true,
                isError = emailError,
                modifier = Modifier.fillMaxWidth()
            )
            if (emailError) {
                Text("E-mail inválido.", color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = !isValidPassword(password)
                },
                label = { Text("Senha", fontFamily = montserratFamily) },
                singleLine = true,
                isError = passwordError,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val icon = if (passwordVisible) {
                        painterResource(id = R.drawable.openicon_eye)
                    } else {
                        painterResource(id = R.drawable.closedicon_eye)
                    }
                    Image(
                        painter = icon,
                        contentDescription = "Ver senha",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { passwordVisible = !passwordVisible }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            if (passwordError) {
                Text(
                    text = "Senha deve ter ao menos 8 caracteres, com maiúscula, minúscula, número e caractere especial.",
                    color = Color.Red,
                    fontFamily = montserratFamily,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Esqueceu a senha?",
                fontFamily = montserratFamily,
                color = customColor,
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.End).clickable(onClick = onForgotPasswordClick)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    if (!emailError && !passwordError && email.isNotEmpty() && password.isNotEmpty()) {
                        onLoginSuccess()
                    } else {
                        Toast.makeText(
                            context, // Use context aqui
                            "Corrija os erros antes de continuar.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107))
            ) {
                Text("Entrar", color = customColor, fontFamily = montserratFamily, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = buildAnnotatedString {
                    append("Não tem conta? ")
                    withStyle(style = SpanStyle(color = Color(0xFFFFC107))) {
                        append("Cadastre-se")
                    }
                },
                fontSize = 14.sp,
                fontFamily = montserratFamily,
                modifier = Modifier.clickable(onClick = onRegisterClick)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "ou",
                fontSize = 14.sp,
                fontFamily = montserratFamily,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(
                onClick = { Toast.makeText(context, "Login com Google em breve!", Toast.LENGTH_SHORT).show() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.google_icon),
                        contentDescription = "Google Icon",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Continue com Google",
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.Bold,
                        color = customColor
                    )
                }
            }
        }
    }
}