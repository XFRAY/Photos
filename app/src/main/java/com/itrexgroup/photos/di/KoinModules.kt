package com.itrexgroup.photos.di

import com.itrexgroup.photos.data.database.PhotosDatabase
import com.itrexgroup.photos.data.network.*
import com.itrexgroup.photos.data.repository.photos.PhotosRepository
import com.itrexgroup.photos.data.repository.photos.PhotosRepositoryImpl
import com.itrexgroup.photos.data.repository.user.UserRepository
import com.itrexgroup.photos.data.repository.user.UserRepositoryImpl
import com.itrexgroup.photos.data.repository.PreferenceManager
import com.itrexgroup.photos.ui.activities.MainActivityViewModel
import com.itrexgroup.photos.ui.fragments.login.LoginViewModel
import com.itrexgroup.photos.ui.fragments.photos.PhotosViewModel
import com.itrexgroup.photos.vm.*
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val networkModule = module {
    single { createOkHttpClient(get(), get()) }
    single { createHttpLoggingInterceptor() }
    single { createHeaderInterceptor() }
    single { createRetrofit(get()).create(ApiInterface::class.java) }
    single { createRetrofitForLogin(get()).create(LoginApiInterface::class.java) }

}

val viewModelModule = module {
    viewModel { PhotosViewModel(get(), get()) }
    viewModel { LoginViewModel(get(), get(), get()) }
    viewModel { MainFlowViewModel(get()) }
    viewModel { MainActivityViewModel(get(), get()) }
    viewModel { UserProfileViewModel(get(), get()) }
}

val repositoryModule = module {
    single<PhotosRepository> { PhotosRepositoryImpl(get(), get()) }
    single<UserRepository> { UserRepositoryImpl(get(), get(), get()) }
}

val utilsModule = module {
    single { PreferenceManager(get()) }
}

val databaseModule = module {
    single { PhotosDatabase.createDatabase(get()) }
}