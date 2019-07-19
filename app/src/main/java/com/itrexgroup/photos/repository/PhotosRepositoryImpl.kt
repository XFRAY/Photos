package com.itrexgroup.photos.repository

import com.itrexgroup.photos.network.ApiInterface
import com.itrexgroup.photos.entity.photos.Photo
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PhotosRepositoryImpl(private val apiInterface: ApiInterface) : PhotosRepository {

    override fun loadPhotos(page: Int): Single<List<Photo>> =
        apiInterface.loadPhoto(page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())

}