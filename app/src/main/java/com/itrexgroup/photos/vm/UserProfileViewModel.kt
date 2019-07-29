package com.itrexgroup.photos.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.itrexgroup.photos.data.database.entity.user.User
import com.itrexgroup.photos.data.repository.user.UserRepository
import com.itrexgroup.photos.ui.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable

class UserProfileViewModel(application: Application,
                           private val userRepository: UserRepository) : BaseViewModel(application) {

    private val compositeDisposable = CompositeDisposable()

    val userLiveData = MutableLiveData<User>()

    fun getCurrentUser() {
        if (userLiveData.value != null) {
            return
        }
        compositeDisposable.add(userRepository.getCurrentUser()
                .subscribe({
                    userLiveData.value = it
                }, {

                }))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}