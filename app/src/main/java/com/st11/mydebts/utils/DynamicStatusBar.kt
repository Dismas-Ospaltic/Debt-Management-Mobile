package com.st11.mydebts.utils


import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.luminance
import com.google.accompanist.systemuicontroller.rememberSystemUiController


//@Composable
//fun DynamicStatusBar(backgroundColor: Color) {
//    val systemUiController = rememberSystemUiController()
//
//    // Determine if status bar icons should be dark (for light backgrounds) or white (for dark backgrounds)
//    val useDarkIcons = backgroundColor.luminance() > 0.5
//
//    LaunchedEffect(backgroundColor) {
//        systemUiController.setSystemBarsColor(
//            color = Color.Transparent, // Keep the status bar transparent
//            darkIcons = useDarkIcons   // Set icons to white or black based on background color
//        )
//    }
//}
@Composable
fun DynamicStatusBar(backgroundColor: Color) {
    val systemUiController = rememberSystemUiController()

    // Determine if status bar icons should be dark or light
    val useDarkIcons = backgroundColor.luminance() > 0.5

    LaunchedEffect(backgroundColor) {
        // Set transparent status bar with dynamic icon color
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons
        )

        // Always set navigation bar color to white with dark icons
        systemUiController.setNavigationBarColor(
            color = Color.White,
            darkIcons = true
        )
    }
}

