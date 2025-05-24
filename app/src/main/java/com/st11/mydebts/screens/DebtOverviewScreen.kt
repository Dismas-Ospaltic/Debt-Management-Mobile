package com.st11.mydebts.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.st11.mydebts.R
import com.st11.mydebts.utils.DynamicStatusBar
import com.st11.mydebts.viewmodel.CurrencyViewModel
import com.st11.mydebts.viewmodel.DebtViewModel
import com.st11.mydebts.viewmodel.PeopleViewModel
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

    val currencyViewModel: CurrencyViewModel = koinViewModel()
    val currency by currencyViewModel.userData.collectAsState()

    LaunchedEffect(Unit) {
        peopleViewModel.fetchTotalNoPeople()
        debtViewModel.fetchTotalUnpaidPaidAllPeople()
        debtViewModel.fetchTotalNoUnpaid()
        debtViewModel.fetchTotalNoPartial()
        debtViewModel.fetchTotalNoPeople()
        debtViewModel.fetchTotalNoOverDue()
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
                paddingValues
//                top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
            ) // Ensure content does not overlap status bar
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


//        val items = listOf(
//            Triple(R.drawable.user, "People / Clients", totalPeople.toInt()),
//            Triple(R.drawable.debtdash, "Total Debt", ${currency.userCurrency} totalUnpaidPeople.toInt()),
//            Triple(R.drawable.burden, "Unpaid No.", totalNoPending),
//            Triple(R.drawable.debthand, "Paid No.", totalNoPaid),
//            Triple(R.drawable.debthand, "Partial No.", totalNoPartial),
//            Triple(R.drawable.debtdue, "Past Due No.", totalNoOverDue)
//        )

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
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}
}
