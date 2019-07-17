package com.itrexgroup.photos.vm.base

import android.app.Application
import com.itrexgroup.photos.repository.UserRepository

class LoginViewModel(application: Application,
                     private val repository: UserRepository) : BaseViewModel(application) {

    fun login(code: String) {
        repository.login(code)
    }

}