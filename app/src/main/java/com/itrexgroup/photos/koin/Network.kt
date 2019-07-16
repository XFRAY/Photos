package com.itrexgroup.photos.koin

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

fun createRetrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl("https://api.unsplash.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()

fun createHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()
    .setLevel(HttpLoggingInterceptor.Level.BODY)

fun createHeaderInterceptor(): Interceptor {

    return Interceptor {
        val original = it.request()
        val builder = original.newBuilder()
        builder.apply {
            addHeader("Accept-Version", "v1")
            addHeader("Authorization", "Client-ID 5fe66adbcb966a5c1e813074b67f364731f8d07f752f9a95b0ef52761be59fe2")
        }
        return@Interceptor it.proceed(builder.build())
    }
}

fun createOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor, headerInterceptor: Interceptor): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(headerInterceptor)
        .build()