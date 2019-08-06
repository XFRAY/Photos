package com.itrexgroup.photos.data.network

import com.itrexgroup.photos.BuildConfig
import com.itrexgroup.photos.data.database.entity.photos.Photo
import com.itrexgroup.photos.data.database.entity.user.User
import io.reactivex.Single
import retrofit2.http.*


interface ApiInterface {

    @GET("/photos")
    @Headers("Authorization: Client-ID " + BuildConfig.UNSPLASH_CLIENT_ID)
    fun loadPhotos(@Query("page") page: Int): Single<List<Photo>>

    @POST("/oauth/token")
    @Headers("Authorization: Client-ID " + BuildConfig.UNSPLASH_CLIENT_ID)
    fun login(
            @Query("client_id") clientId: String,
            @Query("client_secret") clientSecret: String,
            @Query("redirect_uri") redirectUri: String,
            @Query("code") code: String,
            @Query("grant_type") grantType: String
    ): Single<LoginResponse>

    @GET("/me")
    fun getCurrentUser(@Header("Authorization") authorizationHeader: String): Single<User>

    @GET("users/{username}")
    fun getUserByUsername(@Path("username") username: String): Single<User>

}