package com.itrexgroup.photos.di

import com.itrexgroup.photos.network.*
import com.itrexgroup.photos.repository.photos.PhotosRepository
import com.itrexgroup.photos.repository.photos.PhotosRepositoryImpl
import com.itrexgroup.photos.repository.user.UserRepository
import com.itrexgroup.photos.repository.user.UserRepositoryImpl
import com.itrexgroup.photos.utils.PreferenceManager
import com.itrexgroup.photos.view.activities.MainActivityViewModel
import com.itrexgroup.photos.view.fragments.login.LoginViewModel
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
    single<PhotosRepository> { PhotosRepositoryImpl(get()) }
    single<UserRepository> { UserRepositoryImpl(get(), get(), get()) }
}

val utilsModule = module {
    single { PreferenceManager(get()) }
}