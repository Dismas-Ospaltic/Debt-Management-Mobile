package com.st11.mydebts.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.st11.mydebts.R
import com.st11.mydebts.utils.DynamicStatusBar



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreditAuthorScreen(navController: NavController) {

    val backgroundColor = colorResource(id = R.color.dark)
    DynamicStatusBar(backgroundColor)
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }
    var isHovered by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Credit & Acknowledgement", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold) }, // - Item $itemId
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(if (isHovered) Color.Gray.copy(alpha = 0.3f) else Color.Transparent)
                            .hoverable(interactionSource = interactionSource, enabled = true)
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onPress = {
                                        isHovered = true
                                        tryAwaitRelease()
                                        isHovered = false
                                    }
                                )
                            }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = colorResource(id = R.color.dark),
                    navigationIconContentColor = Color.White,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = colorResource(id = R.color.light_bg_color))
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {


            val iconCredits = listOf(
                "App icon designed by Freepik from Flaticon",

                "Debt overview icons:",
                "- Number of People/Clients icon designed by Freepik from Flaticon",
                "- Total Debt icon designed by Freepik from Flaticon",
                "- Number of Unpaid icon designed by Muhammad_Usman from Flaticon",
                "- Number of Paid icons designed by zero_wing from Flaticon",
                "- Due debt icon designed by Freepik from Flaticon",
                "-Partial Paid Debt icon designed by zero_wing from Flaticon",
                "App graphic images for Background:",
                "\"Client details Background\" image by rawpixel.com on Freepik",
                "\"Debt Details background\" image by freepik on Freepik"
            )


            iconCredits.forEach { credit ->
                Text(
                    text = credit,
                    style = if (credit.endsWith(":"))
                        MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    else
                        MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }


            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Visit Flaticon: www.flaticon.com",
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Blue),
                modifier = Modifier.clickable {
                    val url = "https://www.flaticon.com"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Visit Freepik: www.freepik.com",
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Blue),
                modifier = Modifier.clickable {
                    val url = "https://www.freepik.com"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent)
                }
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreditAuthorScreenPreview() {
    CreditAuthorScreen(navController = rememberNavController())
}




