package com.example.polibee_v2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polibee_v2.ui.theme.Polibee_v2Theme

class ForgotPasswordActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Polibee_v2Theme {
                ForgotPasswordScreen(
                    onBackClicked = { finish() },
                    onContinueClick = { email ->
                        // Simular envio de e-mail/código e navegar para a tela de confirmação
                        if (email.isNotBlank() && email.contains("@")) {
                            Toast.makeText(this, "Simulando envio de código para \$email", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, VerifyEmailConfirmationActivity::class.java))
                            // Não finalizar esta activity aqui para permitir retorno
                        } else {
                            Toast.makeText(this, "Por favor, insira um e-mail válido.", Toast.LENGTH_SHORT).show()
                        }
                    },
                    onRecoverWithPhoneClick = {
                        // TODO: Implementar fluxo de recuperação por telefone, se necessário
                        Toast.makeText(this, "Recuperar com número em desenvolvimento.", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    onBackClicked: () -> Unit,
    onContinueClick: (String) -> Unit,
    onRecoverWithPhoneClick: () -> Unit
) {
    val context = LocalContext.current
    val montserratFamily = FontFamily(
        Font(R.font.montserrat_regular),
        Font(R.font.montserrat_bold, FontWeight.Bold)
    )

    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }

    // Cores dos campos de texto (reaproveitando do CadastroScreen)
    val customTextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = PolibeeDarkGreen,
        focusedLabelColor = PolibeeDarkGreen,
        cursorColor = PolibeeDarkGreen,
        unfocusedBorderColor = PolibeeDarkGreen,
        unfocusedLabelColor = PolibeeDarkGreen
    )

    GetStartedScaffold(
        showBack = true,
        onBackClick = onBackClicked
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Centraliza o conteúdo verticalmente
        ) {
            Spacer(modifier = Modifier.height(10.dp)) // Espaçamento superior ajustado

            Text(
                text = "Recuperar Senha",
                fontFamily = montserratFamily,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = PolibeeDarkGreen
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Informe o e-mail utilizado no cadastro",
                fontFamily = montserratFamily,
                fontSize = 16.sp,
                color = PolibeeDarkGreen,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = !it.contains("@")
                },
                label = { Text("E-mail", fontFamily = montserratFamily) },
                singleLine = true,
                isError = emailError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                colors = customTextFieldColors
            )
            if (emailError) {
                Text("E-mail inválido.", color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = { onContinueClick(email) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107))
            ) {
                Text(
                    "Continuar",
                    color = Color.White,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = onRecoverWithPhoneClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Recuperar com Número",
                    color = PolibeeDarkGreen,
                    fontFamily = montserratFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}