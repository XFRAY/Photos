package com.itrexgroup.photos.data.network

import io.reactivex.Single
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginApiInterface {

    @POST("/oauth/token")
    fun login(
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("redirect_uri") redirectUri: String,
        @Query("code") code: String,
        @Query("grant_type") grantType: String
    ): Single<LoginResponse>

}