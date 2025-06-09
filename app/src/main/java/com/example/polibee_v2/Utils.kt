package com.example.polibee_v2

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polibee_v2.access.PolibeeDarkGreen
import java.util.regex.Pattern

fun isValidPassword(password: String): Boolean {
    val pattern = Pattern.compile(
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\S+\$).{8,}\$"
    )
    return pattern.matcher(password).matches()
}

// Este Composable pode ser adicionado em um arquivo de utilidades (ex: CommonComposables.kt)
// ou no final de um dos seus arquivos de Activity para ser acessível.
// Se você já tem PolibeeDarkGreen e montserratFamily acessíveis, não precisa redefini-los aqui.

@Composable
fun PolibeeTopBarWithTitleAndBack(
    title: String,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp) // Espaço do topo da tela
            .height(64.dp) // Altura da barra
            .background(Color.White) // Fundo branco
    ) {
        IconButton(
            onClick = onBackClick,
        ) {
            Image(
                painter = painterResource(id = R.drawable.seta_voltar),
                contentDescription = "Voltar",
                modifier = Modifier.size(30.dp),
                colorFilter = ColorFilter.tint(PolibeeDarkGreen)
            )
        }
        Text(
            text = title,
            fontFamily = montserratFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = PolibeeDarkGreen,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.8f) // Para não sobrepor o botão de voltar
        )
    }
}