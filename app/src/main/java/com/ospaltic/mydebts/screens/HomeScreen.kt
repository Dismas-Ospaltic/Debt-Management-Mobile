package com.ospaltic.mydebts.screens


import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.ospaltic.mydebts.BottomNavigationBar


@OptIn(ExperimentalMaterial3Api::class)



        @Composable
        fun HomeScreen(navController: NavController) {

    val searchQuery = remember { mutableStateOf("") }

    val backgroundColor = colorResource(id = R.color.white)

    DynamicStatusBar(backgroundColor)  // ✅ Apply dynamic status bar settings

    Column(
        Modifier.fillMaxSize()
            .padding(
                top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
            )
            .background(color = colorResource(id = R.color.light_bg_color))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth() // Adjust width as needed
                .padding(2.dp) // Space around the section
                .background(
                    color = colorResource(id = R.color.dark), // Your custom background color
                    shape = RoundedCornerShape(0.dp) // Rounded corners
                )
                .padding(20.dp) // Inner padding
        ) {
            Text(
                text = "Manage People",
                color = Color.White, // Text color
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(10.dp))



        TextField(
            value = searchQuery.value,
            onValueChange = { searchQuery.value = it },
            placeholder = { Text(text = "Search...") },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search), // Replace with your search icon
                    contentDescription = "Search Icon",
                    tint = Color.Gray // Customize icon color
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(12.dp)) // Clip for rounded corners
                .background(Color.White), // Background color
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent, // Transparent background when focused
                unfocusedContainerColor = Color.Transparent, // Transparent background when unfocused
                disabledContainerColor = Color.Transparent, // Transparent when disabled
                cursorColor = Color.Black, // Cursor color
                focusedIndicatorColor = Color.Transparent, // Remove underline when focused
                unfocusedIndicatorColor = Color.Transparent // Remove underline when unfocused
            ),
            singleLine = true
        )


        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 150.dp) // Adds bottom margin
        ) {
            items(50) { index -> // Repeat 50 times
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { navController.navigate(Screen.Detail.createRoute(index.toString())) },
                    shape = RoundedCornerShape(12.dp), // Rounded corners
                    colors = CardDefaults.cardColors(containerColor = Color.White) // White background
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Left Column: Index, Name, Contact
                        Column(
                            modifier = Modifier.weight(1f) // Takes all available space on left
                        ) {
                            Text(text = "$index")
                            Text(text = "Name: Jane Doe")
                            Text(text = "Contact: +254742354784")
                        }

                        // Right Icon (Arrow)
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arror_right_gray),
                            contentDescription = "Arrow Right",
                            modifier = Modifier.size(24.dp),
                            tint = Color.Gray
                        )
                    }
                }
            }
        }


    }
}




@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}
