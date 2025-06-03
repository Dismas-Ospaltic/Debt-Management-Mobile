package com.st11.mydebts.screens.components



import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.st11.mydebts.R
import com.st11.mydebts.model.DebtEntity
import com.st11.mydebts.model.RecentDebt
import com.st11.mydebts.viewmodel.CurrencyViewModel
import com.st11.mydebts.viewmodel.DebtViewModel
import com.st11.mydebts.viewmodel.PeopleViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun RecentDebtBox(navController: NavController, recentDebt: RecentDebt) {
    var showDialog by remember { mutableStateOf(false) } // State to control popup visibility
    val currencyViewModel: CurrencyViewModel = koinViewModel()
    val currency by currencyViewModel.userData.collectAsState()


    val peopleViewModel: PeopleViewModel = koinViewModel()
    val context = LocalContext.current
    val debtViewModel: DebtViewModel = koinViewModel()



//    val recentDebts by debtViewModel.recentDebts.collectAsState()
//    val recentDebt = recentDebts.firstOrNull()
//    val clientDet by peopleViewModel.personState.collectAsState()

//
//    LaunchedEffect(recentDebt?.uid) {
//        recentDebt?.uid?.let {
//            peopleViewModel.getPersonDetails(it)
//        }
//    }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .clickable { },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.light_bg_color))
    ) {
        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            verticalAlignment = Alignment.CenterVertically
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start
        ) {



            // Left Section
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text("Customer: ${recentDebt.firstName} ${recentDebt.lastName}", color = colorResource(id = R.color.dark), fontSize = 16.sp)
                Text("Phone: ${recentDebt.phone}", color = colorResource(id = R.color.dark), fontSize = 16.sp)
                Text("Amount: ${currency.userCurrency} ${recentDebt.amount}", color = colorResource(id = R.color.teal_200), fontSize = 16.sp)
                Text("Due Date: ${recentDebt.dueDate}", color = colorResource(id = R.color.teal_200), fontSize = 16.sp)
            }

//            // Right Section (Buttons)
//            Column(
//                verticalArrangement = Arrangement.spacedBy(8.dp),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                modifier = Modifier.weight(0.4f)
//            ) {
//                // Show "Pay Now" button only if status is NOT "Paid"
//                if (!debt.status.equals("Paid", ignoreCase = true)) {
//                    Button(
//                        onClick = { showDialog = true }, // Show popup when clicked
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = colorResource(id = R.color.teal_200)
//                        ),
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        Text("Pay Now", color = Color.White)
//                    }
//                }
//
//                Button(
//                    onClick = { navController.navigate(Screen.HistDetail.createRoute(debt.debtId)) },
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = colorResource(id = R.color.dark)
//                    ),
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Text("History", color = Color.White)
//                }
//
//
//                Button(
//                    onClick = { navController.navigate(Screen.DebtDetail.createRoute(debt.debtId)) },
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = colorResource(id = R.color.teal_700)
//                    ),
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Text("Details", color = Color.White)
//                }
//            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun RecentDebtBoxPreview() {
    val mockNavController = rememberNavController() // ✅ Correct way to create NavController in Compose

//    PaymentBox(
//        navController = mockNavController, // ✅ Pass the mock NavController
//        payment = DebtEntity("2025-03-10", "2025-04-10", "$120.00", "$120.00", "Paid")
//    )
}