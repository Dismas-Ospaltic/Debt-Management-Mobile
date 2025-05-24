package com.st11.mydebts

import android.os.Build
import android.os.Bundle
import androidx.compose.ui.res.painterResource
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.st11.mydebts.navigation.AppNavHost
import com.st11.mydebts.navigation.Screen
import com.st11.mydebts.screens.components.HomeFAB
import java.util.Locale

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Ensure full-screen layout
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val navController = rememberAnimatedNavController()

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            // Define screens where the bottom bar should be hidden
            val hideBottomBarScreens = listOf(Screen.Splash.route,Screen.Detail.route, Screen.HistDetail.route, Screen.DebtHistDetail.route, Screen.DebtDetail.route, Screen.SelectCurrency.route)

            Scaffold(
                bottomBar = {
//                    BottomNavigationBar(navController)
                    if (currentRoute !in hideBottomBarScreens) {
                        BottomNavigationBar(navController)
                    }
                },
                floatingActionButton = {
                    if (currentRoute == Screen.Home.route) { // Show FAB only on Home
                        HomeFAB()
                    }
                }

            ) { paddingValues ->
                AppNavHost(navController, Modifier.padding(paddingValues))
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    val screens = listOf(Screen.Home, Screen.Account, Screen.Setting)

    val backgroundColor = colorResource(id = R.color.bottom_bar_background)
    val selectedColor = colorResource(id = R.color.tab_selected)
    val unselectedColor = colorResource(id = R.color.tab_unselected)
    val tabIndicatorColor = colorResource(id = R.color.tab_indicator)

    Column{
        // Top Divider
        HorizontalDivider(
            thickness = 1.dp, // Adjust thickness as needed
            color = colorResource(id = R.color.border_color)
        )
    NavigationBar(
        containerColor = backgroundColor
    ) {
        screens.forEach { screen ->
            val isSelected = currentDestination == screen.route

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (currentDestination != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(Screen.Home.route) { inclusive = false }
                            launchSingleTop = true
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(
                            id = when (screen) {
                                is Screen.Home -> R.drawable.ic_home  // Home icon from res
                                is Screen.Account -> R.drawable.ic_analytics  // Account icon from res
                                is Screen.Setting -> R.drawable.ic_setting
                                else -> R.drawable.ic_home  // Fallback icon
                            }
                        ),
                        contentDescription = screen.route,
                        tint = if (isSelected) selectedColor else unselectedColor // Apply custom colors
                    )
                },
                label = {
                    Text(
                        text = screen.route.replaceFirstChar { it.titlecase(Locale.ROOT) },
                        color = if (isSelected) selectedColor else unselectedColor // Apply custom colors
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = selectedColor,
                    unselectedIconColor = unselectedColor,
                    selectedTextColor = selectedColor,
                    unselectedTextColor = unselectedColor,
                    indicatorColor = tabIndicatorColor // Change the background color of selected tab
                )
            )
        }
    }
}
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    BottomNavigationBar(navController = rememberNavController())
}

