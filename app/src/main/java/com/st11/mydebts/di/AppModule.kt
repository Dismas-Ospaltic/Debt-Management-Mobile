package com.st11.mydebts.di

import com.st11.mydebts.data.datastore.CurrencyPreferences
import com.st11.mydebts.data.local.AppDatabase
import com.st11.mydebts.repository.DebtRepayRepository
import com.st11.mydebts.repository.DebtRepository
import com.st11.mydebts.repository.PeopleRepository
import com.st11.mydebts.viewmodel.CurrencyViewModel
import com.st11.mydebts.viewmodel.DebtPayViewModel
import com.st11.mydebts.viewmodel.DebtViewModel
import com.st11.mydebts.viewmodel.PeopleViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
//    // Singleton Repository
//    single { Repository() }
//
//    // ViewModels
//    viewModel { HomeViewModel(get()) }


    single { AppDatabase.getDatabase(get()).peopleDao() }
    single { PeopleRepository(get()) }
    viewModel {  PeopleViewModel(get()) }



    single { AppDatabase.getDatabase(get()).debtDao() }
    single { DebtRepository(get()) }
    viewModel {  DebtViewModel(get()) }


    viewModel { DebtPayViewModel(get()) }
    single { DebtRepayRepository(get()) }
    single { AppDatabase.getDatabase(get()).debtRepayDao() }


    // Define ViewModel injection
    viewModel { CurrencyViewModel(get()) }

    // Define PreferencesManager injection
    single { CurrencyPreferences(get()) }
}

