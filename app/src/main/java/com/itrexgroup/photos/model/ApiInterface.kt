package com.itrexgroup.photos.model

import io.reactivex.Single
import retrofit2.http.GET


interface ApiInterface {

    @GET("/photos")
    fun loadPhoto(): Single<List<Photo>>

}