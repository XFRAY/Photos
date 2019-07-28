package com.itrexgroup.photos.utils

import android.content.Context

class PreferenceManager(context: Context) {

    companion object {
        private const val SHARED_PREFERENCE_NAME = "SHARED_PREFERENCE_NAME"
        private const val AUTHORIZATION_TOKEN_KEY = "AUTHORIZATION_TOKEN_KEY"
    }

    private val preferenceManager = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun setAuthorizationToken(token: String) {
        preferenceManager.edit().putString(AUTHORIZATION_TOKEN_KEY, token).apply()
    }

    fun getAuthorizationToken() = preferenceManager.getString(AUTHORIZATION_TOKEN_KEY, "")
}