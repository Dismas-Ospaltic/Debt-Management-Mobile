package com.st11.mydebts.screens


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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.st11.mydebts.R
import com.st11.mydebts.navigation.Screen
import com.st11.mydebts.utils.DynamicStatusBar
import com.st11.mydebts.viewmodel.PeopleViewModel
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Clipboard
import compose.icons.fontawesomeicons.solid.FileExcel
import org.koin.androidx.compose.koinViewModel




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, peopleViewModel: PeopleViewModel = koinViewModel()) {
    val people by peopleViewModel.people.collectAsState()
    val searchQuery = remember { mutableStateOf("") }
    val backgroundColor = colorResource(id = R.color.dark)

    DynamicStatusBar(backgroundColor)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Manage Your Customers", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) {  paddingValues ->
    Column(
        Modifier
            .fillMaxSize()
//            .padding(paddingValues) //top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = 0.dp
            )
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
//                text = "Manage People",
//                color = Color.White,
//                fontSize = 16.sp,
//                fontWeight = FontWeight.Bold
//            )
//        }
//        Spacer(modifier = Modifier.height(10.dp))

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
//            Box(
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    text = "No Data Available",
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.Gray
//                )
//            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .heightIn(min = 200.dp), // Guarantees at least 200dp height
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = FontAwesomeIcons.Solid.Clipboard,
                        contentDescription = "No data",
                        tint = Color.Gray,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "No data found, Click the plus Icon to add a customer/client",
                        color = Color.Gray,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
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
//                contentPadding = PaddingValues(bottom = 150.dp)
                contentPadding = PaddingValues(
                    start = 0.dp,
                    top = 0.dp,
                    end = 0.dp,
                    bottom = 150.dp // Add enough padding for bottom bar (adjust as needed)
                )
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
}



@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}
