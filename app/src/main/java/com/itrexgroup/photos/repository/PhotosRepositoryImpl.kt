package com.itrexgroup.photos.repository

import com.itrexgroup.photos.model.ApiInterface
import com.itrexgroup.photos.model.Photo
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PhotosRepositoryImpl(private val apiInterface: ApiInterface) : PhotosRepository {

    override fun loadPhotos(): Single<List<Photo>> =
        apiInterface.loadPhoto()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())

}