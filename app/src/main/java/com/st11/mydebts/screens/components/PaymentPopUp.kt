package com.st11.mydebts.screens.components

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.st11.mydebts.R
import com.st11.mydebts.model.DebtEntity
import com.st11.mydebts.model.RepayEntity
import com.st11.mydebts.utils.formatDate
import com.st11.mydebts.viewmodel.CurrencyViewModel
import com.st11.mydebts.viewmodel.DebtPayViewModel
import com.st11.mydebts.viewmodel.DebtViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PaymentPopupScreen(onDismiss: () -> Unit, debt: DebtEntity) {
    var amount by remember { mutableStateOf("") }
    var balance by remember { mutableFloatStateOf(0f) }
    var change by remember { mutableFloatStateOf(0f) }
    val debtPayViewModel: DebtPayViewModel = koinViewModel()
    val currentDate = remember { System.currentTimeMillis() }
    val formattedDate = formatDate(currentDate)
    val debtViewModel: DebtViewModel = koinViewModel()

    val currencyViewModel: CurrencyViewModel = koinViewModel()
    val currency by currencyViewModel.userData.collectAsState()


    val context = LocalContext.current
    val errorColor = colorResource(id = R.color.red)
    var amountError by remember { mutableStateOf(false) }
//    val totalAmount by remember { mutableFloatStateOf(debt.amount ?: 0f) }
    var totalAmount by remember { mutableFloatStateOf(0f) }
    val totalPaid by debtPayViewModel.totalPaid.collectAsState()

    val totalAmountCredit by remember { mutableFloatStateOf(debt.amount ?: 0f) }


    LaunchedEffect(debt.debtId) {
        debt.debtId.let { debtPayViewModel.fetchTotalPaid(it)
            Log.d("UI", "Fetching total paid for: $it") // Debugging
        }

    }


    totalAmount = if(totalPaid > 0f){
        (totalAmountCredit - totalPaid).toFloat()
    }else{
        totalAmountCredit
    }


    fun validateInputs(): Boolean {
        amountError = amount.isBlank()
        return !amountError
    }

    fun updateBalance(newAmount: String) {
        val amountFloat = newAmount.toFloatOrNull() ?: 0f
        if (amountFloat >= totalAmount) {
            balance = 0f
            change = amountFloat - totalAmount
        } else {
            balance = totalAmount - amountFloat
            change = 0f
        }
    }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Make Payment") },
        text = {
            Column {
                Text(
                    text = "Total Amount: ${currency.userCurrency} ${"%.2f".format(totalAmount)}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                TextField(
                    value = amount,
                    onValueChange = {
                        if (it.toFloatOrNull() != null || it.isEmpty()) {
                            amount = it
                            updateBalance(it)
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
            TextButton(onClick = {
                if (validateInputs()) {
                    val amountPaid = amount.toFloatOrNull() ?: 0f
                    if (amountPaid <= 0f) return@TextButton
                    val newBalance = totalAmount - amountPaid
                    val newStatus = if (amountPaid >= totalAmount) "Paid" else "Partial"
                    change = if (amountPaid > totalAmount) amountPaid - totalAmount else 0f
                    debtPayViewModel.insertRepayment(
                        RepayEntity(
                            uid = debt.uid,
                            amountPaid = amountPaid,
                            amountRem = newBalance.coerceAtLeast(0f),
                            date = formattedDate,
                            debtId = debt.debtId
                        )
                    )
                    debtViewModel.updateDebtStatus(debt.debtId, newStatus)
                    debtViewModel.updateDebtValues(debt.debtId,balance,((amountPaid - change) + totalPaid))
//                    debtViewModel.updateDebt(debt.copy(status = newStatus))
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
