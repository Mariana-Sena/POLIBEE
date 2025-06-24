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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polibee_v2.GetStartedScaffold
import com.example.polibee_v2.R
import com.example.polibee_v2.ui.theme.Polibee_v2Theme
import com.example.polibee_v2.isValidPassword // Importa a função isValidPassword

class ResetPasswordActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Polibee_v2Theme {
                ResetPasswordScreen(
                    onBackClicked = { finish() },
                    onResetPasswordSuccess = {
                        // Para simular sucesso e retornar ao login
                        Toast.makeText(this, "Senha alterada com sucesso!", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish() // Finaliza esta activity e todas as anteriores do fluxo de recuperação
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordScreen(
    onBackClicked: () -> Unit,
    onResetPasswordSuccess: () -> Unit
) {
    val context = LocalContext.current
    val montserratFamily = FontFamily(
        Font(R.font.montserrat_regular),
        Font(R.font.montserrat_bold, FontWeight.Bold)
    )

    var newPassword by remember { mutableStateOf("") }
    var confirmNewPassword by remember { mutableStateOf("") }
    var newPasswordVisible by remember { mutableStateOf(false) }
    var confirmNewPasswordVisible by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }

    // Cores dos campos de texto (reaproveitando)
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
        ) {
            Spacer(modifier = Modifier.height(120.dp))

            Text(
                text = "Nova Senha",
                fontFamily = montserratFamily,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = PolibeeDarkGreen,
            )

            Spacer(modifier = Modifier.height(30.dp))

            OutlinedTextField(
                value = newPassword,
                onValueChange = {
                    newPassword = it
                    passwordError = !isValidPassword(newPassword)
                },
                label = { Text("Nova Senha", fontFamily = montserratFamily) },
                singleLine = true,
                isError = passwordError,
                visualTransformation = if (newPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val icon = if (newPasswordVisible) {
                        painterResource(id = R.drawable.openicon_eye)
                    } else {
                        painterResource(id = R.drawable.closedicon_eye)
                    }
                    Image(
                        painter = icon,
                        contentDescription = "Ver nova senha",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { newPasswordVisible = !newPasswordVisible }
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = customTextFieldColors
            )
            if (passwordError) {
                Text(
                    text = "Senha deve ter ao menos 8 caracteres, com maiúscula, minúscula, número e caractere especial.",
                    color = Color.Red,
                    fontFamily = montserratFamily,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = confirmNewPassword,
                onValueChange = {
                    confirmNewPassword = it
                    confirmPasswordError = (it != newPassword)
                },
                label = { Text("Confirmar Nova Senha", fontFamily = montserratFamily) },
                singleLine = true,
                isError = confirmPasswordError,
                visualTransformation = if (confirmNewPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val icon = if (confirmNewPasswordVisible) {
                        painterResource(id = R.drawable.openicon_eye)
                    } else {
                        painterResource(id = R.drawable.closedicon_eye)
                    }
                    Image(
                        painter = icon,
                        contentDescription = "Ver confirmar senha",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { confirmNewPasswordVisible = !confirmNewPasswordVisible }
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = customTextFieldColors
            )
            if (confirmPasswordError) {
                Text("As senhas não coincidem.", color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(80.dp))

            Button(
                onClick = onResetPasswordSuccess,
                enabled = newPassword.isNotBlank() && confirmNewPassword.isNotBlank() && !passwordError && !confirmPasswordError,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107))
            ) {
                Text(
                    "Continuar",
                    color = PolibeeDarkGreen,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
    }
}