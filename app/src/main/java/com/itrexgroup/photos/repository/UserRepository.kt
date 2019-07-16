package com.itrexgroup.photos.repository

interface UserRepository {

    fun login(email: String, password: String)
}