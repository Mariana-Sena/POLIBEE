package com.example.polibee

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polibee.ui.theme.PolibeeTheme
import java.util.regex.Pattern

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PolibeeTheme {
                LoginScreen(
                    onBackClicked = { finish() },
                    onForgotPasswordClick = {
                        // Adicione o Intent para a tela de recuperação de senha
                        Toast.makeText(this, "Indo para recuperar senha", Toast.LENGTH_SHORT).show()
                    },
                    onRegisterClick = {
//                        startActivity(Intent(this, CadastroActivity::class.java))
                    },
                    onLoginSuccess = {
                        // Temporariamente apenas exibe success. O banco será adicionado futuramente
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

@Composable
fun LoginScreen(
    onBackClicked: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    val customColor = Color(0xFF243434)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Imagem decorativa no canto inferior esquerdo
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomStart
        ) {
            Image(
                painter = painterResource(id = R.drawable.favo_cortado),
                contentDescription = "Imagem decorativa",
                modifier = Modifier.size(250.dp), // Ajuste o tamanho conforme necessário
                contentScale = ContentScale.Fit
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Botão de voltar e logo alinhados
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botão de voltar
                Image(
                    painter = painterResource(id = R.drawable.seta_voltar),
                    contentDescription = "Voltar",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable(onClick = onBackClicked)
                )

                Spacer(modifier = Modifier.width(70.dp))

                // Logo
                Image(
                    painter = painterResource(id = R.drawable.logo_bgwhite),
                    contentDescription = "Logo Polibee",
                    modifier = Modifier
                        .height(90.dp)
                        .size(120.dp)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            val montserratFamily = FontFamily(
                Font(R.font.montserrat_regular),
                Font(R.font.montserrat_bold, FontWeight.Bold)
            )

            // Título Login
            Text(
                text = "Login",
                fontFamily = montserratFamily,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Campo de E-mail
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = !it.contains("@") // Validação simples do e-mail
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

            // Campo de Senha
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = !isValidPassword(it) // Valida se a senha atende às regras
                },
                label = { Text("Senha", fontFamily = montserratFamily) },
                singleLine = true,
                isError = passwordError,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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

            // Link Esqueceu a senha
            Text(
                text = "Esqueceu a senha?",
                fontFamily = montserratFamily,
                color = customColor,
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.End).clickable(onClick = onForgotPasswordClick)
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Botão Entrar
            Button(
                onClick = {
                    if (!emailError && !passwordError && email.isNotEmpty() && password.isNotEmpty()) {
                        onLoginSuccess()
                    } else {
                        Toast.makeText(
                            null,
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

            // Texto de cadastro
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
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.Black)) {
                        append("ou")
                    }
                },
                fontSize = 14.sp,
                fontFamily = montserratFamily,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))


            // Alternativa de login com Google (ou outros serviços)
            OutlinedButton(
                onClick = { Toast.makeText(null, "Login com Google em breve!", Toast.LENGTH_SHORT).show() },
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
                    Spacer(modifier = Modifier.width(8.dp),)
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

// Validação da senha
fun isValidPassword(password: String): Boolean {
    val pattern = Pattern.compile(
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\S+\$).{8,}\$"
    )
    return pattern.matcher(password).matches()
}