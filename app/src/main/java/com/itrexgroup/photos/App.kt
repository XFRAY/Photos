package com.itrexgroup.photos

import android.app.Application
import com.itrexgroup.photos.di.networkModule
import com.itrexgroup.photos.di.repositoryModule
import com.itrexgroup.photos.di.utilsModule
import com.itrexgroup.photos.di.viewModelModule
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(networkModule, viewModelModule, repositoryModule, utilsModule))
    }
}