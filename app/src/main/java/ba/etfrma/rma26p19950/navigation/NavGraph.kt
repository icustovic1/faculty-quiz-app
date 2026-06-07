package ba.etfrma.rma26p19950.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ba.etfrma.rma26p19950.ui.screen.FilterScreen
import ba.etfrma.rma26p19950.ui.screen.KvizoviScreen
import ba.etfrma.rma26p19950.ui.screen.KvizDetaljiScreen
import ba.etfrma.rma26p19950.viewmodel.KvizViewModel

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val viewModel: KvizViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "filter"
    ) {
        composable("filter") {
            FilterScreen(
                viewModel = viewModel,
                onNavigate = { filterLabel ->
                    navController.navigate("kvizovi/$filterLabel")
                }
            )
        }
        composable(
            route = "kvizovi/{filterLabel}",
            arguments = listOf(
                navArgument("filterLabel") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val filterLabel = backStackEntry.arguments?.getString("filterLabel") ?: ""
            KvizoviScreen(
                viewModel = viewModel,
                filterLabel = filterLabel,
                onBack = { navController.popBackStack() },
                onKvizClick = { idKviza ->
                    navController.navigate("kvizDetalji/$idKviza")
                }
            )
        }
        composable(
            route = "kvizDetalji/{idKviza}",
            arguments = listOf(
                navArgument("idKviza") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val idKviza = backStackEntry.arguments?.getInt("idKviza") ?: 0
            KvizDetaljiScreen(
                idKviza = idKviza,
                onBack = { navController.popBackStack() }
            )
        }
    }
}