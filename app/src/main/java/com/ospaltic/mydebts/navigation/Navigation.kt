package com.ospaltic.mydebts.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import androidx.compose.animation.*
import androidx.compose.ui.Modifier
import com.ospaltic.mydebts.screens.AccountScreen
import com.ospaltic.mydebts.screens.DebtDetailScreen
import com.ospaltic.mydebts.screens.DebtHistoryScreen
import com.ospaltic.mydebts.screens.HomeScreen
import com.ospaltic.mydebts.screens.DetailScreen
import com.ospaltic.mydebts.screens.PayHistoryScreen

sealed class Screen(val route: String) {
    object Home : Screen("People")
    object Account : Screen("Debt")
    object Detail : Screen("detail/{itemId}") {
        fun createRoute(itemId: String) = "detail/$itemId"
    }
    object HistDetail : Screen("history/{itemId}") {
        fun createRoute(itemId: String) = "history/$itemId"
    }
    object DebtHistDetail : Screen("debt_history/{itemId}") {
        fun createRoute(itemId: String) = "debt_history/$itemId"
    }

    object DebtDetail : Screen("debt_detail/{itemId}") {
        fun createRoute(itemId: String) = "debt_detail/$itemId"
    }


}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier) {
    AnimatedNavHost(
        navController,
        startDestination = Screen.Home.route,
        enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn() },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut() },
        popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }) + fadeIn() },
        popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) + fadeOut() }
    ) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Account.route) { AccountScreen(navController) }
        composable(Screen.Detail.route) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId") ?: "Unknown"
            DetailScreen(navController, itemId)
        }
        composable(Screen.HistDetail.route) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId") ?: "Unknown"
            PayHistoryScreen(navController, itemId)
        }
        composable(Screen.DebtHistDetail.route) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId") ?: "Unknown"
            DebtHistoryScreen(navController, itemId)
        }


        composable(Screen.DebtDetail.route) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId") ?: "Unknown"
            DebtDetailScreen(navController, itemId)
        }


    }
}
