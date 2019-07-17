package com.itrexgroup.photos.repository

interface UserRepository {

    fun login(code: String)
}