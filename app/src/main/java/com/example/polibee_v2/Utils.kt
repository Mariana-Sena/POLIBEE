package com.example.polibee_v2

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polibee_v2.access.PolibeeDarkGreen
import java.util.regex.Pattern

// Definição de fontes (nível superior, acessível por todos os componentes neste arquivo)
val montserratFamily = FontFamily(
    Font(R.font.montserrat_regular),
    Font(R.font.montserrat_bold, FontWeight.Bold)
)

// Funções utilitárias
fun isValidPassword(password: String): Boolean {
    val pattern = Pattern.compile(
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\S+\$).{8,}\$"
    )
    return pattern.matcher(password).matches()
}


@Composable
fun PolibeeTopBarWithTitleAndBack(
    title: String,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
            .height(64.dp)
            .background(Color.White)
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
                .fillMaxWidth(0.8f)
        )
    }
}

/**
 * Composable para o cabeçalho "Pollinate Plus" com opção de botão de voltar.
 * AGORA NO NÍVEL SUPERIOR!
 *
 * @param showBack Define se o botão de voltar deve ser exibido.
 * @param onBackClick Função a ser executada quando o botão de voltar for clicada.
 */
@Composable
fun PollinatePlusHeader(showBack: Boolean, onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(Color.White)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (showBack) {
            Image(
                painter = painterResource(id = R.drawable.seta_voltar),
                contentDescription = "Voltar",
                modifier = Modifier
                    .align(Alignment.CenterStart) // Alinha ao início (esquerda)
                    .size(24.dp)
                    .clickable { onBackClick() }, // Torna o ícone clicável
                colorFilter = ColorFilter.tint(PolibeeDarkGreen) // Colore o ícone
            )
        }

        Text(
            text = "Pollinate Plus",
            fontFamily = montserratFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = PolibeeDarkGreen,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}