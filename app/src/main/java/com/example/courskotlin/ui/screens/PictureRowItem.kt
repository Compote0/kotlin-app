package com.example.courskotlin.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.courskotlin.model.GameBean
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.tooling.preview.Preview
import com.example.courskotlin.R
import com.example.courskotlin.model.GenreWrapper
import com.example.courskotlin.ui.theme.app_theme
import com.example.courskotlin.utils.PlatformIconProvider
import com.example.courskotlin.viewmodel.MainViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PictureRowItemPreview() {
    val mockGame = GameBean(
        id = 1,
        url = "https://via.placeholder.com/300",
        title = "Fake Game",
        description_raw = "This is a fake description",
        website = "https://fakegame.com",
        rating = 4.5f,
        metacritic = 85,
        platforms = listOf("PC", "PlayStation 4"),
        releaseDate = "2024-10-17",
        genres = listOf(
            GenreWrapper(1, "Action"),
            GenreWrapper(2, "Adventure")
        )
    )

    app_theme {
        val mockViewModel = MainViewModel(isPreview = true)
        PictureRowItem(data = mockGame, mainViewModel = mockViewModel)
    }
}

@Composable
fun PictureRowItem(
    modifier: Modifier = Modifier,
    data: GameBean,
    mainViewModel: MainViewModel,
    onClick: (Int) -> Unit = {}
) {
    val isDarkTheme = isSystemInDarkTheme()

    val viewMoreTextColor = if (isDarkTheme) {
        Color.DarkGray
    } else {
        Color.LightGray
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onClick(data.id)
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Image(
                painter = rememberImagePainter(data = data.url),
                contentDescription = "Game image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = data.title,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Metacritic: ${data.metacritic ?: "N/A"}",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    val uniquePlatforms = data.platforms?.mapNotNull { getPlatformIcon(it) }?.toSet()
                    uniquePlatforms?.forEach { iconResId ->
                        Icon(
                            painter = painterResource(id = iconResId),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp).padding(end = 4.dp),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row {
                    Text(
                        text = "Release Date: ",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    val formattedDate = data.releaseDate?.let {
                        LocalDate.parse(it).format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
                    } ?: "Unknown"
                    Text(
                        text = formattedDate,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "View more",
                    fontSize = 14.sp,
                    color = viewMoreTextColor,
                    modifier = Modifier.clickable {
                        onClick(data.id)
                    }
                )
            }
        }
    }
}

@Composable
fun getPlatformIcon(platform: String): Int {
    return PlatformIconProvider.getPlatformIcon(platform)
}
