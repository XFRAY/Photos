package com.itrexgroup.photos.repository.user

import com.itrexgroup.photos.entity.user.User
import com.itrexgroup.photos.network.LoginResponse
import io.reactivex.Single

interface UserRepository {

    fun login(code: String): Single<LoginResponse>

    fun getCurrentUser(): Single<User>
}