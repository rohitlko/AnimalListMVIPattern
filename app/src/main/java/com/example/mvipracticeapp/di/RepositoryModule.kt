package com.example.mvipracticeapp.di

import com.example.mvipracticeapp.repo.AnimalRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { AnimalRepository( get())}
}