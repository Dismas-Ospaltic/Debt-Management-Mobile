package com.ospaltic.mydebts.screens.components


import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ospaltic.mydebts.R
import com.ospaltic.mydebts.model.DebtEntity
import com.ospaltic.mydebts.utils.formatDate
import com.ospaltic.mydebts.viewmodel.DebtViewModel
import org.koin.androidx.compose.koinViewModel
import java.util.Locale
import java.util.UUID

@Composable
fun AddDebtPopUpScreen(itemId: String?, onDismiss: () -> Unit) {
    var amount by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val debtViewModel: DebtViewModel = koinViewModel()
    val currentDate = remember { System.currentTimeMillis() }
    val formattedDate = formatDate(currentDate)

    val errorColor = colorResource(id = R.color.red)

    // Validation errors
    var amountError by remember { mutableStateOf(false) }
    var dateError by remember { mutableStateOf(false) }
    var descriptionError by remember { mutableStateOf(false) }


    fun validateInputs(): Boolean {
        amountError = amount.isBlank()
        dateError = date.isBlank()
        descriptionError = description.isBlank()

        return !(amountError || dateError || descriptionError)
    }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    fun showDatePicker() {
        DatePickerDialog(
            context,
            { _, year, month, day ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, day)
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                date = dateFormat.format(selectedDate.time) // Format date to "YYYY-MM-DD"
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Add Debt") },
        text = {
            Column {
                // Amount (User Input)
                TextField(
                    value = amount,
                    onValueChange = { newValue ->
                        if (newValue.all { it.isDigit() || it == '.' }) {
                            amount = newValue
                        }
                    },
                    label = { Text("Amount") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                if (amountError) {
                    Text("Amount is required", color = errorColor, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Description (User Input)
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )
                if (amountError) {
                    Text("Description is required", color = errorColor, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Date Picker TextField
                TextField(
                    value = date,
                    onValueChange = {},
                    label = { Text("Due Date") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker() }) {
                            Icon(
                            painter = painterResource(id = R.drawable.calendar_icon), // Use for Date Picker
                            contentDescription = "Select Date"
                            )
                        }
                    }
                )
                if (amountError) {
                    Text("Due date is required", color = errorColor, fontSize = 12.sp)
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {

                if (itemId.isNullOrBlank()) {
                    Toast.makeText(context, "Error: Invalid user ID", Toast.LENGTH_SHORT).show()
                    return@TextButton
                }
            /* Handle saving debt */
                if (validateInputs()) {
                debtViewModel.insertDebt(
                   DebtEntity(
                       date = formattedDate,
                       debtId = generateUniqueId(),
                       uid = itemId,
                       amount = amount.toFloat(),
                       amountRem = amount.toFloat(),
                       amountPaid = 0f,
                       description = description,
                       status = "Pending",
                       dueDate = date
                   )
                )
                onDismiss()
                }
            }) {
                Text("Add", color = colorResource(R.color.green))
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancel", color = colorResource(R.color.red))
            }
        }
    )
}

fun generateUniqueId(): String {
    return UUID.randomUUID().toString()
}
