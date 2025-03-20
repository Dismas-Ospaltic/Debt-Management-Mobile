package com.ospaltic.mydebts.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ospaltic.mydebts.R
import com.ospaltic.mydebts.model.HistoryItem
import com.ospaltic.mydebts.model.PaymentItem
import com.ospaltic.mydebts.screens.components.PayHistoryCard
import com.ospaltic.mydebts.screens.components.PaymentBox
import com.ospaltic.mydebts.utils.DynamicStatusBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PayHistoryScreen(navController: NavController, itemId: String?) {

    val backgroundColor = colorResource(id = R.color.dark)

    val interactionSource = remember { MutableInteractionSource() }
    var isHovered by remember { mutableStateOf(false) }


    // ✅ Use state so that data can change dynamically
    var historyItems by remember { mutableStateOf(listOf<HistoryItem>()) }

    // Simulate fetching data (you can replace this with an API call)
    LaunchedEffect(Unit) {
        historyItems = listOf(
            HistoryItem("2025-03-10", "$120.00", "$120.00"),
            HistoryItem("2025-03-05", "$80.00", "$20.00"),
            HistoryItem("2025-02-28", "$150.00", "$50.00"),
            HistoryItem("2025-03-10", "$120.00", "$120.00"),
            HistoryItem("2025-03-05", "$80.00", "$20.00"),
            HistoryItem("2025-02-28", "$150.00", "$50.00"),
            HistoryItem("2025-03-10", "$120.00", "$120.00"),
            HistoryItem("2025-03-05", "$80.00", "$20.00"),
            HistoryItem("2025-02-28", "$150.00", "$50.00"),
            HistoryItem("2025-03-10", "$120.00", "$120.00"),
            HistoryItem("2025-03-05", "$80.00", "$20.00"),
            HistoryItem("2025-02-28", "$150.00", "$50.00")
        )
    }


    DynamicStatusBar(backgroundColor)  // ✅ Apply dynamic status bar settings
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Payment History Details", color = Color.White) },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(if (isHovered) Color.Gray.copy(alpha = 0.3f) else Color.Transparent)
                            .hoverable(interactionSource = interactionSource, enabled = true)
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onPress = {
                                        isHovered = true
                                        tryAwaitRelease()
                                        isHovered = false
                                    }
                                )
                            }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = colorResource(id = R.color.dark),
                    navigationIconContentColor = Color.White,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()) // ✅ Enable scrolling
        ) {



        if (historyItems.isEmpty()) {
            // Centered text when no data is found
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No data found",
                    color = Color.Gray,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        } else {
            // ✅ Corrected Loop
            historyItems.forEach { item ->
                PayHistoryCard(item) // ✅ Pass item, not HistoryItem class
            }


        }
        }

    }

    }



@Preview(showBackground = true)
@Composable
fun PayHistoryScreenPreview() {
    PayHistoryScreen(navController = rememberNavController(), itemId = "2")
}




