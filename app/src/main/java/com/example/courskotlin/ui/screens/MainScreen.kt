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
import com.example.courskotlin.ui.theme._2024_10_cdanTheme

@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun MainScreenPreview() {
    val mockViewModel = MainViewModel(isPreview = true)

    _2024_10_cdanTheme {
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
                val homeInteractionSource = remember { MutableInteractionSource() }
                val searchInteractionSource = remember { MutableInteractionSource() }

                NavigationBarItem(
                    selected = currentDestination.value?.destination?.route == "home",
                    onClick = {
                        if (currentDestination.value?.destination?.route != "home") {
                            navController.navigate("home") {
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
                                if (currentDestination.value?.destination?.route == "home")
                                    R.drawable.ic_manette_full
                                else
                                    R.drawable.ic_manette_light
                            ),
                            contentDescription = "Home",
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
                    interactionSource = homeInteractionSource,
                    alwaysShowLabel = false,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )

                NavigationBarItem(
                    selected = currentDestination.value?.destination?.route == "search",
                    onClick = {
                        if (currentDestination.value?.destination?.route != "search") {
                            navController.navigate("search") {
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
                                if (currentDestination.value?.destination?.route == "search")
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
                    interactionSource = searchInteractionSource,
                    alwaysShowLabel = false,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }
        }
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = "home") {
            composable("home") { HomeScreen(mainViewModel = mainViewModel) }
            composable("search") { SearchScreen(mainViewModel = mainViewModel) }
        }
    }
}