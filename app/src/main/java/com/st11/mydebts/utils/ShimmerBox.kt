package com.st11.mydebts.utils

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment


@Composable
fun ShimmerBox(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 12.dp // Default corner radius for rounded corners
) {
    // Define a list of colors to create the shimmer gradient effect
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f), // Light gray with partial opacity
        Color.LightGray.copy(alpha = 0.2f), // Lighter gray (more transparent)
        Color.LightGray.copy(alpha = 0.6f)  // Light gray again for looping effect
    )

    // Create an infinite transition for continuous animation
    val transition = rememberInfiniteTransition(label = "")

    // Animate a float value from 0f to 1000f endlessly
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing) // Linear motion over 1s
        ),
        label = ""
    )

    // Define a linear gradient brush that moves based on translateAnim
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnim, translateAnim),         // Start point of gradient
        end = Offset(translateAnim + 200f, translateAnim + 200f) // End point shifted to create motion
    )

    // Draw the shimmer box using the animated gradient
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius)) // Apply corner radius
            .background(brush) ,                   // Use the shimmer brush as background
        // align contents to the start

        )
}
