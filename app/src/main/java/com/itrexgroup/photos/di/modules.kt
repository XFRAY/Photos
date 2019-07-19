package com.itrexgroup.photos.di

import com.itrexgroup.photos.network.*
import com.itrexgroup.photos.repository.PhotosRepository
import com.itrexgroup.photos.repository.PhotosRepositoryImpl
import com.itrexgroup.photos.repository.UserRepository
import com.itrexgroup.photos.repository.UserRepositoryImpl
import com.itrexgroup.photos.vm.LoginViewModel
import com.itrexgroup.photos.vm.PhotosViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val networkModule = module {
    single { createOkHttpClient(get(), get()) }
    single { createHttpLoggingInterceptor() }
    single { createHeaderInterceptor() }
    single { createRetrofit(get()).create(ApiInterface::class.java) }

}

val viewModelModule = module {
    viewModel { PhotosViewModel(get(), get()) }
    viewModel { LoginViewModel(get(), get()) }
}

val repositoryModule = module {
    single<PhotosRepository> { PhotosRepositoryImpl(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
}