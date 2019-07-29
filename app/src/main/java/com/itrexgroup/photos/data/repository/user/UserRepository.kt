package com.itrexgroup.photos.data.repository.user

import com.itrexgroup.photos.data.database.entity.user.User
import com.itrexgroup.photos.data.network.LoginResponse
import io.reactivex.Single

interface UserRepository {

    fun login(code: String): Single<LoginResponse>

    fun getCurrentUser(): Single<User>
}