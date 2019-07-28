package com.itrexgroup.photos.network

import com.itrexgroup.photos.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

fun createRetrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl(BuildConfig.UNSPLASH_API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()

fun createRetrofitForLogin(okHttpLoggingInterceptor: HttpLoggingInterceptor): Retrofit =
    Retrofit.Builder()
        .baseUrl(BuildConfig.UNSPLASH_LOGIN_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addInterceptor(okHttpLoggingInterceptor)
                .build()
        )
        .build()


fun createHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()
    .setLevel(HttpLoggingInterceptor.Level.BODY)

fun createHeaderInterceptor(): Interceptor {

    return Interceptor {
        val original = it.request()
        val builder = original.newBuilder()
        builder.apply {
            addHeader("Accept-Version", "v1")
        }
        return@Interceptor it.proceed(builder.build())
    }
}

fun createOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor, headerInterceptor: Interceptor): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(headerInterceptor)
        .build()