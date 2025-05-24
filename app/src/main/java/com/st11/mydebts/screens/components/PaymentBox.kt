package com.st11.mydebts.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.st11.mydebts.R
import com.st11.mydebts.model.DebtEntity
import com.st11.mydebts.navigation.Screen
import com.st11.mydebts.utils.CircularPercentageBar
import com.st11.mydebts.viewmodel.CurrencyViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun PaymentBox(navController: NavController, debt: DebtEntity) {
    var showDialog by remember { mutableStateOf(false) } // State to control popup visibility
    val currencyViewModel: CurrencyViewModel = koinViewModel()
    val currency by currencyViewModel.userData.collectAsState()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .clickable { },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.light_bg_color))
    ) {
        // Calculate progress
        val paidPercentage = if (debt.amount > 0) {
            (debt.amountPaid) / debt.amount
        } else 0f

        CircularPercentageBar(
            percentage = paidPercentage.coerceIn(0f, 1f),
//            modifier = Modifier
//                .size(48.dp) // adjust size as needed
//                .align(Alignment.TopStart)
        )

        Spacer(modifier = Modifier.width(8.dp)) // spacing between circle and text
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
                    .weight(0.6f)
                    .padding(end = 8.dp)
            ) {
                Text("Date: ${debt.date}", color = colorResource(id = R.color.dark), fontSize = 16.sp)
                Text("Due Date: ${debt.dueDate}", color = colorResource(id = R.color.dark), fontSize = 16.sp)
                Text("Amount:${currency.userCurrency} ${debt.amount}", color = colorResource(id = R.color.dark), fontSize = 16.sp)
                Text("Paid:${currency.userCurrency} ${debt.amountPaid}", color = colorResource(id = R.color.teal_700), fontSize = 16.sp)
                Text("Amount Rem:${currency.userCurrency} ${debt.amountRem}", color = colorResource(id = R.color.teal_700), fontSize = 16.sp)
            }

            // Right Section (Buttons)
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(0.4f)
            ) {
                // Show "Pay Now" button only if status is NOT "Paid"
                if (!debt.status.equals("Paid", ignoreCase = true)) {
                    Button(
                        onClick = { showDialog = true }, // Show popup when clicked
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.teal_200)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Pay Now", color = Color.White)
                    }
                }

                Button(
                    onClick = { navController.navigate(Screen.HistDetail.createRoute(debt.debtId)) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.dark)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("History", color = Color.White)
                }


                Button(
                    onClick = { navController.navigate(Screen.DebtDetail.createRoute(debt.debtId)) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.teal_700)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Details", color = Color.White)
                }
            }
        }
    }

     //Show the Payment Popup if showDialog is true
    if (showDialog) {
//        PaymentPopupScreen(itemId = itemId, debt=debt) { showAddDebtDialog = false }
        PaymentPopupScreen(onDismiss = { showDialog = false }, debt=debt)
//        PaymentPopupScreen(itemId = debt.uid, onDismiss = {  showDialog = false } , debt=debt)
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentBoxPreview() {
    val mockNavController = rememberNavController() // ✅ Correct way to create NavController in Compose

//    PaymentBox(
//        navController = mockNavController, // ✅ Pass the mock NavController
//        payment = DebtEntity("2025-03-10", "2025-04-10", "$120.00", "$120.00", "Paid")
//    )
}