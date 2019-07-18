package com.itrexgroup.photos.repository

import com.itrexgroup.photos.BuildConfig
import com.itrexgroup.photos.network.ApiInterface
import com.itrexgroup.photos.network.LoginRequest
import com.itrexgroup.photos.model.LoginResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserRepositoryImpl(private val apiInterface: ApiInterface) : UserRepository {

    override fun login(code: String): Single<LoginResponse> {
        val loginRequest = LoginRequest(
            BuildConfig.UNSPLASH_CLIENT_ID,
            BuildConfig.UNSPLASH_CLIENT_SECERET,
            BuildConfig.UNSPLASH_REDIRECT_URI,
            code, code
        )
        return apiInterface.login(loginRequest).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}