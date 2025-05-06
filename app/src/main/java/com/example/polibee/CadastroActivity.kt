
package com.example.polibee
//código com problema no building
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.polibee.ui.theme.PolibeeTheme
import java.util.regex.Pattern

class CadastroActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PolibeeTheme {
                CadastroScreen(
                    onBackClicked = { finish() },
                    onRegisterSuccess = {
                        // Exemplo de Toast para simular sucesso no cadastro
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

@Composable
fun CadastroScreen(
    onBackClicked: () -> Unit,
    onRegisterSuccess: () -> Unit,
    onLoginClicked: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var termsAccepted by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    val inputErrorColor = Color.Red

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
                painter = painterResource(id = R.drawable.favo),
                contentDescription = "Imagem decorativa",
                modifier = Modifier.size(250.dp),
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

            // Campo Nome Completo
            OutlinedTextField(
                value = name,
                onValueChange = { name = it.filter { char -> char.isLetter() || char.isWhitespace() } },
                label = { Text("Nome Completo") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo Número de Celular
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it.filter { char -> char.isDigit() } },
                label = { Text("Número de Celular") },
                singleLine = true,
                leadingIcon = {
                    Text("+55") // Pré-seleção para o Brasil como exemplo
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo E-mail
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-mail") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo Senha
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Senha") },
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
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo Confirmar Senha
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirmar Senha") },
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
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Caixa de seleção para termos de uso
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = termsAccepted,
                    onCheckedChange = { termsAccepted = it }
                )
                Text(
                    text = buildAnnotatedString {
                        append("Aceito ")
                        withStyle(style = SpanStyle(color = Color(0xFFFFC107), textDecoration = TextDecoration.Underline)) {
                            append("Termos de uso & Política de Privacidade")
                        }
                    },
                    modifier = Modifier.clickable {
                        // Abrir a página de termos de uso (não implementado ainda)
                        Toast.makeText(null, "Abrindo termos de uso...", Toast.LENGTH_SHORT).show()
                    }
                )
            }

            // Botão de Cadastro
            Button(
                onClick = {
                    if (termsAccepted && password == confirmPassword && isValidPassword2(password)) {
                        onRegisterSuccess()
                    } else if (!termsAccepted) {
                        Toast.makeText(null, "Aceite os Termos de Uso.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(null, "Verifique os campos.", Toast.LENGTH_SHORT).show()
                    }
                },
                enabled = termsAccepted,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text("Cadastrar-se")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Já tem conta? Login
            Text(
                text = buildAnnotatedString {
                    append("Já tem conta? ")
                    withStyle(style = SpanStyle(color = Color(0xFFFFC107))) {
                        append("Login")
                    }
                },
                modifier = Modifier.clickable(onClick = onLoginClicked)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Continue com Google
            OutlinedButton(
                onClick = { Toast.makeText(null, "Continue com Google em breve!", Toast.LENGTH_SHORT).show() },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.google_icon),
                        contentDescription = "Google Icon",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Continue com Google")
                }
            }
        }
    }
}

// Validação de senha
fun isValidPassword2(password: String): Boolean {
    val pattern = Pattern.compile(
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\S+\$).{8,}\$"
    )
    return pattern.matcher(password).matches()

    val senha = "Senha@123"
    println(isValidPassword2(senha)) // Deve retornar true
}