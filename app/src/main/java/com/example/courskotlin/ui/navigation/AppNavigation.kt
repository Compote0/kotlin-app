import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.courskotlin.ui.navigation.Routes
import com.example.courskotlin.ui.screens.DetailScreen
import com.example.courskotlin.ui.screens.HomeScreen
import com.example.courskotlin.viewmodel.MainViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navHostController = rememberNavController()
    val mainViewModel: MainViewModel = viewModel()

    NavHost(
        navController = navHostController,
        startDestination = Routes.HomeScreen.route,
        modifier = modifier
    ) {
        composable(Routes.HomeScreen.route) {
            HomeScreen(mainViewModel = mainViewModel, navController = navHostController)
        }

        composable(
            route = Routes.DetailScreen.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType }),
            enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(100)) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(100)) }
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            val game = mainViewModel.homeGamesList.value.find { it.id == id }
            game?.let {
                DetailScreen(
                    pictureBean = it,
                    navController = navHostController,
                    mainViewModel = mainViewModel
                )
            }
        }
    }
}
