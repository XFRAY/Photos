package com.itrexgroup.photos.view.fragments.login

import android.app.Application
import com.itrexgroup.photos.utils.SingleLiveEvent
import com.itrexgroup.photos.repository.user.UserRepository
import com.itrexgroup.photos.utils.PreferenceManager
import com.itrexgroup.photos.view.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable

class LoginViewModel(
        application: Application,
        private val repository: UserRepository,
        private val preferenceManager: PreferenceManager
) : BaseViewModel(application) {

    private val disposable = CompositeDisposable()

    val errorLiveEvent = SingleLiveEvent<Unit>()
    val userLoggedLiveEvent = SingleLiveEvent<Unit>()

    private fun login(code: String) {
        disposable.add(
            repository.login(code)
                .subscribe({
                    preferenceManager.setAuthorizationToken("${it.tokeType} ${it.accessToken}")
                    userLoggedLiveEvent.call()
                }, {
                    errorLiveEvent.call()
                })
        )
    }

    fun handleCodeFromUrlAndLogin(url: String) {
        val startIndex = url.lastIndexOf("=") + 1
        val endIndex = url.length
        val code = url.substring(startIndex, endIndex)
        login(code)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }


}