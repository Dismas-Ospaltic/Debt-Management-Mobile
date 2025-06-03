package com.st11.mydebts.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.st11.mydebts.R
import com.st11.mydebts.model.DebtEntity
import com.st11.mydebts.model.RecentDebt
import com.st11.mydebts.screens.components.PaymentBox
import com.st11.mydebts.screens.components.RecentDebtBox
import com.st11.mydebts.utils.DynamicStatusBar
import com.st11.mydebts.utils.ShimmerBox
import com.st11.mydebts.viewmodel.CurrencyViewModel
import com.st11.mydebts.viewmodel.DebtViewModel
import com.st11.mydebts.viewmodel.PeopleViewModel
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Clipboard
import compose.icons.fontawesomeicons.solid.FileExcel
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(navController: NavController) {

    val backgroundColor = colorResource(id = R.color.dark)

    DynamicStatusBar(backgroundColor)  // ✅ Apply dynamic status bar settings


    val peopleViewModel: PeopleViewModel = koinViewModel()
    val context = LocalContext.current
    val debtViewModel: DebtViewModel = koinViewModel()


    val totalPeople by peopleViewModel.totalPeople.collectAsState()
    val totalUnpaidPeople by debtViewModel.totalPaidAllPeople.collectAsState()
    val totalNoPaid by debtViewModel.totalNoPaid.collectAsState()
    val totalNoPartial by debtViewModel.totalNoPartial.collectAsState()
    val totalNoPending by debtViewModel.totalNoUnpaid.collectAsState()
    val totalNoOverDue by debtViewModel.totalNoOverDue.collectAsState()
    val isRecentLoading by debtViewModel.isRecentLoading.collectAsState()

    val recentDebtList by debtViewModel.getRecentDebtList().collectAsState(initial = emptyList())


    val currencyViewModel: CurrencyViewModel = koinViewModel()
    val currency by currencyViewModel.userData.collectAsState()

    val recentDebtsAll by remember {
        derivedStateOf {
            recentDebtList.map { recentDebt ->
                RecentDebt(
                    firstName = recentDebt.firstName,
                    dueDate = recentDebt.dueDate,
                    amount = recentDebt.amount,
                    lastName = recentDebt.lastName,
                    phone = recentDebt.phone
                )
            }
        }
    }



    LaunchedEffect(Unit) {
        peopleViewModel.fetchTotalNoPeople()
        debtViewModel.fetchTotalUnpaidPaidAllPeople()
        debtViewModel.fetchTotalNoUnpaid()
        debtViewModel.fetchTotalNoPartial()
        debtViewModel.fetchTotalNoPeople()
        debtViewModel.fetchTotalNoOverDue()
//        debtViewModel.getAllRecentDebt()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Debt Overview", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.light_bg_color)) // ✅ Apply background color
            .padding(
//                paddingValues
                top = paddingValues.calculateTopPadding(),
                        bottom = 100.dp
            ) // Ensure content does not overlap status bar
            .verticalScroll(rememberScrollState()) // Enables scrolling

    ) {
        // 🔹 Title Box (Header)
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
//                text = "Debt Overview",
//                color = Color.White,
//                fontSize = 16.sp,
//                fontWeight = FontWeight.Bold
//            )
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Inline Grid Component

        val items = listOf(
            Triple(R.drawable.user, "People / Clients", totalPeople.toString()),
            Triple(R.drawable.debtdash, "Total Debt", "${currency.userCurrency} ${totalUnpaidPeople.toString()}"),
            Triple(R.drawable.burden, "Unpaid No.", totalNoPending.toString()),
            Triple(R.drawable.debthand, "Paid No.", totalNoPaid.toString()),
            Triple(R.drawable.debthand, "Partial No.", totalNoPartial.toString()),
            Triple(R.drawable.debtdue, "Past Due No.", totalNoOverDue.toString())
        )


        val cardData = listOf(
            CardInfo("Number of People/Clients", "$totalPeople", R.drawable.user),
            CardInfo("Total Debt", "${currency.userCurrency} $totalUnpaidPeople", R.drawable.debtdash),
            CardInfo("Number of Unpaid", "$totalNoPending", R.drawable.burden),
            CardInfo("Number of Paid", "$totalNoPaid", R.drawable.debthand),
            CardInfo("Number of Partial Paid Debt", "$totalNoPartial", R.drawable.debthand),
            CardInfo("Number of Past Due Debts", "$totalNoOverDue", R.drawable.debtdue)
        )

        val configuration = LocalConfiguration.current
        val columns = when {
            configuration.screenWidthDp < 400 -> 2
            configuration.screenWidthDp < 600 -> 3
            else -> 4 // More columns for larger screens
        }

//        LazyVerticalGrid(
//            columns = GridCells.Fixed(columns),
//            modifier = Modifier
//                .fillMaxWidth()
//                .heightIn(min = 300.dp) // Give a bounded height to prevent infinite height
//                .padding(horizontal = 12.dp, vertical = 8.dp),
//            userScrollEnabled = false // ❗ disable nested scroll
//        ) {
//            items(items) { (image, label, count) ->
//                Card(
//                    modifier = Modifier
//                        .padding(8.dp)
//                        .fillMaxWidth()
//                        .aspectRatio(1f), // Ensures square shape
//                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp),
//                    shape = RoundedCornerShape(12.dp), // Slightly more rounded
//                    colors = CardDefaults.cardColors(containerColor = Color.White) // ✅ White background
//                ) {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(16.dp),
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.Center
//                    ) {
//                        Image(
//                            painter = painterResource(id = image),
//                            contentDescription = label,
//                            modifier = Modifier
//                                .size(64.dp)
//                                .padding(bottom = 8.dp)
//                        )
//                        Text(
//                            text = label,
//                            fontSize = 16.sp,
//                            fontWeight = FontWeight.Bold,
//                            color = Color.Black
//                        )
//                        Text(
//                            text = count.toString(),
//                            fontSize = 12.sp,
//                            fontWeight = FontWeight.Bold,
//                            color = Color.Black
//                        )
//                    }
//                }
//            }
//        }


        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(cardData) { card ->
                Card(
                    modifier = Modifier
                        .width(250.dp)
                        .height(100.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(
                        containerColor = colorResource(id = R.color.teal_700)
                    ),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = card.iconRes),
                            contentDescription = card.title,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                                .padding(8.dp)
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            Text(
                                text = card.title,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                            Text(
                                text = card.amount,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.DarkGray
                            )
                        }
                    }
                }
            }
        }

        Text(text = "Recent Debts",
            color = colorResource(id = R.color.dark),
            fontWeight = FontWeight.Bold,
                    modifier = Modifier
                .padding(20.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .defaultMinSize(minHeight = 200.dp) // Ensures a minimum height of 200dp
                .clip(RoundedCornerShape(12.dp))
                .background(colorResource(id = R.color.white)),
        ) {


            if(isRecentLoading){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    repeat(6) {
                        ShimmerBox(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                                .height(100.dp) // Set a fixed height or use another layout
                        )
                    }
                }
            }else {

            if (recentDebtsAll.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
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
                            text = "No data found",
                            color = Color.Gray,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            } else {
//                for (item in recentDebts) {
//                    RecentDebtBox(
//                      navController = navController
//                    )
//                }
                for (item in recentDebtsAll) {
                    RecentDebtBox(
                        navController = navController,
                        recentDebt = item
                    )
                }

            }
        }
        }

    }
}
}

data class CardInfo(val title: String, val amount: String, val iconRes: Int)
