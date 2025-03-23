package com.ospaltic.mydebts.di

import com.ospaltic.mydebts.data.local.AppDatabase
import com.ospaltic.mydebts.repository.PeopleRepository
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

}

