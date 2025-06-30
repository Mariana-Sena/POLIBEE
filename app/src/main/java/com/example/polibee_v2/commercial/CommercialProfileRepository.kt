package com.example.polibee_v2.commercial

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object CommercialProfileRepository {

    private const val PREFS_NAME = "commercial_profile_prefs"
    private const val KEY_ID = "profile_id"
    private const val KEY_IMAGE_URI = "profile_image_uri"
    private const val KEY_COMPANY_NAME = "profile_company_name"
    private const val KEY_CPF_CNPJ = "profile_cpf_cnpj"
    private const val KEY_DESCRIPTION = "profile_description"

    private lateinit var sharedPreferences: SharedPreferences

    // MutableStateFlow para observar o perfil atual. Será null se não houver perfil salvo.
    private val _currentProfile = MutableStateFlow<CommercialProfile?>(null)
    val currentProfile: StateFlow<CommercialProfile?> = _currentProfile.asStateFlow()

    /**
     * Deve ser chamado uma única vez no início do ciclo de vida da aplicação (ex: em Application.onCreate()).
     * Inicializa o repositório e carrega o perfil salvo.
     */
    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        loadProfile()
    }

    /**
     * Carrega o perfil salvo nas SharedPreferences para o _currentProfile Flow.
     */
    private fun loadProfile() {
        val id = sharedPreferences.getString(KEY_ID, null)
        val imageUri = sharedPreferences.getString(KEY_IMAGE_URI, null)
        val companyName = sharedPreferences.getString(KEY_COMPANY_NAME, null)
        val cpfCnpj = sharedPreferences.getString(KEY_CPF_CNPJ, null)
        val description = sharedPreferences.getString(KEY_DESCRIPTION, null)

        if (id != null && companyName != null && cpfCnpj != null && description != null) {
            _currentProfile.value = CommercialProfile(id, imageUri, companyName, cpfCnpj, description)
        } else {
            _currentProfile.value = null
        }
    }

    /**
     * Salva (ou sobrescreve) o único perfil comercial nas SharedPreferences.
     * Atualiza também o StateFlow para que os observers sejam notificados.
     */
    fun saveProfile(profile: CommercialProfile) {
        with(sharedPreferences.edit()) {
            putString(KEY_ID, profile.id)
            putString(KEY_IMAGE_URI, profile.imageUri)
            putString(KEY_COMPANY_NAME, profile.companyName)
            putString(KEY_CPF_CNPJ, profile.cpfCnpj)
            putString(KEY_DESCRIPTION, profile.description)
            apply()
        }
        _currentProfile.value = profile // Atualiza o StateFlow
    }

    /**
     * Obtém o perfil comercial atualmente carregado (ou null se não houver).
     */
    fun getProfile(): CommercialProfile? {
        return _currentProfile.value
    }

    /**
     * Verifica se existe um perfil comercial salvo.
     */
    fun hasProfile(): Boolean {
        return _currentProfile.value != null
    }

    /**
     * Exclui o perfil comercial salvo, limpando as SharedPreferences.
     */
    fun clearProfile() {
        with(sharedPreferences.edit()) {
            clear()
            apply()
        }
        _currentProfile.value = null // Atualiza o StateFlow
    }
}