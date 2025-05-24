package com.st11.mydebts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.st11.mydebts.data.datastore.CurrencyPreferences
import com.st11.mydebts.data.datastore.UserData
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CurrencyViewModel(
    private val currencyPreferences: CurrencyPreferences
) : ViewModel() {

    // Observe isIdentityCreated separately
    val isCurrencySet: StateFlow<Boolean> = currencyPreferences.isCurrencySet
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)


    // StateFlow to observe user data
    val userData: StateFlow<UserData> = currencyPreferences.userData
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserData("",  false))

    // Save user identity
    fun saveUserCurrency(currency: String) {
        viewModelScope.launch {
            currencyPreferences.saveUserData(currency)

        }
    }

    // Clear identity (e.g., on logout)
    fun clearUserIdentity() {
        viewModelScope.launch {
            currencyPreferences.clearUserData()
        }
    }
}