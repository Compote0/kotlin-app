package com.example.courskotlin.model

import com.example.courskotlin.http.PlatformWrapper

data class GameBean(
    val id: Int,
    val url: String,
    val title: String,
    val description_raw: String,
    val website: String?,
    val rating: Float?,
    val metacritic: Int?,
    val platforms: List<String>?,
    val releaseDate: String?,
    val genres: List<GenreWrapper>?

)

data class GenreWrapper(
    val id: Int?,
    val name: String?
)