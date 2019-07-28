package com.itrexgroup.photos.repository.user

import com.itrexgroup.photos.BuildConfig
import com.itrexgroup.photos.entity.user.User
import com.itrexgroup.photos.network.ApiInterface
import com.itrexgroup.photos.network.LoginResponse
import com.itrexgroup.photos.network.LoginApiInterface
import com.itrexgroup.photos.repository.user.UserRepository
import com.itrexgroup.photos.utils.PreferenceManager
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserRepositoryImpl(private val apiInterface: ApiInterface,
                         private val loginApiInterface: LoginApiInterface,
                         private val preferenceManager: PreferenceManager) : UserRepository {

    override fun login(code: String): Single<LoginResponse> {
        return loginApiInterface.login(
                BuildConfig.UNSPLASH_CLIENT_ID,
                BuildConfig.UNSPLASH_CLIENT_SECERET,
                BuildConfig.UNSPLASH_REDIRECT_URI,
                code, "authorization_code"
        ).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    override fun getCurrentUser(): Single<User> =
            apiInterface.getCurrentUser(preferenceManager.getAuthorizationToken()!!)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())

}