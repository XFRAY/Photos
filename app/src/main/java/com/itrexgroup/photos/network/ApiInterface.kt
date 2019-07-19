package com.itrexgroup.photos.network

import com.itrexgroup.photos.model.LoginResponse
import com.itrexgroup.photos.entity.photos.Photo
import io.reactivex.Single
import retrofit2.http.*


interface ApiInterface {

    @GET("/photos")
    fun loadPhoto(@Query("page") page: Int): Single<List<Photo>>

   /* @POST("/oauth/token")
    fun login(
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("redirect_uri") redirectUri: String,
        @Query("code") code: String,
        @Query("grant_type") grantType: String
    ): Single<LoginResponse>*/

     @POST("/oauth/token")
     fun login(@Body loginRequest: LoginRequest): Single<LoginResponse>

}