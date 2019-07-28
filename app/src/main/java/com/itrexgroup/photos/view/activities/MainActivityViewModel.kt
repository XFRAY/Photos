package com.itrexgroup.photos.view.activities

import android.app.Application
import com.itrexgroup.photos.utils.PreferenceManager
import com.itrexgroup.photos.utils.SingleLiveEvent
import com.itrexgroup.photos.view.base.BaseViewModel

class MainActivityViewModel(application: Application, preferenceManager: PreferenceManager) :
    BaseViewModel(application) {

    val userLoggedLiveEvent = SingleLiveEvent<Boolean>()

    init {
        val token = preferenceManager.getAuthorizationToken()
        userLoggedLiveEvent.value = !token.isNullOrEmpty()
    }

}