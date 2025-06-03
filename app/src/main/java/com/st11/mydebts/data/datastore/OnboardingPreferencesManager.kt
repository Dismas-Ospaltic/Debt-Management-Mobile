package com.st11.mydebts.data.datastore


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Create a singleton DataStore instance
val Context.dataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(name = "app_preferences")

class OnboardingPreferencesManager(private val context: Context) {

    private val onboardingKey = booleanPreferencesKey("onboarding_completed")

    // Flow to get the current onboarding status
    val isOnboardingCompleted: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[onboardingKey] ?: false  // Return false if no value is set
        }

    // Method to mark onboarding as completed
    suspend fun completeOnboarding() {
        context.dataStore.edit { preferences ->
            preferences[onboardingKey] = true
        }
    }
}