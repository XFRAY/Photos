package com.itrexgroup.photos.repository

import com.itrexgroup.photos.model.LoginResponse
import io.reactivex.Single

interface UserRepository {

    fun login(code: String): Single<LoginResponse>
}