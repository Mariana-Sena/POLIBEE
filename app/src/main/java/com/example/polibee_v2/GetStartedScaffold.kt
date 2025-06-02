package com.example.polibee_v2

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun GetStartedScaffold(
    showBack: Boolean = false,
    onBackClick: () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            // Essa Box servirá como a área superior do aplicativo
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp) // Espaçamento superior
                    .background(Color.White) // Cor de fundo da barra superior
                    .height(64.dp) // Altura para o conteúdo da barra
            ) {
                // 1. Logo Polibee: Centralizada horizontal e verticalmente
                Image(
                    painter = painterResource(id = R.drawable.logo_bgwhite),
                    contentDescription = "Logo Polibee",
                    modifier = Modifier
                        .align(Alignment.Center) // Centraliza a logo no meio do Box
                        .size(width = 120.dp, height = 40.dp)
                )

                // 2. Botão de Voltar: Alinhado à esquerda e centralizado verticalmente
                if (showBack) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .align(Alignment.CenterStart) // Alinha o botão ao início (esquerda) do Box
                            .padding(start = 16.dp) // Adiciona um padding para a esquerda do botão
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.seta_voltar),
                            contentDescription = "Voltar",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
        },
        content = { innerPadding ->
            // O conteúdo principal da tela, que receberá o padding para não ser coberto pela TopBar
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                // favo no canto inferior esquerdo
                Image(
                    painter = painterResource(id = R.drawable.favo_cortado),
                    contentDescription = "Imagem decorativa",
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .size(250.dp),
                    contentScale = ContentScale.Fit
                )
                content(innerPadding)
            }
        }
    )
}