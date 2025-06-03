package com.st11.mydebts.screens



import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.*
import com.st11.mydebts.R
import com.st11.mydebts.utils.DynamicStatusBar
import kotlinx.coroutines.launch
import com.airbnb.lottie.compose.*


//@OptIn(ExperimentalPagerApi::class)
//@Composable
//fun OnboardingScreen(onCompleteOnboarding: () -> Unit) {
//    val pagerState = rememberPagerState()
//    val scope = rememberCoroutineScope()
//
//
//    val backgroundColor = colorResource(id = R.color.white)
//    DynamicStatusBar(backgroundColor)
//
//    Column(
//        modifier = Modifier.fillMaxSize()
//            .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
//            .padding(bottom = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // **Pager for onboarding screens**
//        HorizontalPager(
//            state = pagerState,
//            count = onboardingPages.size,
//            modifier = Modifier.weight(1f)
//        ) { page ->
//            OnboardingPageContent(onboardingPages[page])
//        }
//
//        // **Page indicators**
//        HorizontalPagerIndicator(
//            pagerState = pagerState,
//            modifier = Modifier
//                .align(Alignment.CenterHorizontally)
//                .padding(16.dp),
//            activeColor = colorResource(id = R.color.pink),
//            inactiveColor = Color.Gray
//        )
//
//        // **Navigation Buttons**
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            if (pagerState.currentPage > 0) {
//                Button(
//                    onClick = { scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) } },
//                    shape = RoundedCornerShape(12.dp),
//                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.dark))
//                ) {
//                    Text("Prev")
//                }
//            }
//
//            if (pagerState.currentPage == onboardingPages.lastIndex) {
//                Button(
//                    onClick = { onCompleteOnboarding() },
//                    shape = RoundedCornerShape(12.dp),
//                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.pink))
//                ) {
//                    Text("Get Started")
//                }
//            } else {
//                Button(
//                    onClick = { scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) } },
//                    shape = RoundedCornerShape(12.dp),
//                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.dark))
//                ) {
//                    Text("Next")
//                }
//            }
//        }
//    }
//}
//
//// **Data class for onboarding pages**
//data class OnboardingPage(
//    val imageRes: Int,
//    val title: String,
//    val description: String
//)
//
//// **Onboarding page content**
//@Composable
//fun OnboardingPageContent(page: OnboardingPage) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(24.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Image(
//            painter = painterResource(id = page.imageRes),
//            contentDescription = "Onboarding Image",
//            modifier = Modifier.size(200.dp)
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        Text(text = page.title, fontWeight = FontWeight.Bold, fontSize = 24.sp)
//        Spacer(modifier = Modifier.height(8.dp))
//        Text(text = page.description, fontSize = 16.sp)
//    }
//}
//
//// **Onboarding pages list**
//val onboardingPages = listOf(
//    OnboardingPage(
//        imageRes = R.drawable.people,
//        title = "Create a Party Event",
//        description = "Create A party Event with friends and family"
//    ),
//    OnboardingPage(
//        imageRes = R.drawable.id,
//        title = "Your Identity",
//        description = "Add your phone number and name to create your Identity"
//    ),
//    OnboardingPage(
//        imageRes = R.drawable.scan,
//        title = "Gate Pass",
//        description = "Use Your QR code as a gate pass to enter the party and check in"
//    )
//)


@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingScreen(onCompleteOnboarding: () -> Unit) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    val backgroundColor = colorResource(id = R.color.white)
    DynamicStatusBar(backgroundColor)

    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(WindowInsets.statusBars.asPaddingValues()),
//        horizontalAlignment = Alignment.CenterHorizontally


        modifier = Modifier.fillMaxSize()
            .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
            .padding(bottom = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Pager
        HorizontalPager(
            state = pagerState,
            count = onboardingPages.size,
            modifier = Modifier.weight(1f)
        ) { page ->
            OnboardingPageContent(onboardingPages[page])
        }

        // Indicators
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier.padding(bottom = 32.dp),
//            activeColor = Color(0xFFE91E63), // pink
            activeColor = colorResource(id = R.color.teal_200),
            inactiveColor = Color.Gray
        )

        // Show only on last page
        if (pagerState.currentPage == onboardingPages.lastIndex) {
            Button(
                onClick = { onCompleteOnboarding() },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth(0.8f),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.teal_200))
            ) {
                Text("Get Started")
            }
//            Spacer(modifier = Modifier.height(32.dp)) // Prevent button from being covered
        } else {
            // Auto-swipe to next page after a short delay (optional)
            LaunchedEffect(pagerState.currentPage) {
                kotlinx.coroutines.delay(2000)
                scope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            }
        }
    }
}

data class OnboardingPage(
    val lottieAsset: String,
    val title: String,
    val description: String
)

@Composable
fun OnboardingPageContent(page: OnboardingPage) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.Asset(page.lottieAsset))
        val progress by animateLottieCompositionAsState(
            composition,
            iterations = LottieConstants.IterateForever
        )

        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp) // Adjust height if needed
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = page.title, fontWeight = FontWeight.Bold, fontSize = 24.sp, modifier = Modifier.padding(16.dp))
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = page.description, fontSize = 16.sp, modifier = Modifier.padding(16.dp))
    }
}

// Example pages with local .json Lottie files in assets/
val onboardingPages = listOf(
    OnboardingPage(
        lottieAsset = "managepeople.json",
        title = "Track Your Debts",
        description = "Click the plus icon on Home Screen to add people/clients," +
                " Easily manage records of people or clients you’ve lent money to or sold goods on credit."
    ),
    OnboardingPage(
        lottieAsset = "Animation04.json",
        title = "Debt Summary",
        description = "Get an overview of all debts to help you make informed decisions."
    ),
    OnboardingPage(
        lottieAsset = "record.json",
        title = "Timely Follow-Up",
        description = "Stay on top of due debts and follow up with clients when payments are expected."
    )
)

