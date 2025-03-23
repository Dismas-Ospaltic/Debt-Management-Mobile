package com.ospaltic.mydebts.screens


import android.app.Activity
import androidx.compose.foundation.background
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
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.ospaltic.mydebts.BottomNavigationBar
import com.ospaltic.mydebts.viewmodel.PeopleViewModel
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)


//
//        @Composable
//        fun HomeScreen(navController: NavController,peopleViewModel: PeopleViewModel = koinViewModel()) {
//
//    val people by peopleViewModel.people.collectAsState()
//
//    val searchQuery = remember { mutableStateOf("") }
//
//    val backgroundColor = colorResource(id = R.color.white)
//
//    DynamicStatusBar(backgroundColor)  // ✅ Apply dynamic status bar settings
//
//    Column(
//        Modifier.fillMaxSize()
//            .padding(
//                top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
//            )
//            .background(color = colorResource(id = R.color.light_bg_color))
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth() // Adjust width as needed
//                .padding(2.dp) // Space around the section
//                .background(
//                    color = colorResource(id = R.color.dark), // Your custom background color
//                    shape = RoundedCornerShape(0.dp) // Rounded corners
//                )
//                .padding(20.dp) // Inner padding
//        ) {
//            Text(
//                text = "Manage People",
//                color = Color.White, // Text color
//                fontSize = 16.sp,
//                fontWeight = FontWeight.Bold
//            )
//        }
//        Spacer(modifier = Modifier.height(10.dp))
//
//
//
//        TextField(
//            value = searchQuery.value,
//            onValueChange = { searchQuery.value = it },
//            placeholder = { Text(text = "Search...") },
//            leadingIcon = {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_search), // Replace with your search icon
//                    contentDescription = "Search Icon",
//                    tint = Color.Gray // Customize icon color
//                )
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//                .clip(RoundedCornerShape(12.dp)) // Clip for rounded corners
//                .background(Color.White), // Background color
//            colors = TextFieldDefaults.colors(
//                focusedContainerColor = Color.Transparent, // Transparent background when focused
//                unfocusedContainerColor = Color.Transparent, // Transparent background when unfocused
//                disabledContainerColor = Color.Transparent, // Transparent when disabled
//                cursorColor = Color.Black, // Cursor color
//                focusedIndicatorColor = Color.Transparent, // Remove underline when focused
//                unfocusedIndicatorColor = Color.Transparent // Remove underline when unfocused
//            ),
//            singleLine = true
//        )
//
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        LazyColumn(
//            modifier = Modifier.fillMaxSize(),
//            contentPadding = PaddingValues(bottom = 150.dp) // Adds bottom margin
//        ) {
//            items(people) { person -> // Repeat 50 times
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(8.dp)
//                        .clickable { navController.navigate(Screen.Detail.createRoute(${peson.userId}.toString())) },
//                    shape = RoundedCornerShape(12.dp), // Rounded corners
//                    colors = CardDefaults.cardColors(containerColor = Color.White) // White background
//                ) {
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(16.dp),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        // Left Column: Index, Name, Contact
//                        Column(
//                            modifier = Modifier.weight(1f) // Takes all available space on left
//                        ) {
//                            Text(text = "$index")
//                            Text(text = "Name: Jane Doe")
//                            Text(text = "Contact: +254742354784")
//                        }
//
//                        // Right Icon (Arrow)
//                        Icon(
//                            painter = painterResource(id = R.drawable.ic_arror_right_gray),
//                            contentDescription = "Arrow Right",
//                            modifier = Modifier.size(24.dp),
//                            tint = Color.Gray
//                        )
//                    }
//                }
//            }
//        }
//
//
//    }
//}


//@Composable
//fun HomeScreen(navController: NavController, peopleViewModel: PeopleViewModel = koinViewModel()) {
//    val people by peopleViewModel.people.collectAsState()
//    val searchQuery = remember { mutableStateOf("") }
//    val backgroundColor = colorResource(id = R.color.white)
//
//    DynamicStatusBar(backgroundColor)
//
//    Column(
//        Modifier
//            .fillMaxSize()
//            .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
//            .background(color = colorResource(id = R.color.light_bg_color))
//    ) {
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
//                text = "Manage People",
//                color = Color.White,
//                fontSize = 16.sp,
//                fontWeight = FontWeight.Bold
//            )
//        }
//        Spacer(modifier = Modifier.height(10.dp))
//
//        // Search Field
//        TextField(
//            value = searchQuery.value,
//            onValueChange = { searchQuery.value = it },
//            placeholder = { Text(text = "Search...") },
//            leadingIcon = {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_search),
//                    contentDescription = "Search Icon",
//                    tint = Color.Gray
//                )
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//                .clip(RoundedCornerShape(12.dp))
//                .background(Color.White),
//            colors = TextFieldDefaults.colors(
//                focusedContainerColor = Color.Transparent,
//                unfocusedContainerColor = Color.Transparent,
//                disabledContainerColor = Color.Transparent,
//                cursorColor = Color.Black,
//                focusedIndicatorColor = Color.Transparent,
//                unfocusedIndicatorColor = Color.Transparent
//            ),
//            singleLine = true
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // ✅ **Filter the list based on search query**
//        val filteredPeople = people.filter {
//            it.firstName.contains(searchQuery.value, ignoreCase = true) ||
//                    it.lastName.contains(searchQuery.value, ignoreCase = true)
//        }
//
//        LazyColumn(
//            modifier = Modifier.fillMaxSize(),
//            contentPadding = PaddingValues(bottom = 150.dp)
//        ) {
//            itemsIndexed(filteredPeople) { index, person ->
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(8.dp)
//                        .clickable { navController.navigate(Screen.Detail.createRoute(person.uid)) },
//                    shape = RoundedCornerShape(12.dp),
//                    colors = CardDefaults.cardColors(containerColor = Color.White)
//                ) {
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(16.dp),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Column(modifier = Modifier.weight(1f)) {
//                            Text(text = "ID: ${index + 1}")
//                            Text(text = "Name: ${person.firstName} ${person.lastName}")
//                            Text(text = "Contact: ${person.phone}")
//                        }
//
//                        Icon(
//                            painter = painterResource(id = R.drawable.ic_arror_right_gray),
//                            contentDescription = "Arrow Right",
//                            modifier = Modifier.size(24.dp),
//                            tint = Color.Gray
//                        )
//                    }
//                }
//            }
//        }
//    }
//}

@Composable
fun HomeScreen(navController: NavController, peopleViewModel: PeopleViewModel = koinViewModel()) {
    val people by peopleViewModel.people.collectAsState()
    val searchQuery = remember { mutableStateOf("") }
    val backgroundColor = colorResource(id = R.color.white)

    DynamicStatusBar(backgroundColor)

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
                text = "Manage People",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        // Search Field
        TextField(
            value = searchQuery.value,
            onValueChange = { searchQuery.value = it },
            placeholder = { Text(text = "Search...") },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Search Icon",
                    tint = Color.Gray
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ✅ **Filter the list based on search query**
        val filteredPeople = people.filter {
            it.firstName.contains(searchQuery.value, ignoreCase = true) ||
                    it.lastName.contains(searchQuery.value, ignoreCase = true)
        }

        // ✅ Show "No Data Available" if the list is empty initially or after filtering
        if (people.isEmpty()) {
            // No data available at the initial display
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No Data Available",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            }
        } else if (filteredPeople.isEmpty()) {
            // No data available after search
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No Results Found",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            }
        } else {
            // ✅ Show the filtered list
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 150.dp)
            ) {
                itemsIndexed(filteredPeople) { index, person ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable { navController.navigate(Screen.Detail.createRoute(person.uid)) },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = "ID: ${index + 1}")
                                Text(text = "Name: ${person.firstName} ${person.lastName}")
                                Text(text = "Contact: ${person.phone}")
                            }

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
}



@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}
