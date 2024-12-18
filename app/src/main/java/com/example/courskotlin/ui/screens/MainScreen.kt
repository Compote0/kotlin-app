package com.example.courskotlin.ui.screens
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import com.example.courskotlin.R
import com.example.courskotlin.viewmodel.MainViewModel
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.courskotlin.ui.navigation.Routes
import com.example.courskotlin.ui.theme.app_theme

@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun MainScreenPreview() {
    val mockViewModel = MainViewModel(isPreview = true)

    app_theme {
        MainScreen(mainViewModel = mockViewModel)
    }
}
@Composable
fun MainScreen(mainViewModel: MainViewModel) {
    val navController = rememberNavController()
    val currentDestination = navController.currentBackStackEntryAsState()

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.height(80.dp)
            ) {
                NavigationBarItem(
                    selected = currentDestination.value?.destination?.route == Routes.HomeScreen.route,
                    onClick = {
                        if (currentDestination.value?.destination?.route != Routes.HomeScreen.route) {
                            navController.navigate(Routes.HomeScreen.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(
                                if (currentDestination.value?.destination?.route == Routes.HomeScreen.route)
                                    R.drawable.ic_manette_full
                                else
                                    R.drawable.ic_manette_light
                            ),
                            contentDescription = "Home",
                            modifier = Modifier.size(36.dp).padding(top = 8.dp)
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSecondary,
                        indicatorColor = Color.Transparent
                    ),
                    alwaysShowLabel = false,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )

                NavigationBarItem(
                    selected = currentDestination.value?.destination?.route == Routes.SearchScreen.route,
                    onClick = {
                        if (currentDestination.value?.destination?.route != Routes.SearchScreen.route) {
                            navController.navigate(Routes.SearchScreen.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(
                                if (currentDestination.value?.destination?.route == Routes.SearchScreen.route)
                                    R.drawable.ic_rechercher_bold
                                else
                                    R.drawable.ic_rechercher_light
                            ),
                            contentDescription = "Search",
                            modifier = Modifier
                                .size(36.dp)
                                .padding(top = 8.dp)
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSecondary,
                        indicatorColor = Color.Transparent
                    ),
                    alwaysShowLabel = false,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }
        }
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = Routes.HomeScreen.route) {
            composable(Routes.HomeScreen.route) {
                HomeScreen(mainViewModel = mainViewModel, navController = navController)
            }
            composable(Routes.SearchScreen.route) {
                SearchScreen(mainViewModel = mainViewModel, navController = navController)
            }
            composable(
                route = Routes.DetailScreen.route,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: 0
                val game = mainViewModel.homeGamesList.value.find { it.id == id }
                game?.let {
                    DetailScreen(
                        pictureBean = it,
                        navController = navController,
                        mainViewModel = mainViewModel
                    )
                }
            }
        }
    }
}
