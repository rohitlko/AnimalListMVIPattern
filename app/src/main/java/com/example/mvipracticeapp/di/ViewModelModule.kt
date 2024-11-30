package com.example.mvipracticeapp.di

import com.example.mvipracticeapp.viewmodel.AnimalViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AnimalViewModel(get()) }
}
