package com.example.mvipracticeapp.di

import com.example.mvipracticeapp.api.AnimalApi
import com.example.mvipracticeapp.api.AnimalService
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {
//    single { AnimalService.getAnimalServiceRetrofit() }
//    single { get<Retrofit>().create(AnimalApi::class.java) }

    single { get<Retrofit>().create(AnimalApi::class.java) }
    single { AnimalService.getRetrofit() }
}