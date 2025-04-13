package com.ospaltic.mydebts.screens



import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ospaltic.mydebts.R
import com.ospaltic.mydebts.navigation.Screen
import com.ospaltic.mydebts.utils.DynamicStatusBar
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.ospaltic.mydebts.BottomNavigationBar
import com.ospaltic.mydebts.viewmodel.PeopleViewModel
import org.koin.androidx.compose.koinViewModel




@Composable
fun SettingsScreen(navController: NavController) {

    val backgroundColor = colorResource(id = R.color.white)
    DynamicStatusBar(backgroundColor)
    val context = LocalContext.current

    Column(
        Modifier
            .fillMaxSize()
            .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
            .background(color = colorResource(id = R.color.light_bg_color))
    ) {
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
                text = "Settings",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))



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
                            Uri.parse("https://ospaltictech.github.io/MyDebts/privacy.html")
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




@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(navController = rememberNavController())
}
