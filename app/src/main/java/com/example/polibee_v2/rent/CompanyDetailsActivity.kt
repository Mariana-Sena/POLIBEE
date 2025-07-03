package com.example.polibee_v2.rent


import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material.icons.outlined.ThumbDown
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polibee_v2.AppDataSource
import com.example.polibee_v2.Company
import com.example.polibee_v2.PolibeeOrange
import com.example.polibee_v2.R
import com.example.polibee_v2.access.PolibeeDarkGreen
import com.example.polibee_v2.montserratFamily
import com.example.polibee_v2.ui.theme.Polibee_v2Theme
import kotlin.math.ceil
import kotlin.math.floor


class CompanyDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val company = intent.getParcelableExtra<Company>("company")

        company?.let {
            AppDataSource.addCompanyToHistory(it)
        }

        setContent {
            Polibee_v2Theme {
                if (company != null) {
                    CompanyDetailsScreen(
                        company = company,
                        onBackClick = { finish() },
                        // ALTERADO: Ações de clique agora mostram um Toast
                        onChatClick = { _, _ ->
                            Toast.makeText(this, "Tela de Chat a ser implementada", Toast.LENGTH_SHORT).show()
                        },
                        onRentClick = {
                            Toast.makeText(this, "Tela de Aluguel a ser implementada", Toast.LENGTH_SHORT).show()
                        },
                        onBottomNavItemClick = { _ ->
                            Toast.makeText(this, "Navegação a ser implementada", Toast.LENGTH_SHORT).show()
                        }
                    )
                } else {
                    Toast.makeText(this, "Erro: Dados da empresa não encontrados.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyDetailsScreen(
    company: Company,
    onBackClick: () -> Unit,
    onChatClick: (String, Int) -> Unit,
    onRentClick: () -> Unit,
    onBottomNavItemClick: (Int) -> Unit
) {
    val context = LocalContext.current
    var isFavorite by remember { mutableStateOf(company.isFavorite) }
    var selectedBottomNavItem by remember { mutableStateOf(-1) }

    Scaffold(
        containerColor = Color.White,
        bottomBar = {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 8.dp), // Adiciona um respiro vertical
                horizontalAlignment = Alignment.CenterHorizontally // Centraliza o botão
            ) {
                Button(
                    onClick = onRentClick,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(50.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PolibeeOrange)
                ) {
                    Text(
                        "Alugar",
                        color = PolibeeDarkGreen,
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }

                 Spacer(modifier = Modifier.height(7.dp))

                val items = listOf("Home", "Histórico", "Favoritos", "Perfil")
                val icons = listOf(
                    R.drawable.home,
                    R.drawable.clock,
                    R.drawable.heart,
                    R.drawable.profile
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                ) {
                    NavigationBar(
                        containerColor = PolibeeDarkGreen,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .padding(horizontal = 0.dp)
                            .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                    ) {
                        items.forEachIndexed { index, item ->
                            NavigationBarItem(
                                selected = selectedBottomNavItem == index,
                                onClick = {
                                    selectedBottomNavItem = index
                                    onBottomNavItemClick(index)
                                },
                                icon = {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Image(
                                            painter = painterResource(id = icons[index]),
                                            contentDescription = item,
                                            modifier = Modifier.size(24.dp),
                                            colorFilter = ColorFilter.tint(Color.White)
                                        )
                                        if (selectedBottomNavItem == index) {
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Box(
                                                modifier = Modifier
                                                    .size(6.dp)
                                                    .clip(CircleShape)
                                                    .background(PolibeeOrange)
                                            )
                                        }
                                    }
                                },
                                label = null,
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = Color.Unspecified,
                                    unselectedIconColor = Color.Unspecified,
                                    indicatorColor = Color.Transparent
                                )
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                Image(
                    painter = painterResource(id = company.imageResId),
                    contentDescription = "Imagem da Empresa",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.background(Color.Black.copy(alpha = 0.4f), CircleShape)
                    ) {
                        Icon(painter = painterResource(id = R.drawable.seta_voltar), contentDescription = "Voltar", tint = Color.White)
                    }
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.4f))
                            .clickable {
                                isFavorite = !isFavorite
                                AppDataSource.toggleFavoriteStatus(company.id)
                                Toast
                                    .makeText(
                                        context,
                                        if (isFavorite) "Adicionado aos favoritos!" else "Removido dos favoritos!",
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(
                                id = if (isFavorite) R.drawable.heart_filled else R.drawable.heart
                            ),
                            contentDescription = "Favoritar",
                            modifier = Modifier.size(24.dp),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
                }
            }


            Column(modifier = Modifier.padding(16.dp)){
                Text(
                    text = company.name,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.location_pin),
                        contentDescription = "Localização",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = company.location,
                        fontFamily = montserratFamily,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Sobre a empresa",
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = company.description,
                    fontFamily = montserratFamily,
                    fontSize = 16.sp,
                    color = Color.DarkGray,
                    lineHeight = 24.sp
                )
            }


            Divider(modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp))
            ReviewsSection(company = company)
        }
    }
}

@Composable
fun ReviewsSection(company: Company) {
    var selectedFilter by remember { mutableStateOf("Todas") }

    val filteredReviews = remember(selectedFilter, company.avaliacoes) {
        when (selectedFilter) {
            "Todas" -> company.avaliacoes
            "5 estrelas" -> company.avaliacoes.filter { it.nota >= 5.0f }
            "4 estrelas" -> company.avaliacoes.filter { it.nota >= 4.0f && it.nota < 5.0f }
            "3 estrelas" -> company.avaliacoes.filter { it.nota >= 3.0f && it.nota < 4.0f }
            "2 estrelas" -> company.avaliacoes.filter { it.nota >= 2.0f && it.nota < 3.0f }
            "1 estrela" -> company.avaliacoes.filter { it.nota < 2.0f }
            else -> company.avaliacoes
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        // Cabeçalho
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Avaliações",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Estrela",
                tint = Color(0xFFFFC107),
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "${company.rating} (${company.avaliacoes.size})",
                fontWeight = FontWeight.SemiBold
            )
        }

        // Filtros
        Spacer(modifier = Modifier.height(12.dp))
        ReviewFilters(
            selectedFilter = selectedFilter,
            onFilterSelected = { selectedFilter = it }
        )

        // Lista de Avaliações
        Spacer(modifier = Modifier.height(16.dp))
        if (filteredReviews.isNotEmpty()) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                filteredReviews.forEach { avaliacao ->
                    ReviewCard(avaliacao = avaliacao)
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 48.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Nenhuma avaliação encontrada para este filtro.",
                    color = Color.Gray
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ReviewFilters(selectedFilter: String, onFilterSelected: (String) -> Unit) {
    val filters = listOf("Todas", "5 estrelas", "4 estrelas", "3 estrelas", "2 estrelas", "1 estrela")

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(filters) { filter ->
            val isSelected = filter == selectedFilter
            Button(
                onClick = { onFilterSelected(filter) },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) Color.Black else Color.Transparent,
                    contentColor = if (isSelected) Color.White else Color.Black
                ),
                border = if (!isSelected) ButtonDefaults.outlinedButtonBorder else null
            ) {
                Text(text = filter)
            }
        }
    }
}

@Composable
fun ReviewCard(avaliacao: Avaliacao) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F7F7)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Seção Superior: Foto, Nome, Nota e Data
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Image(
                    painter = painterResource(id = avaliacao.fotoAutorResId),
                    contentDescription = "Foto de ${avaliacao.nomeAutor}",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = avaliacao.nomeAutor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    RatingBar(rating = avaliacao.nota, modifier = Modifier.height(16.dp))
                }
                Text(
                    text = avaliacao.data,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            // Seção do Comentário
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = avaliacao.comentario,
                fontSize = 14.sp,
                lineHeight = 21.sp,
                color = Color.DarkGray
            )

            // Seção "Foi útil?"
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Foi útil?",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { /* Lógica para curtir */ }, modifier = Modifier.size(20.dp)) {
                    Icon(
                        imageVector = Icons.Outlined.ThumbUp,
                        contentDescription = "Útil",
                        tint = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                IconButton(onClick = { /* Lógica para não curtir */ }, modifier = Modifier.size(20.dp)) {
                    Icon(
                        imageVector = Icons.Outlined.ThumbDown,
                        contentDescription = "Não útil",
                        tint = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun RatingBar(modifier: Modifier = Modifier, rating: Float, stars: Int = 5, starsColor: Color = Color(0xFFFFC107)) {
    val filledStars = floor(rating).toInt()
    val unfilledStars = (stars - ceil(rating)).toInt()
    val halfStar = !(rating.rem(1).equals(0.0f))
    Row(modifier = modifier) {
        repeat(filledStars) {
            Icon(imageVector = Icons.Filled.Star, contentDescription = null, tint = starsColor)
        }
        if (halfStar) {
            Icon(imageVector = Icons.Filled.StarHalf, contentDescription = null, tint = starsColor)
        }
        repeat(unfilledStars) {
            Icon(imageVector = Icons.Filled.StarOutline, contentDescription = null, tint = starsColor)
        }
    }
}