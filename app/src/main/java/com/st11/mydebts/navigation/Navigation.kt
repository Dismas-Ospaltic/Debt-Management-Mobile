package com.st11.mydebts.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import androidx.compose.animation.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.st11.mydebts.screens.AccountScreen
import com.st11.mydebts.screens.DebtDetailScreen
import com.st11.mydebts.screens.DebtHistoryScreen
import com.st11.mydebts.screens.HomeScreen
import com.st11.mydebts.screens.DetailScreen
import com.st11.mydebts.screens.OnboardingScreen
import com.st11.mydebts.screens.PayHistoryScreen
import com.st11.mydebts.screens.SelectCurrencyScreen
import com.st11.mydebts.screens.SettingsScreen
import com.st11.mydebts.screens.SplashScreen
import com.st11.mydebts.viewmodel.CurrencyViewModel
import com.st11.mydebts.viewmodel.OnboardingViewModel
import org.koin.androidx.compose.getViewModel

sealed class Screen(val route: String) {
    object Home : Screen("People")
    object Account : Screen("Debt")
    object Setting : Screen("Setting")
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
    object SelectCurrency : Screen("selectCurrency")

    object Splash : Screen("splash")

    object Onboarding : Screen("onboarding")

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier) {
    val currencyViewModel: CurrencyViewModel = getViewModel()
    val isCurrencySet by currencyViewModel.isCurrencySet.collectAsState(initial = false)

    val mainViewModel: OnboardingViewModel = getViewModel()
    val isOnboardingCompleted by mainViewModel.isOnboardingCompleted.collectAsState(initial = false)

//    val startDestination = if (isCurrencySet) Screen.Home.route else Screen.SelectCurrency.route

    AnimatedNavHost(
        navController,
        startDestination = Screen.Splash.route,
        enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn() },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut() },
        popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }) + fadeIn() },
        popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) + fadeOut() }
    ) {

//        composable(Screen.Splash.route) {
//            SplashScreen(
//                onNavigate = {
//                    when {
//                        isCurrencySet -> navController.navigate(Screen.Home.route) {
//                            popUpTo(Screen.Splash.route) { inclusive = true }
//                        }
//
//                        else -> navController.navigate(Screen.SelectCurrency.route) {
//                            popUpTo(Screen.Splash.route) { inclusive = true }
//                        }
//                    }
//
//                })
//        }


//        composable(Screen.Splash.route) {
//            SplashScreen(
//                onNavigate = {
//                    when {
//                        isOnboardingCompleted -> navController.navigate(Screen.SelectCurrency.route) {
//                            popUpTo(Screen.Splash.route) { inclusive = true }
//                        }
//
//                        else -> navController.navigate(Screen.Onboarding.route) {
//                            popUpTo(Screen.Splash.route) { inclusive = true }
//                        }
//                    }
//
//                })
//        }

        composable(Screen.Splash.route) {
        SplashScreen(
            onNavigate = {
                when {
                    !isOnboardingCompleted -> navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                    !isCurrencySet -> navController.navigate(Screen.SelectCurrency.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                    else -> navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            }
        )
            }




        composable(Screen.Home.route) {
            LaunchedEffect(Unit) {
                if (!isCurrencySet) {
                    navController.navigate(Screen.SelectCurrency.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            }

            if (isCurrencySet) {
                HomeScreen(navController)
            }
        }




        composable(Screen.Setting.route) { SettingsScreen(navController) }
        composable(Screen.Account.route) { AccountScreen(navController) }
//        composable(Screen.SelectCurrency.route) { SelectCurrencyScreen(navController) }
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

//        composable(Screen.SelectCurrency.route) {
//            LaunchedEffect(isCurrencySet) {
//                if (isCurrencySet) {
//                    navController.navigate(Screen.Home.route) {
//                        popUpTo(Screen.SelectCurrency.route) { inclusive = true }
//                    }
//                }
//            }
//            // Always show CreateIdentityScreen unless already created
//            SelectCurrencyScreen(navController)
//        }


        composable(Screen.SelectCurrency.route) {
            LaunchedEffect(Unit) {
                if (isCurrencySet) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.SelectCurrency.route) { inclusive = true }
                    }
                }
            }
            if (!isCurrencySet) {
                SelectCurrencyScreen(navController)
            }
        }



        composable(Screen.Onboarding.route) {  OnboardingScreen( onCompleteOnboarding = {
            mainViewModel.completeOnboarding()
            navController.navigate(Screen.SelectCurrency.route) {
                popUpTo(Screen.Onboarding.route) { inclusive = true }

            }
        }
        )
        }


    }
}


