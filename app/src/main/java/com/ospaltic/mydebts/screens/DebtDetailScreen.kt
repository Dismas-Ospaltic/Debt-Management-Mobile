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
import com.ospaltic.mydebts.model.DebtEntity
import com.ospaltic.mydebts.model.PaymentItem
import com.ospaltic.mydebts.navigation.Screen
import com.ospaltic.mydebts.screens.components.AddDebtPopUpScreen
import com.ospaltic.mydebts.screens.components.PayAllPopupScreen
import com.ospaltic.mydebts.screens.components.PaymentBox
import com.ospaltic.mydebts.screens.components.PaymentPopupScreen
import com.ospaltic.mydebts.utils.DynamicStatusBar
import com.ospaltic.mydebts.utils.formatDate
import com.ospaltic.mydebts.viewmodel.DebtViewModel
import com.ospaltic.mydebts.viewmodel.PeopleViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.math.absoluteValue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebtDetailScreen(navController: NavController, itemId: String?) {

    val backgroundColor = colorResource(id = R.color.dark)

    DynamicStatusBar(backgroundColor)  // ✅ Apply dynamic status bar settings


    val interactionSource = remember { MutableInteractionSource() }
    var isHovered by remember { mutableStateOf(false) }

    val buttonColor = colorResource(id = R.color.teal_700) // Button color
    val textColor = colorResource(id = R.color.white) // Text color



    val debtViewModel: DebtViewModel = koinViewModel()
    val debtDetail by debtViewModel.debtDetail.collectAsState()

    val currentDate = System.currentTimeMillis()
    val formattedDate = formatDate(currentDate) // Should return "DD-MM-YYYY"

    val days = debtDetail?.let { daysBetweenDates(formattedDate, it.dueDate) }



    LaunchedEffect(Unit) {
        if (itemId != null) {
            debtViewModel.getDebtById(debtId = itemId.toString())
        }  // Pass actual userId
    }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Debt Details", color = Color.White) }, // - Item $itemId
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
//                .padding(paddingValues)
//                .verticalScroll(rememberScrollState()) // ✅ Enable scrolling
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize() // ✅ Take full width and height
                    .padding(paddingValues)
                    .background(colorResource(id = R.color.dark))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.record04),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize().fillMaxSize()
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


                    days?.let { safeDays ->
                        val boxColor = if (safeDays < 0) colorResource(id = R.color.red) else colorResource(id = R.color.green)
                        val message = if (safeDays < 0) "Past due by ${-safeDays} days" else "$safeDays days remaining until due"

                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .padding(0.dp)
                                .background(boxColor, shape = RoundedCornerShape(12.dp))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = message,
                                    fontSize = 16.sp,
                                    color = colorResource(id = R.color.white)
                                )
                            }
                        }
                    }


//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth(0.8f)
//                            .padding(0.dp)
//                            .background(colorResource(id = R.color.green), shape = RoundedCornerShape(12.dp)) // Use your background color
//                    ) {
//                        Column(
//                            modifier = Modifier.fillMaxWidth()
//                                .padding(16.dp),
//                            verticalArrangement = Arrangement.spacedBy(8.dp)// Spacing between rows
//                        ) {
//                            Text(
//                                text = "$days days remaining until due",
//                                fontSize = 16.sp,
//                                color = colorResource(id = R.color.white)
//                            )
//                        }
//
//                    }
                        Text(
                            text ="Debt Id: ${debtDetail?.id}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Date: ${debtDetail?.date}",
                            fontSize = 16.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                        Text(
                            text = "Due Date: ${debtDetail?.dueDate}",
                            fontSize = 16.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                        Text(
                            text = "Status: ${debtDetail?.status}",
                            fontSize = 16.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                        Text(
                            text = "Amount: ${debtDetail?.amount}",
                            fontSize = 16.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )

                    Text(
                        text = "Amount Paid: ${debtDetail?.amountPaid}",
                        fontSize = 16.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )


                    Text(
                        text = "Amount Remaining: ${debtDetail?.amountRem}",
                        fontSize = 16.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(0.dp)
                            .background(colorResource(id = R.color.white), shape = RoundedCornerShape(12.dp)) // Use your background color
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)// Spacing between rows
                        ) {
                            Text(
                                text = "Description: ${debtDetail?.description}",
                                fontSize = 16.sp,
                                color = colorResource(id = R.color.dark)
                            )
                        }

                        }



                    }

                }
            }






        }
    }


fun daysBetweenDates(date1: String, date2: String): Long {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val firstDate = LocalDate.parse(date1, formatter)
    val secondDate = LocalDate.parse(date2, formatter)

    // Subtract secondDate from firstDate (first - second)
    return ChronoUnit.DAYS.between(secondDate, firstDate)
}



@Preview(showBackground = true)
@Composable
fun DebtDetailScreenPreview() {
    DebtDetailScreen(navController = rememberNavController(), itemId = "2")
}
