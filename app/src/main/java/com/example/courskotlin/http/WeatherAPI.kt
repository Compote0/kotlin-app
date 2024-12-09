package com.example.courskotlin.http

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

object WeatherAPI {
    val client = OkHttpClient()

    fun sendGet(url: String): String {
        val request = Request.Builder().url(url).build()
        return client.newCall(request).execute().use { response ->
            require(response.isSuccessful) { "Erreur : ${response.code}" }
            response.body?.string() ?: "got no data"
        }
    }

    fun loadWeathers(cityName: String): List<CityWeather> {
        val url = "https://api.openweathermap.org/data/2.5/find?q=$cityName&cnt=5&appid=b80967f0a6bd10d23e44848547b26550&units=metric&lang=fr"
        val json = sendGet(url)
        val weatherResponse = Gson().fromJson(json, WeatherResponse::class.java)
        return weatherResponse.list
    }

}

data class WeatherResponse(
    var list: List<CityWeather>
)

data class CityWeather(
    var id: Int,
    var name: String,
    var main: Main,
    var wind: Wind,
    var weather: List<Weather>
)

data class Main(
    var temp: Double
)

data class Wind(
    var speed: Double
)

data class Weather(
    var description: String,
    var icon: String
)

fun main() {
    WeatherAPI.loadWeathers("Toulouse")
}
