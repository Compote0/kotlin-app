package com.example.courskotlin.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courskotlin.http.GameAPI
import com.example.courskotlin.model.GameBean
import com.example.courskotlin.model.GenreWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class MainViewModel(isPreview: Boolean = false) : ViewModel() {
    var dataList = mutableStateOf<List<GameBean>>(emptyList())
        private set
    var homeGamesList = mutableStateOf<List<GameBean>>(emptyList())
        private set
    var runInProgress = mutableStateOf(false)
        private set
    var errorMessage = mutableStateOf("")
        private set
    var currentPage = 1
    var isEndReached = false

    init {
        if (isPreview) {
            loadFakeData()
        }
    }

    fun loadFakeData() {
        homeGamesList.value = listOf(
            GameBean(
                1,
                "https://via.placeholder.com/150",
                "Fake Game 1",
                "This is a fake description",
                "https://fakegame1.com",
                4.5f,
                90,
                listOf("PC, PlayStation 4"),
                "2024-01-01",
                genres = listOf(
                    GenreWrapper(1, "Action"),
                    GenreWrapper(2, "Adventure")
                )
            ),
            GameBean(
                2,
                "https://via.placeholder.com/150",
                "Fake Game 2",
                "This is another fake description",
                "https://fakegame2.com",
                3.8f,
                80,
                listOf("Xbox, PC"),
                "2024-01-01",
                genres = listOf(
                    GenreWrapper(1, "Action"),
                    GenreWrapper(2, "Adventure")
                )
            )
        )
    }

    fun loadRandomGamesForHome() {
        if (runInProgress.value || isEndReached) return

        println("MainViewModel: loading games for the home screen...")
        runInProgress.value = true
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val games = GameAPI.getRandomGames(10)
                if (games.isEmpty()) {
                    isEndReached = true
                    println("MainViewModel: no games found, end of the list.")
                } else {
                    homeGamesList.value += games.map { game ->
                        GameBean(
                            game.id,
                            game.background_image ?: "https://via.placeholder.com/150",
                            game.name ?: "No name available",
                            game.description_raw ?: "No description available",
                            game.website ?: "No website available",
                            game.rating ?: 0f,
                            game.metacritic ?: 0,
                            game.platforms?.map { it.platform?.name ?: "Unknown platform" },
                            game.released ?: "Unknown release date",
                            game.genres?.map { GenreWrapper(it.id, it.name) }
                        )
                    }
                    println("MainViewModel: ${homeGamesList.value.size} games loaded for the home screen.")
                }
            } catch (e: Exception) {
                errorMessage.value = e.message ?: "An error has occurred"
                println("MainViewModel: error when loading games: ${e.message}")
            }
            runInProgress.value = false
        }
    }


    fun loadMoreGamesForHome() {
        if (runInProgress.value || isEndReached) return

        runInProgress.value = true
        viewModelScope.launch(Dispatchers.Default) {
            try {

                currentPage++
                val games = GameAPI.getRandomGames(10)
                if (games.isEmpty()) {
                    isEndReached = true
                    println("MainViewModel: no games found, end of the list.")
                } else {
                    homeGamesList.value += games.map { game ->
                        GameBean(
                            game.id,
                            game.background_image ?: "https://via.placeholder.com/150",
                            game.name ?: "No name available",
                            game.description_raw ?: "No description available",
                            game.website ?: "No website available",
                            game.rating ?: 0f,
                            game.metacritic ?: 0,
                            game.platforms?.map { it.platform?.name ?: "Unknown platform" },
                            game.released ?: "Unknown release date",
                            game.genres?.map { GenreWrapper(it.id, it.name) }
                        )
                    }
                    println("MainViewModel: ${homeGamesList.value.size} games loaded.")
                }
            } catch (e: Exception) {
                errorMessage.value = e.message ?: "An error has occurred"
                println("MainViewModel: error when loading games : ${e.message}")
            }
            runInProgress.value = false
        }
    }

    fun loadSortedGames(sortBy: String) {
        println("Loading games sorted by: $sortBy")
        runInProgress.value = true
        homeGamesList.value = emptyList()
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val games = GameAPI.getSortedGames(sortBy, 10)
                if (games.isEmpty()) {
                    println("No games found for sortBy: $sortBy")
                } else {
                    println("Games loaded: ${games.size}")

                }
                homeGamesList.value = games.map { game ->
                    GameBean(
                        game.id,
                        game.background_image ?: "https://via.placeholder.com/150",
                        game.name ?: "No name available",
                        game.description_raw ?: "No description available",
                        game.website ?: "No website available",
                        game.rating ?: 0f,
                        game.metacritic ?: 0,
                        game.platforms?.map { it.platform?.name ?: "Unknown platform" },
                        game.released ?: "Unknown release date",
                        game.genres?.map { GenreWrapper(it.id, it.name) }
                    )
                }
            } catch (e: Exception) {
                println("Error in loadSortedGames: ${e.message}")
                errorMessage.value = e.message ?: "An error has occurred"
            }
            runInProgress.value = false
        }
    }

    fun loadTopGames(category: String) {
        runInProgress.value = true
        errorMessage.value = ""
        dataList.value = emptyList()

        viewModelScope.launch(Dispatchers.Default) {
            try {
                val games = GameAPI.getTopGames(category, 10)
                dataList.value = games.map { game ->
                    GameBean(
                        game.id,
                        game.background_image ?: "https://via.placeholder.com/150",
                        game.name ?: "No name available",
                        game.description_raw ?: "No description available",
                        game.website ?: "No website available",
                        game.rating ?: 0f,
                        game.metacritic ?: 0,
                        game.platforms?.map { it.platform?.name ?: "Unknown platform" },
                        game.released ?: "Unknown release date",
                        game.genres?.map { GenreWrapper(it.id, it.name) }
                    )
                }
            } catch (e: Exception) {
                errorMessage.value = e.message ?: "An error has occurred"
            }
            runInProgress.value = false
        }
    }

    fun loadGameDetails(gameId: Int, onGameDetailsLoaded: (GameBean) -> Unit) {
        runInProgress.value = true
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val game = GameAPI.getGameDetails(gameId)
                game?.let {
                    val gameBean = GameBean(
                        it.id,
                        it.background_image ?: "https://via.placeholder.com/150",
                        it.name ?: "No name available",
                        it.description_raw ?: it.description ?: "No description available",
                        it.website ?: "No website available",
                        it.rating ?: 0f,
                        it.metacritic ?: 0,
                        it.platforms?.map { it.platform?.name ?: "Unknown platform" },
                        it.released ?: "Unknown release date",
                        it.genres?.map { GenreWrapper(it.id, it.name) }
                    )
                    onGameDetailsLoaded(gameBean)
                }
            } catch (e: Exception) {
                errorMessage.value = e.message ?: "An error has occurred"
            }
            runInProgress.value = false
        }
    }


    fun searchGames(query: String) {
        if (query.isEmpty()) {
            dataList.value = emptyList()
        } else {
            runInProgress.value = true
            dataList.value = emptyList()
            viewModelScope.launch(Dispatchers.Default) {
                try {
                    val games = GameAPI.searchGamesByName(query)
                    dataList.value = games.map { game ->
                        GameBean(
                            game.id,
                            game.background_image ?: "https://via.placeholder.com/150",
                            game.name ?: "No name available",
                            game.description_raw ?: "No description available",
                            game.website ?: "No website available",
                            game.rating ?: 0f,
                            game.metacritic ?: 0,
                            game.platforms?.map { it.platform?.name ?: "Unknown platform" },
                            game.released ?: "Unknown release date",
                            game.genres?.map { GenreWrapper(it.id, it.name) }
                        )
                    }
                } catch (e: Exception) {
                    errorMessage.value = e.message ?: "An error has occurred"
                }
                runInProgress.value = false
            }
        }
    }

    fun refreshHomeGames(onComplete: () -> Unit) {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                homeGamesList.value = emptyList()
                val games = GameAPI.getRandomGames(10)
                homeGamesList.value = games.map { game ->
                    GameBean(
                        game.id,
                        game.background_image ?: "https://via.placeholder.com/150",
                        game.name ?: "No name available",
                        game.description_raw ?: "No description available",
                        game.website ?: "No website available",
                        game.rating ?: 0f,
                        game.metacritic ?: 0,
                        game.platforms?.map { it.platform?.name ?: "Unknown platform" },
                        game.released ?: "Unknown release date",
                        game.genres?.map { GenreWrapper(it.id, it.name) }
                    )
                }
            } catch (e: Exception) {
                errorMessage.value = e.message ?: "An error occurred"
            } finally {
                onComplete()
            }
        }
    }

}