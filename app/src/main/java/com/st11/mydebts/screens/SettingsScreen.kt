package com.st11.mydebts.screens



import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.st11.mydebts.R
import com.st11.mydebts.utils.DynamicStatusBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {

    val backgroundColor = colorResource(id = R.color.dark)
    DynamicStatusBar(backgroundColor)
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", color = Color.White,fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
    Column(
        Modifier
            .fillMaxSize()
            .padding(paddingValues)//top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
            .background(color = colorResource(id = R.color.light_bg_color))
    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(2.dp)
//                .background(
//                    color = colorResource(id = R.color.dark),
//                    shape = RoundedCornerShape(0.dp)
//                )
//                .padding(20.dp)
//        ) {
//            Text(
//                text = "Settings",
//                color = Color.White,
//                fontSize = 16.sp,
//                fontWeight = FontWeight.Bold
//            )
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))



        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Privacy Policy",
                modifier = Modifier
                    .clickable {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://st11-homy.github.io/MyDebts/privacy.html")
                        )
                        context.startActivity(intent)
                    }
                    .background(
                        color = colorResource(id = R.color.white),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = colorResource(id = R.color.dark),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                color = colorResource(id = R.color.dark),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }

    }
}
}




@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(navController = rememberNavController())
}
