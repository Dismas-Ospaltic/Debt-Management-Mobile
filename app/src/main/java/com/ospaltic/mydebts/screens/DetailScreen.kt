package com.ospaltic.mydebts.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import com.ospaltic.mydebts.R  // Importing resources
import com.ospaltic.mydebts.model.PaymentItem
import com.ospaltic.mydebts.screens.components.PayAllPopupScreen
import com.ospaltic.mydebts.screens.components.PaymentBox
import com.ospaltic.mydebts.screens.components.PaymentPopupScreen
import com.ospaltic.mydebts.utils.DynamicStatusBar




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, itemId: String?) {

    val backgroundColor = colorResource(id = R.color.dark)

    DynamicStatusBar(backgroundColor)  // ✅ Apply dynamic status bar settings

    val tabs = listOf("All", "Pending", "Complete", "Sold", "Partial", "Minimum")
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val coroutineScope = rememberCoroutineScope()
    val interactionSource = remember { MutableInteractionSource() }
    var isHovered by remember { mutableStateOf(false) }

    val buttonColor = colorResource(id = R.color.teal_700) // Button color
    val textColor = colorResource(id = R.color.white) // Text color

    var showDialog by remember { mutableStateOf(false) } // State to control popup visibility



    val paymentItems = listOf(
        PaymentItem("2025-03-10", "2025-04-10", "120.00", "120.00", "Paid"),
        PaymentItem("2025-03-05", "2025-04-05", "80.00", "20.00", "Partial"),
        PaymentItem("2025-02-28", "2025-03-28", "150.00", "50.00", "Minimum"),
        PaymentItem("2025-03-10", "2025-04-10", "120.00", "120.00", "Paid"),
        PaymentItem("2025-03-05", "2025-04-05", "80.00", "20.00", "Partial"),
        PaymentItem("2025-02-28", "2025-03-28", "150.00", "50.00", "Minimum"),
        PaymentItem("2025-03-10", "2025-04-10", "120.00", "120.00", "Paid"),
        PaymentItem("2025-03-05", "2025-04-05", "80.00", "20.00", "Partial"),
        PaymentItem("2025-02-28", "2025-03-28", "150.00", "50.00", "Minimum"),
        PaymentItem("2025-03-10", "2025-04-10", "120.00", "120.00", "Paid"),
        PaymentItem("2025-03-05", "2025-04-05", "80.00", "20.00", "Partial"),
        PaymentItem("2025-02-28", "2025-03-28", "150.00", "50.00", "Minimum")
    )

    val buttons = listOf(
        ButtonItem("Pay All", R.drawable.ic_money) { showDialog = true  },
        ButtonItem("Show History", R.drawable.ic_clipboard) { },
    )
    val columns = if (LocalConfiguration.current.screenWidthDp < 600) 2 else 3 // Responsive grid

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Details - Item $itemId", color = Color.White) },
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

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.record02),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )

                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Black.copy(alpha = 0.6f), Color.Transparent),
                                startY = 0f
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .align(Alignment.BottomStart)
                ) {
                    Text(
                        "Dane Doe",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        "jane@gmail.com",
                        fontSize = 16.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Text(
                        "+254742354784",
                        fontSize = 16.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Text(
                        "Jshoes Ltd",
                        fontSize = 16.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Text(
                        "Nairobi",
                        fontSize = 16.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp)
                    .background(colorResource(id = R.color.dark), shape = RoundedCornerShape(4.dp)) // Use your background color
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp) // Spacing between rows
                ) {
                    buttons.chunked(columns).forEach { rowButtons ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp) // Spacing between buttons
                        ) {
                            rowButtons.forEach { button ->
                                Box(
                                    modifier = Modifier
                                        .weight(1f) // Distribute buttons evenly
                                        .padding(8.dp)
                                ) {
                                    CustomButton(button, buttonColor, textColor)// Pass your button data
                                }
                            }

                            // Fill remaining space if the last row has fewer buttons
                            repeat(columns - rowButtons.size) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(700.dp) // Fixed height for the entire view
                    .padding(16.dp)
            ) {
                // Tabs should remain fixed at the top
                ScrollableTabRow(
                    selectedTabIndex = pagerState.currentPage,
                    edgePadding = 12.dp,
                    modifier = Modifier
                        .padding(vertical = 6.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    contentColor = Color.White,
                    divider = {},
                    indicator = {}
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(
                                    if (pagerState.currentPage == index)
                                        colorResource(id = R.color.tabSelected)
                                    else
                                        colorResource(id = R.color.tabUnselected)
                                )
                        ) {
                            Text(
                                text = title,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                                color = if (pagerState.currentPage == index) Color.White else Color.Gray
                            )
                        }
                    }
                }

                // Scrollable content
                Box(
                    modifier = Modifier
                        .weight(1f) // Makes this section take the available space
                        .fillMaxSize()
                ) {
                    HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
                        val filteredItems = when (page) {
                            0 -> paymentItems // "All" tab shows all payments
                            1 -> paymentItems.filter { it.status == "Pending" }
                            2 -> paymentItems.filter { it.status == "Paid" }
                            3 -> paymentItems.filter { it.status == "Sold" }
                            4 -> paymentItems.filter { it.status == "Partial" }
                            5 -> paymentItems.filter { it.status == "Minimum" }
                            else -> emptyList()
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState()) // Enables scrolling
                        ) {
                        if (filteredItems.isEmpty()) {
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
                            filteredItems.forEach { item ->
                                PaymentBox(navController = navController, payment = item) // Display each payment dynamically
                            }

                        }
                    }
                    }
                }
            }

        }
        }

    // Show the Payment Popup if showDialog is true
    if (showDialog) {
        PayAllPopupScreen(onDismiss = { showDialog = false }, 200f)

    }
    }



@Composable
fun CustomButton(button: ButtonItem, buttonColor: Color, textColor: Color) {
    Button(
        onClick = button.onClick,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        shape = RoundedCornerShape(4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = button.icon),
                contentDescription = button.label,
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = button.label, color = textColor, fontWeight = FontWeight.Bold)
        }
    }
}

data class ButtonItem(val label: String, val icon: Int, val onClick: () -> Unit)



@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen(navController = rememberNavController(), itemId = "2")
}
