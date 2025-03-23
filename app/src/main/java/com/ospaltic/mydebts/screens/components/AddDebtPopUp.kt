package com.ospaltic.mydebts.screens.components


//import android.icu.text.SimpleDateFormat
//import android.icu.util.Calendar
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.icons.Icons
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.colorResource
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.input.TextFieldValue
//import com.ospaltic.mydebts.R
//import java.util.Locale
//
//
////AddDebtPopUp
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AddDebtPopUpScreen(onDismiss: () -> Unit) {
//    var amount by remember { mutableStateOf("") }
//    var description by remember { mutableStateOf("") }
//    var date by remember { mutableStateOf("") }
//    var showDatePicker by remember { mutableStateOf(false) }
//
//    val context = LocalContext.current
//    val dateFormat = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }
//    val calendar = remember { Calendar.getInstance() }
//
//    if (showDatePicker) {
//        DatePickerDialog(
//            onDismissRequest = { showDatePicker = false },
//            confirmButton = {
//                TextButton(onClick = { showDatePicker = false }) {
//                    Text("OK")
//                }
//            }
//        ) {
//            DatePicker(
//                state = rememberDatePickerState(
//                    initialSelectedDateMillis = calendar.timeInMillis
//                ),
//                onDateSelected = { selectedMillis ->
//                    selectedMillis?.let {
//                        calendar.timeInMillis = it
//                        date = dateFormat.format(calendar.time) // Update selected date
//                    }
//                    showDatePicker = false
//                }
//            )
//        }
//    }
//
//    AlertDialog(
//        onDismissRequest = { onDismiss() },
//        title = { Text(text = "Add Debt") },
//        text = {
//            Column {
//                // Amount (User Input)
//                TextField(
//                    value = amount,
//                    onValueChange = { newValue ->
//                        if (newValue.all { it.isDigit() || it == '.' }) {
//                            amount = newValue
//                        }
//                    },
//                    label = { Text("Amount to Pay") },
//                    modifier = Modifier.fillMaxWidth(),
//                    singleLine = true,
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
//                )
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                // Description
//                TextField(
//                    value = description,
//                    onValueChange = { description = it },
//                    label = { Text("Description") },
//                    modifier = Modifier.fillMaxWidth(),
//                    singleLine = true
//                )
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                // Due Date Picker
//                TextField(
//                    value = date,
//                    onValueChange = {},
//                    label = { Text("Due Date") },
//                    modifier = Modifier.fillMaxWidth(),
//                    singleLine = true,
//                    readOnly = true,
//                    trailingIcon = {
//                        IconButton(onClick = { showDatePicker = true }) {
//                            Icon(imageVector = Icons.Default.CalendarToday, contentDescription = "Pick Date")
//                        }
//                    }
//                )
//            }
//        },
//        confirmButton = {
//            TextButton(onClick = { /* Handle submission */ }) {
//                Text("Add", color = colorResource(R.color.green))
//            }
//        },
//        dismissButton = {
//            TextButton(onClick = { onDismiss() }) {
//                Text("Cancel", color = colorResource(R.color.red))
//            }
//        }
//    )
//}

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
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
import com.ospaltic.mydebts.R
import java.util.Locale

@Composable
fun AddDebtPopUpScreen(onDismiss: () -> Unit) {
    var amount by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

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
                    label = { Text("Amount to Pay") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Description (User Input)
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

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
            }
        },
        confirmButton = {
            TextButton(onClick = { /* Handle saving debt */ }) {
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
