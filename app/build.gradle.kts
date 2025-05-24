plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp") version "2.0.20-1.0.25"
}

android {
    namespace = "com.st11.mydebts"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.st11.mydebts"
        minSdk = 26
        targetSdk = 34
        versionCode = 2
        versionName = "1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.media3.common.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //  this is for Material2 design
    implementation("com.google.android.material:material:1.12.0")

    // Koin dependencies for dependency injection
    // Koin Core (required)
    implementation("io.insert-koin:koin-android:3.5.0")

    // Koin for Jetpack Compose
    implementation("io.insert-koin:koin-androidx-compose:3.5.0")

    // Koin for Navigation in Jetpack Compose
    implementation("io.insert-koin:koin-androidx-navigation:3.5.0")

    // Lifecycle (to work with ViewModel)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    // Room dependencies
    implementation("androidx.room:room-runtime:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")

    // For coroutine support (if you're using suspend functions in DAO)
    implementation("androidx.room:room-ktx:2.6.1")


    //animation
    implementation("com.google.accompanist:accompanist-navigation-animation:0.31.2-alpha")

    //pager APi
    implementation("androidx.compose.foundation:foundation:1.7.8") // Use latest version

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.31.2-alpha")


    // DataStore for storing preferences
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    //font Awesome Icons
    implementation("br.com.devsrsouza.compose.icons:font-awesome:1.1.1")


}