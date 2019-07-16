package com.itrexgroup.photos.koin

import com.itrexgroup.photos.model.ApiInterface
import com.itrexgroup.photos.repository.PhotosRepository
import com.itrexgroup.photos.repository.PhotosRepositoryImpl
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
}

val repositoryModule = module {
    single { PhotosRepositoryImpl(get()) as PhotosRepository }
}