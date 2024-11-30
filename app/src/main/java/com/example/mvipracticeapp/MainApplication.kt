package com.example.mvipracticeapp

import android.app.Application
import com.example.mvipracticeapp.di.networkModule
import com.example.mvipracticeapp.di.repositoryModule
import com.example.mvipracticeapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(networkModule, repositoryModule, viewModelModule)
        }
    }
}