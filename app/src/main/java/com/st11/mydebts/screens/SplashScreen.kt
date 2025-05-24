package com.st11.mydebts.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.st11.mydebts.utils.DynamicStatusBar
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigate: () -> Unit) {
    // Use a simple delay to simulate a splash screen
    androidx.compose.runtime.LaunchedEffect(Unit) {
        delay(2000) // Simulate splash screen duration
        onNavigate() // Navigate after the delay
    }
    DynamicStatusBar(colorResource(id = com.st11.mydebts.R.color.white))

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
                .background(colorResource(id = com.st11.mydebts.R.color.white))

        ) {
            Image(
                painter = painterResource(id = com.st11.mydebts.R.drawable.debt),
                contentDescription = "Splash Image",
                modifier = Modifier.size(150.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Manage Your Debt", fontWeight = FontWeight.SemiBold, fontSize = 18.sp, color = colorResource(id = com.st11.mydebts.R.color.teal_700))
        }
    }
}