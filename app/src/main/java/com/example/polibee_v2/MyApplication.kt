package com.example.polibee_v2

import android.app.Application
import com.example.polibee_v2.commercial.CommercialProfileRepository

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Inicializa o repositório com o contexto da aplicação
        CommercialProfileRepository.initialize(this)
    }
}