package com.st11.mydebts.data.datastore


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// ✅ Rename the DataStore extension property
private val Context.userDataStore: DataStore<androidx.datastore.preferences.core.Preferences>
        by preferencesDataStore(name = "user_currency_prefs")

class CurrencyPreferences(private val context: Context) {

    companion object {
        val CURRENCY = stringPreferencesKey("user_currency")
        val IS_CURRENCY_SET = booleanPreferencesKey("is_currency_set")
    }

    // Save user data
    suspend fun saveUserData(userCurrency: String) {
        context.userDataStore.edit { preferences ->
            preferences[CURRENCY] = userCurrency
            preferences[IS_CURRENCY_SET] = true
        }
    }

    // Retrieve user data as Flow (to observe changes)
    val userData: Flow<UserData> = context.userDataStore.data.map { preferences ->
        UserData(
            userCurrency = preferences[CURRENCY] ?: "",
            isCurrencySet = preferences[IS_CURRENCY_SET] ?: false
        )
    }

    // Clear user data (logout)
    suspend fun clearUserData() {
        context.userDataStore.edit { preferences ->
            preferences.clear()
        }
    }

    // Expose isIdentityCreated as Flow<Boolean>
    val isCurrencySet: Flow<Boolean> = context.userDataStore.data.map { preferences ->
        preferences[IS_CURRENCY_SET] ?: false
    }

}

// Data class for user data
data class UserData(
    val userCurrency: String,
    val isCurrencySet: Boolean
)