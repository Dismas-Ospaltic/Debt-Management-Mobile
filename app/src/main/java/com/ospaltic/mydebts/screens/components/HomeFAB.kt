package com.ospaltic.mydebts.screens.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.compose.rememberNavController
import com.ospaltic.mydebts.R
import com.ospaltic.mydebts.screens.HomeScreen

@Composable
fun HomeFAB() {
    var showDialog by remember { mutableStateOf(false) }

    // State variables for input fields
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var businessName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    // Validation errors
    var firstNameError by remember { mutableStateOf(false) }
    var lastNameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var phoneError by remember { mutableStateOf(false) }

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

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Add New Person") },
            text = {
                Column {
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
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (validateInputs()) {
                            showDialog = false
                            // Handle saving the data here
                        }
                    }
                ) {
                    Text("Save", color = primaryColor)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel", color = primaryColor)
                }
            }
        )
    }

    FloatingActionButton(
        onClick = { showDialog = true },
        containerColor = primaryColor,
        modifier = Modifier
            .padding(bottom = 32.dp, end = 16.dp)
            .zIndex(1f)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.add_white),
            contentDescription = "Add"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeFABPreview() {
    HomeFAB()
}



