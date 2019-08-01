package com.itrexgroup.photos

import android.app.Application
import com.itrexgroup.photos.di.*
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(networkModule, viewModelModule, repositoryModule, utilsModule, databaseModule))
    }
}