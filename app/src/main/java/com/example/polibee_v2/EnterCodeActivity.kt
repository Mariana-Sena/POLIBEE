package com.example.polibee_v2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polibee_v2.ui.theme.Polibee_v2Theme

class EnterCodeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Polibee_v2Theme {
                EnterCodeScreen(
                    onBackClicked = { finish() },
                    onCodeEntered = { code ->
                        // Simular validação do código
                        if (code == "123456") { // Código de teste fixo
                            Toast.makeText(this, "Código validado!", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, ResetPasswordActivity::class.java))
                            // Não finalizar esta activity aqui para permitir retorno
                        } else {
                            Toast.makeText(this, "Código inválido. Tente novamente.", Toast.LENGTH_SHORT).show()
                        }
                    },
                    onResendCodeClick = {
                        Toast.makeText(this, "Simulando reenvio de código.", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterCodeScreen(
    onBackClicked: () -> Unit,
    onCodeEntered: (String) -> Unit,
    onResendCodeClick: () -> Unit
) {
    val context = LocalContext.current
    val montserratFamily = FontFamily(
        Font(R.font.montserrat_regular),
        Font(R.font.montserrat_bold, FontWeight.Bold)
    )

    var code by remember { mutableStateOf("") }

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
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Digite o Código",
                fontFamily = montserratFamily,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = PolibeeDarkGreen,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Por favor, insira o código de 6 dígitos enviado para seu e-mail.",
                fontFamily = montserratFamily,
                fontSize = 16.sp,
                color = PolibeeDarkGreen,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(0.9f)
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Campo de entrada do código (6 dígitos separados)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Para simplificar, vou usar um único TextField e limitá-lo a 6 dígitos
                // Uma implementação mais complexa teria 6 TextFields individuais
                OutlinedTextField(
                    value = code,
                    onValueChange = { newValue ->
                        if (newValue.length <= 6 && newValue.all { it.isDigit() }) {
                            code = newValue
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(60.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = PolibeeDarkGreen,
                        unfocusedBorderColor = PolibeeDarkGreen,
                        cursorColor = PolibeeDarkGreen,
                    )
                )
            }


            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = { onCodeEntered(code) },
                enabled = code.length == 6,
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

            Text(
                text = "Não recebeu o código? Reenviar",
                fontFamily = montserratFamily,
                fontSize = 14.sp,
                color = PolibeeDarkGreen,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable { onResendCodeClick() }
            )
        }
    }
}