package com.example.courskotlin.http

import com.example.courskotlin.config.Config
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.random.Random

object GameAPI {
    val gson = Gson()
    private val client = OkHttpClient()

    // search games by name method
    fun searchGamesByName(query: String): List<Game> {
        val url = "https://api.rawg.io/api/games?key=${Config.API_KEY}&search=$query&page_size=5"

        // send request with sendGet
        val json = sendGet(url)

        println("response: $json")

        // parse JSON with GSON
        val gamesResponse: GamesResponse = gson.fromJson(json, GamesResponse::class.java)

        // return all results if exist
        return gamesResponse.results ?: emptyList()
    }

    fun getSortedGames(sortBy: String, numberOfGames: Int): List<Game> {
        val url = "https://api.rawg.io/api/games?key=${Config.API_KEY}&ordering=$sortBy&page_size=$numberOfGames"
        println("Fetching games with URL: $url") // validate URL

        val json = sendGet(url)
        val gamesResponse: GamesResponse = gson.fromJson(json, GamesResponse::class.java)
        println("Sorted result: ${gamesResponse.results}")

        return gamesResponse.results ?: emptyList()
    }


    // get random games method
    fun getRandomGames(numberOfGames: Int): List<Game> {
        val randomPage = Random.nextInt(1, 100)
        val url = "https://api.rawg.io/api/games?key=${Config.API_KEY}&page_size=$numberOfGames&page=$randomPage"

        val json = sendGet(url)

        val gamesResponse: GamesResponse = gson.fromJson(json, GamesResponse::class.java)

        return gamesResponse.results?.map { game ->
            getGameDetails(game.id) ?: game
        } ?: emptyList()
    }


    fun getTopGames(category: String, numberOfGames: Int): List<Game> {
        val url = when (category) {
            "best_of_the_year" -> "https://api.rawg.io/api/games?key=${Config.API_KEY}&dates=2024-01-01,2024-12-31&ordering=-added&page_size=$numberOfGames"
            "popular_in_2023" -> "https://api.rawg.io/api/games?key=${Config.API_KEY}&dates=2023-01-01,2023-12-31&ordering=-added&page_size=$numberOfGames"
            "top_250" -> "https://api.rawg.io/api/games?key=${Config.API_KEY}&metacritic=90,100&ordering=-metacritic&page_size=$numberOfGames"
            else -> "https://api.rawg.io/api/games?key=${Config.API_KEY}&page_size=$numberOfGames"
        }

        val json = sendGet(url)

        val gamesResponse: GamesResponse = gson.fromJson(json, GamesResponse::class.java)

        return gamesResponse.results ?: emptyList()
    }

    fun getGameDetails(gameId: Int): Game? {
        val url = "https://api.rawg.io/api/games/$gameId?key=${Config.API_KEY}"

        val json = sendGet(url)

        return gson.fromJson(json, Game::class.java)
    }

    fun getPlatforms(): List<Platform> {
        val url = "https://api.rawg.io/api/platforms?key=${Config.API_KEY}"

        val json = sendGet(url)
        val platformsResponse = gson.fromJson(json, PlatformsResponse::class.java)

        return platformsResponse.results ?: emptyList()
    }

    // send get request
    fun sendGet(url: String): String {
        println("URL: $url")
        val request = Request.Builder().url(url).build()

        return client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw Exception("Error : ${response.code}\n${response.body?.string()}")
            }
            return response.body?.string() ?: throw Exception("got no data")
        }
    }
}

// main function for API
fun main() {
    val games = GameAPI.getRandomGames(1)
    if (games.isNotEmpty()) {
        games.forEach { game ->
            println("Game name: ${game.name ?: "No name available"}")
            println("Game description: ${game.description_raw ?: game.description ?: "No description available"}")
            println("Website: ${game.website ?: "No website available"}")
            println("Note: ${game.rating ?: "No rating available"}")
            println("Metacritic : ${game.metacritic ?: "No Metacritic score"}")
            println("Image: ${game.background_image ?: "No image available"}")
            println("Platforms: ${game.platforms?.joinToString { it.platform?.name ?: "Unknown platform" } ?: "No platforms available"}")
            println("-------------------------------------------------")
        }
    } else {
        println("No games to display")
    }
}
data class Game(
    val id: Int,
    val slug: String?,
    val name: String?,
    val description: String?,
    val description_raw: String?,
    val metacritic: Int?,
    val released: String?,
    val rating: Float?,
    val website: String?,
    val background_image: String?,
    val platforms: List<PlatformWrapper>?,
    val genres: List<Genre>?
)


data class Genre(
    val id: Int?,
    val name: String?
)

data class PlatformWrapper(
    val platform: Platform?
)

data class Platform(
    val id: Int?,
    val name: String?
)

data class GamesResponse(
    val results: List<Game>?
)

data class PlatformsResponse(
    val results: List<Platform>?
)