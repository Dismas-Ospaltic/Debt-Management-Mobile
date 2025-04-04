package com.ospaltic.mydebts.screens.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ospaltic.mydebts.R
import com.ospaltic.mydebts.model.DebtEntity
import com.ospaltic.mydebts.utils.formatDate
import com.ospaltic.mydebts.viewmodel.DebtPayViewModel
import com.ospaltic.mydebts.viewmodel.DebtViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PayAllPopupScreen(onDismiss: () -> Unit, totalAmount: Float, itemId: String) {
    var amount by remember { mutableStateOf("") }
    var balance by remember { mutableFloatStateOf(0f) }
    var change by remember { mutableFloatStateOf(0f) }


    val debtPayViewModel: DebtPayViewModel = koinViewModel()
    val currentDate = remember { System.currentTimeMillis() }
    val formattedDate = formatDate(currentDate)
    val debtViewModel: DebtViewModel = koinViewModel()

    val totalUnpaid by debtViewModel.totalUnpaid.collectAsState()


    LaunchedEffect(itemId) {
        itemId.let { debtViewModel.fetchTotalUnpaid(itemId)
            Log.d("UI", "Fetching total unpaid for: $itemId") // Debugging
        }

    }

    val errorColor = colorResource(id = R.color.red)
    var amountError by remember { mutableStateOf(false) }

    fun updateBalance(newAmount: String) {
        val amountFloat = newAmount.toFloatOrNull() ?: 0f

        if (amountFloat >= totalAmount) {
            balance = 0f
            change = amountFloat - totalAmount // Extra amount paid
        } else {
            balance = totalAmount - amountFloat // Remaining balance (negative)
            change = 0f
        }
    }


    fun validateInputs(): Boolean {
        amountError = amount.isBlank()
        return !amountError
    }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Make Payment For All Debt") },
        text = {
            Column {

                // Display Total Amount
                Text(
                    text = "$totalUnpaid Total Amount: ${"%.2f".format(totalAmount)}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                // Amount (User Input)
                TextField(
                    value = amount,
                    onValueChange = { newValue ->
                        if (newValue.toFloatOrNull() != null || newValue.isEmpty()) {
                            amount = newValue
                            updateBalance(newValue)
                        }
                    },
                    label = { Text("Amount to Pay") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                if (amountError) {
                Text("Amount is required", color = errorColor, fontSize = 12.sp)
            }

                Spacer(modifier = Modifier.height(8.dp))

                // Balance (Read-Only)
                TextField(
                    value = "%.2f".format(balance),
                    onValueChange = {},
                    label = { Text("Balance Remaining") },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    colors = TextFieldDefaults.colors(
                        disabledTextColor = Color.Gray,
                        disabledContainerColor = Color.LightGray
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Change (Read-Only)
                TextField(
                    value = "%.2f".format(change),
                    onValueChange = {},
                    label = { Text("Change") },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    colors = TextFieldDefaults.colors(
                        disabledTextColor = Color.Gray,
                        disabledContainerColor = Color.LightGray
                    )
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { /* Handle payment submission */
                if (validateInputs()) {

                    onDismiss()
                }
            }) {
                Text("Pay", color = colorResource(R.color.green))
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancel", color = colorResource(R.color.red))
            }
        }
    )
}