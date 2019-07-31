package com.itrexgroup.photos.ui.activities

import android.app.Application
import com.itrexgroup.photos.data.repository.PreferenceManager
import com.itrexgroup.photos.utils.SingleLiveEvent
import com.itrexgroup.photos.ui.base.BaseViewModel

class MainActivityViewModel(application: Application, preferenceManager: PreferenceManager) :
    BaseViewModel(application) {

    val userLoggedLiveEvent = SingleLiveEvent<Boolean>()

    init {
        val token = preferenceManager.getAuthorizationToken()
        userLoggedLiveEvent.value = !token.isNullOrEmpty()
    }

}