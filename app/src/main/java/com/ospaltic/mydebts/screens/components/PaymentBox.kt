package com.ospaltic.mydebts.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ospaltic.mydebts.R
import com.ospaltic.mydebts.model.PaymentItem
import com.ospaltic.mydebts.navigation.Screen


//@Composable
//fun PaymentBox(navController: NavController, payment: PaymentItem) {
//
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp)
//            .clickable { },
//        shape = RoundedCornerShape(12.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.White)
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            // Left Section: 80% of the width
//            Column(
//                modifier = Modifier
//                    .weight(0.5f) // Takes 80% of the row width
//                    .padding(end = 8.dp) // Adds some space before buttons
//            ) {
//                Text("Date: ${payment.date}", color = Color.Black, fontSize = 16.sp)
//                Text("Due Date: ${payment.dueDate}", color = Color.Black, fontSize = 16.sp)
//                Text("Amount: ${payment.amount}", color = Color.Black, fontSize = 16.sp)
//                Text("Paid: ${payment.paid}", color = Color.Green, fontSize = 16.sp)
//            }
//
//            // Right Section (Buttons): 20% of the width
//            Column(
//                verticalArrangement = Arrangement.spacedBy(8.dp),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                modifier = Modifier.weight(0.5f) // Takes 20% of the row width
//            ) {
//                Button(
//                    onClick = { /* Handle Pay Now */PaymentPopupScreen()   },
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = colorResource(id = R.color.payNowButton)
//                    ),
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Text("Pay Now", color = Color.White)
//                }
//
//                Button(
//                    onClick = {  navController.navigate(Screen.HistDetail.createRoute(payment.date))/* Handle View History */ },
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = colorResource(id = R.color.dark)
//                    ),
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Text("View History", color = Color.White)
//                }
//            }
//        }
//    }
//}

@Composable
fun PaymentBox(navController: NavController, payment: PaymentItem) {
    var showDialog by remember { mutableStateOf(false) } // State to control popup visibility

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left Section
            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(end = 8.dp)
            ) {
                Text("Date: ${payment.date}", color = Color.Black, fontSize = 16.sp)
                Text("Due Date: ${payment.dueDate}", color = Color.Black, fontSize = 16.sp)
                Text("Amount: ${payment.amount}", color = Color.Black, fontSize = 16.sp)
                Text("Paid: ${payment.paid}", color = Color.Green, fontSize = 16.sp)
            }

            // Right Section (Buttons)
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(0.5f)
            ) {
                Button(
                    onClick = { showDialog = true }, // Show popup when clicked
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.payNowButton)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Pay Now", color = Color.White)
                }

                Button(
                    onClick = { navController.navigate(Screen.HistDetail.createRoute(payment.date)) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.dark)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("View History", color = Color.White)
                }
            }
        }
    }

    // Show the Payment Popup if showDialog is true
    if (showDialog) {
        PaymentPopupScreen(onDismiss = { showDialog = false }, payment = payment)
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentBoxPreview() {
    val mockNavController = rememberNavController() // ✅ Correct way to create NavController in Compose

    PaymentBox(
        navController = mockNavController, // ✅ Pass the mock NavController
        payment = PaymentItem("2025-03-10", "2025-04-10", "$120.00", "$120.00", "Paid")
    )
}