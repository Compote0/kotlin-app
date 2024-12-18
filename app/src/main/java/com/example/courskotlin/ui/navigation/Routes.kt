package com.example.courskotlin.ui.navigation

// define routes with sealed class
sealed class Routes(val route: String) {

    data object HomeScreen : Routes("homeScreen")

    data object SearchScreen : Routes("searchScreen")

    data object DetailScreen : Routes("detailScreen/{id}") {
        fun withId(id: Int) = "detailScreen/$id"
    }
}
