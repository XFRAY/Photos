package com.itrexgroup.photos.repository

import com.itrexgroup.photos.model.ApiInterface

class UserRepositoryImpl(private val apiInterface: ApiInterface): UserRepository {

    override fun login(code: String) {
        apiInterface.loadPhoto()
    }
}