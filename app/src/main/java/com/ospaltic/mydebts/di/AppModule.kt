package com.ospaltic.mydebts.di

import com.ospaltic.mydebts.data.local.AppDatabase
import com.ospaltic.mydebts.repository.DebtRepayRepository
import com.ospaltic.mydebts.repository.DebtRepository
import com.ospaltic.mydebts.repository.PeopleRepository
import com.ospaltic.mydebts.viewmodel.DebtPayViewModel
import com.ospaltic.mydebts.viewmodel.DebtViewModel
import com.ospaltic.mydebts.viewmodel.PeopleViewModel
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
}

