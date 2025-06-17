// src/main/java/com/example/polibee_v2/MyDataActivity.kt
package com.example.polibee_v2.profile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.polibee_v2.access.PolibeeDarkGreen
import com.example.polibee_v2.ui.theme.Polibee_v2Theme

class MyCardsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Polibee_v2Theme {
                Scaffold { paddingValues ->
                    Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                        Text("Página Meus Cartões", color = PolibeeDarkGreen)
                    }
                }
            }
        }
    }
}