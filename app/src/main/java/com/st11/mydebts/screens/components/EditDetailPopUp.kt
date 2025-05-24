package com.st11.mydebts.screens.components


import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.st11.mydebts.R
import com.st11.mydebts.model.DebtEntity
import com.st11.mydebts.model.PeopleEntity
import com.st11.mydebts.utils.ShimmerBox
import com.st11.mydebts.utils.formatDate
import com.st11.mydebts.viewmodel.DebtViewModel
import com.st11.mydebts.viewmodel.PeopleViewModel
import org.koin.androidx.compose.koinViewModel
import java.util.Locale
import java.util.UUID

@Composable
fun EditDebtPopUpScreen(itemId: String?, onDismiss: () -> Unit) {
   val peopleViewModel: PeopleViewModel = koinViewModel();
    val isPersonDetailsLoading by peopleViewModel.isPersonDetailsLoading.collectAsState()
    // Observe the person state
    val person = peopleViewModel.personState.collectAsState().value

    // Fetch user details if itemId is not null
    LaunchedEffect(itemId) {
        itemId?.let { peopleViewModel.getPersonDetails(it) }
    }
  val context = LocalContext.current

    // State variables for input fields
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var businessName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
     var id by remember { mutableIntStateOf(0) }

    // Validation errors
    var firstNameError by remember { mutableStateOf(false) }
    var lastNameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var phoneError by remember { mutableStateOf(false) }


    val currentDate = remember { System.currentTimeMillis() }
    val formattedDate = formatDate(currentDate)

    LaunchedEffect(person) {
        person?.let {
            if (firstName.isEmpty()) {
                firstName = it.firstName
                lastName = it.lastName
                email = it.email
                phone = it.phone
                businessName = it.businessName ?: ""
                address = it.address ?: ""
                date = it.date ?: ""
                id  = it.id ?: 0
            }
        }
    }

    // Custom colors from res
    val primaryColor = colorResource(id = R.color.teal_700)
    val errorColor = colorResource(id = R.color.red)

    fun validateInputs(): Boolean {
        firstNameError = firstName.isBlank()
        lastNameError = lastName.isBlank()
        emailError = !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        phoneError = phone.isBlank()

        return !(firstNameError || lastNameError || emailError || phoneError)
    }


        AlertDialog(
            onDismissRequest = {  onDismiss() },
            title = { Text(text = "Edit Customer/ client",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(
                id = R.color.teal_700
            )
            )
            },
            text = {
                Column {
                    if (isPersonDetailsLoading) {
                        LazyColumn(
                            modifier = Modifier
//                                .fillMaxSize()
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            items(3) {
                                ShimmerBox(
                                    modifier = Modifier
                                        .padding(5.dp)
                                        .fillMaxWidth()
                                        .height(20.dp) // Set a fixed height or use another layout
                                )
                            }
                        }
                    } else {
                        if (person != null) {
                        TextField(
                            value = firstName,
                            onValueChange = { firstName = it },
                            label = { Text("First Name") },
                            modifier = Modifier.fillMaxWidth(),
                            isError = firstNameError,
                            singleLine = true
                        )
                        if (firstNameError) {
                            Text("First name is required", color = errorColor, fontSize = 12.sp)
                        }

                        TextField(
                            value = lastName,
                            onValueChange = { lastName = it },
                            label = { Text("Last Name") },
                            modifier = Modifier.fillMaxWidth(),
                            isError = lastNameError,
                            singleLine = true
                        )
                        if (lastNameError) {
                            Text("Last name is required", color = errorColor, fontSize = 12.sp)
                        }

                        TextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Email") },
                            modifier = Modifier.fillMaxWidth(),
                            isError = emailError,
                            singleLine = true
                        )
                        if (emailError) {
                            Text("Enter a valid email", color = errorColor, fontSize = 12.sp)
                        }

                        TextField(
                            value = phone,
                            onValueChange = { phone = it },
                            label = { Text("Phone") },
                            modifier = Modifier.fillMaxWidth(),
                            isError = phoneError,
                            singleLine = true
                        )
                        if (phoneError) {
                            Text("Phone number is required", color = errorColor, fontSize = 12.sp)
                        }

                        TextField(
                            value = businessName,
                            onValueChange = { businessName = it },
                            label = { Text("Business Name") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        TextField(
                            value = address,
                            onValueChange = { address = it },
                            label = { Text("Address") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    }

                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (validateInputs()) {
                            // Handle saving the data here
//
//                            peopleViewModel.update(PeopleEntity
//                                (
//                                id = 0,
//                                uid = itemId.toString(),
//                                firstName = firstName,
//                                lastName = lastName,
//                                email = email, phone = phone,
//                                businessName = businessName,
//                                address = address,
//                                date = date))
                            peopleViewModel.updateCustomerDetails(itemId.toString(),
                                firstName,
                                lastName,
                                email,
                                phone,
                                businessName,
                                address)
                            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                            onDismiss()
                        }
                    }
                ) {
                    Text("Save", color = colorResource(id = R.color.teal_700))
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text("Cancel", color = colorResource(id = R.color.red))
                }
            }
        )


}

