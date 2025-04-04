package com.ospaltic.mydebts.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ospaltic.mydebts.R
import com.ospaltic.mydebts.navigation.Screen
import com.ospaltic.mydebts.utils.DynamicStatusBar


@Composable
fun AccountScreen(navController: NavController) {

    val backgroundColor = colorResource(id = R.color.white)

    DynamicStatusBar(backgroundColor)  // ✅ Apply dynamic status bar settings

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
            ) // Ensure content does not overlap status bar
    ) {
        // 🔹 Title Box (Header)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
                .background(
                    color = colorResource(id = R.color.dark),
                    shape = RoundedCornerShape(0.dp)
                )
                .padding(20.dp)
        ) {
            Text(
                text = "Debt Overview",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Inline Grid Component
        val items = listOf(
            Triple(R.drawable.user, "People / Clients", 120),
            Triple(R.drawable.debtdash, "Total Debt", 1000),
            Triple(R.drawable.burden, "Unpaid No.", 25),
            Triple(R.drawable.debthand, "Paid No.", 340),
            Triple(R.drawable.debtdue, "Past Due No.", 340)
        )

        val configuration = LocalConfiguration.current
        val columns = when {
            configuration.screenWidthDp < 400 -> 2
            configuration.screenWidthDp < 600 -> 3
            else -> 4 // More columns for larger screens
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 8.dp) // ✅ More balanced padding
        ) {
            items(items) { (image, label, count) ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .aspectRatio(1f), // Ensures square shape
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp),
                    shape = RoundedCornerShape(12.dp), // Slightly more rounded
                    colors = CardDefaults.cardColors(containerColor = Color.White) // ✅ White background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = image),
                            contentDescription = label,
                            modifier = Modifier
                                .size(64.dp)
                                .padding(bottom = 8.dp)
                        )
                        Text(
                            text = label,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = count.toString(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}
