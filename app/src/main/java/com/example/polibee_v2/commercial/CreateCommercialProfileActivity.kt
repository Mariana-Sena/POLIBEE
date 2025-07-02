package com.example.polibee_v2.commercial

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.polibee_v2.PolibeeOrange
import com.example.polibee_v2.R
import com.example.polibee_v2.access.PolibeeDarkGreen
import com.example.polibee_v2.montserratFamily
import com.example.polibee_v2.ui.theme.Polibee_v2Theme
import java.util.UUID

class CreateCommercialProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isEditMode = intent.getBooleanExtra("IS_EDIT_MODE", false)
        setContent {
            Polibee_v2Theme {
                CreateOrEditCommercialProfileScreen(isEditMode = isEditMode)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateOrEditCommercialProfileScreen(isEditMode: Boolean) {
    val context = LocalContext.current
    val activity = (context as? ComponentActivity)

    var companyName by remember { mutableStateOf("") }
    var cpfCnpj by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var profileId by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(isEditMode) {
        if (isEditMode) {
            CommercialProfileRepository.getProfile()?.let { profile ->
                profileId = profile.id
                companyName = profile.companyName
                cpfCnpj = profile.cpfCnpj
                description = profile.description
                imageUri = profile.imageUri?.let { Uri.parse(it) }
            }
        }
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> uri?.let { imageUri = it } }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isEditMode) "Editar seu perfil" else "Crie seu perfil comercial",
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.Bold,
                        color = PolibeeDarkGreen,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { activity?.finish() }) {
                        Image(
                            painter = painterResource(id = R.drawable.seta_voltar),
                            contentDescription = "Voltar",
                            modifier = Modifier.size(50.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE0E0E0))
                    .border(1.dp, Color.Gray, CircleShape)
                    .clickable {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = imageUri ?: R.drawable.camera,
                    contentDescription = "Foto do Perfil",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.camera)
                )
            }
            Text("Adicionar Foto", color = Color.Gray, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(value = companyName, onValueChange = { companyName = it }, label = { Text("Nome da Empresa") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(value = cpfCnpj, onValueChange = { cpfCnpj = it }, label = { Text("CPF ou CNPJ") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Descreva sua empresa") }, modifier = Modifier.fillMaxWidth().height(120.dp))

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (companyName.isBlank() || cpfCnpj.isBlank() || description.isBlank()) {
                        Toast.makeText(context, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
                    } else {
                        val finalProfileId = profileId ?: UUID.randomUUID().toString()
                        val updatedProfile = CommercialProfile(
                            id = finalProfileId,
                            imageUri = imageUri?.toString(),
                            companyName = companyName,
                            cpfCnpj = cpfCnpj,
                            description = description
                        )

                        CommercialProfileRepository.saveProfile(updatedProfile)

                        if (isEditMode) {
                            Toast.makeText(context, "Perfil atualizado!", Toast.LENGTH_SHORT).show()
                            activity?.finish()
                        } else {
                            Toast.makeText(context, "Perfil salvo!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(context, WhatToDoActivity::class.java)
                            context.startActivity(intent)
                            activity?.finish()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PolibeeOrange),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = if (isEditMode) "Salvar Alterações" else "Salvar e Continuar",
                    fontFamily = montserratFamily, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = PolibeeDarkGreen
                )
            }
        }
    }
}