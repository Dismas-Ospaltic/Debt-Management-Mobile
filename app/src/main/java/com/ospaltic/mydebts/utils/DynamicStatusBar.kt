package com.ospaltic.mydebts.utils


import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.luminance
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
fun DynamicStatusBar(backgroundColor: Color) {
    val systemUiController = rememberSystemUiController()

    // Determine if status bar icons should be dark (for light backgrounds) or white (for dark backgrounds)
    val useDarkIcons = backgroundColor.luminance() > 0.5

    LaunchedEffect(backgroundColor) {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent, // Keep the status bar transparent
            darkIcons = useDarkIcons   // Set icons to white or black based on background color
        )
    }
}
