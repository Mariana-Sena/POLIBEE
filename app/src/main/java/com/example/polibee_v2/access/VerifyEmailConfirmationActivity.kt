package com.example.polibee_v2.access

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polibee_v2.GetStartedScaffold
import com.example.polibee_v2.R
import com.example.polibee_v2.ui.theme.Polibee_v2Theme

class VerifyEmailConfirmationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Polibee_v2Theme {
                VerifyEmailConfirmationScreen(
                    onBackClicked = { finish() },
                    onContinueClick = {
                        // Navegar para a tela de inserir o código
                        startActivity(Intent(this, EnterCodeActivity::class.java))
                        // Não finalizar esta activity aqui para permitir retorno
                    }
                )
            }
        }
    }
}

@Composable
fun VerifyEmailConfirmationScreen(
    onBackClicked: () -> Unit,
    onContinueClick: () -> Unit
) {
    val context = LocalContext.current
    val montserratFamily = FontFamily(
        Font(R.font.montserrat_regular),
        Font(R.font.montserrat_bold, FontWeight.Bold)
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
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.check_icon), // Ícone de sucesso (você precisará adicionar este drawable)
                contentDescription = "Sucesso",
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Verifique seu e-mail!",
                fontFamily = montserratFamily,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = PolibeeDarkGreen,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Um código de verificação foi enviado para o seu e-mail.",
                fontFamily = montserratFamily,
                fontSize = 16.sp,
                color = PolibeeDarkGreen,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(0.8f)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = onContinueClick,
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
        }
    }
}