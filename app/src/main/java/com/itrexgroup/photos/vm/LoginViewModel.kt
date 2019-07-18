package com.itrexgroup.photos.vm

import android.app.Application
import com.itrexgroup.photos.utils.SingleLiveEvent
import com.itrexgroup.photos.repository.UserRepository
import com.itrexgroup.photos.vm.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable

class LoginViewModel(
    application: Application,
    private val repository: UserRepository
) : BaseViewModel(application) {

    private val disposable = CompositeDisposable()

    val errorLiveData = SingleLiveEvent<Unit>()

    private fun login(code: String) {
        disposable.add(
            repository.login(code)
                .subscribe({
                    if (it != null) {

                    }
                }, {
                    if (it != null) {

                    }
                })
        )
    }

    fun handleCodeFromUrlAndLogin(url: String?) {
        url?.let {
            if (url.contains("code=")) {
                val startIndex = url.lastIndexOf("=") + 1
                val endIndex = url.length
                val code = url.substring(startIndex, endIndex)
                login(code)
            } else {
                errorLiveData.call()
            }
        } ?: errorLiveData.call()
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }


}