package com.st11.mydebts.screens.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.st11.mydebts.R
import com.st11.mydebts.model.PeopleEntity
import com.st11.mydebts.utils.formatDate
import com.st11.mydebts.viewmodel.PeopleViewModel
import org.koin.androidx.compose.koinViewModel
import java.security.MessageDigest
import java.security.SecureRandom

@Composable
fun HomeFAB(peopleViewModel: PeopleViewModel = koinViewModel()) {
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


    val currentDate = remember { System.currentTimeMillis() }
    val formattedDate = formatDate(currentDate)

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
            title = { Text(text = "Add New Customer/ client", fontSize = 18.sp, fontWeight = FontWeight(700), color = colorResource(id = R.color.teal_700)) },
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
                            // Handle saving the data here
//                            noteViewModel.insert(NoteEntity(title = title, noteId= generateUniqueId(), content = content))
                        peopleViewModel.insert(PeopleEntity(uid = generateUniqueUserId(),
                            firstName = firstName,
                            lastName = lastName,
                            email = email, phone = phone,
                            businessName = businessName,
                            address = address,
                            date = formattedDate))

                            // Clear all fields after saving
                            firstName = ""
                            lastName = ""
                            email = ""
                            phone = ""
                            businessName = ""
                            address = ""

                            // Clear validation states too (optional but recommended)
                            firstNameError = false
                            lastNameError = false
                            emailError = false
                            phoneError = false
                            showDialog = false
                        }
                    }
                ) {
                    Text("Save", color = colorResource(id = R.color.teal_700))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel", color = colorResource(id = R.color.red))
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


//fun generateUniqueId(): String {
//    return UUID.randomUUID().toString()
//}

fun generateUniqueUserId(): String {
    val random = SecureRandom()
    val bytes = ByteArray(16)
    random.nextBytes(bytes)

    val md = MessageDigest.getInstance("SHA-256")
    val hash = md.digest(bytes)

    return hash.joinToString("") { "%02x".format(it) }
        .chunked(8)
        .joinToString("-")
}



