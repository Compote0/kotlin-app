package com.example.courskotlin.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.courskotlin.model.GameBean
import com.example.courskotlin.ui.theme.linkColor
import com.example.courskotlin.utils.PlatformIconProvider
import com.example.courskotlin.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    pictureBean: GameBean,
    navController: NavController?,
    mainViewModel: MainViewModel?
) {
    val uriHandler = LocalUriHandler.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Game Details", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController?.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            item {
                Image(
                    painter = rememberImagePainter(data = pictureBean.url),
                    contentDescription = "Game Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                // game details
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 80.dp)
                ) {
                    Text(
                        text = pictureBean.title,
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                        fontSize = 24.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        pictureBean.platforms?.forEach { platform ->
                            Icon(
                                painter = painterResource(id = PlatformIconProvider.getPlatformIcon(platform)),
                                contentDescription = platform,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Divider(thickness = 1.dp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Metacritic: ${pictureBean.metacritic}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Release Date: ${pictureBean.releaseDate}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = pictureBean.website ?: "No website available",
                        color = MaterialTheme.linkColor,
                        style = MaterialTheme.typography.bodyLarge,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable {
                            pictureBean.website?.let { uriHandler.openUri(it) }
                        }
                    )



                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Rating: ${pictureBean.rating}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Genres: ${pictureBean.genres?.joinToString(", ") { it.name ?: "Unknown" } ?: "N/A"}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Divider(thickness = 1.dp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.LightGray.copy(alpha = 0.2f))
                    ) {
                        Text(
                            text = pictureBean.description_raw ?: "No description available",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailScreenPreview() {
    val mockGame = GameBean(
        id = 1,
        url = "https://via.placeholder.com/300",
        title = "Mock Game",
        description_raw = "This is a sample description of the game. It is a beautifully designed game with rich visuals and engaging gameplay.",
        website = "https://example.com",
        rating = 4.5f,
        metacritic = 85,
        platforms = listOf("PC", "PlayStation 4"),
        releaseDate = "2024-10-17",
        genres = listOf(
            com.example.courskotlin.model.GenreWrapper(1, "Action"),
            com.example.courskotlin.model.GenreWrapper(2, "Adventure")
        )
    )
    DetailScreen(pictureBean = mockGame, navController = null, mainViewModel = null)
}
