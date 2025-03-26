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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ospaltic.mydebts.R
import com.ospaltic.mydebts.model.HistoryItem
import com.ospaltic.mydebts.model.PaymentItem
import com.ospaltic.mydebts.model.RepayEntity


@Composable
fun PayHistoryCard(history: RepayEntity) {
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
            // Left Section: 80% of the width
            Column(
                modifier = Modifier
                    .weight(1f) // Takes 80% of the row width
                    .padding(end = 8.dp) // Adds some space before buttons
            ) {
                Text("Date: ${history.date}", color = Color.Black, fontSize = 16.sp)
                Text("Paid: ${history.amountPaid}", color = Color.Black, fontSize = 16.sp)
                Text("Remaining: ${history.amountRem}", color = Color.Black, fontSize = 16.sp)
            }

        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun PayHistoryCardPreview() {
//    PayHistoryCard(
//        history = RepayEntity("2025-03-10", "$120.00", "$120.00")
//    )
//}