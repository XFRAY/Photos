package com.itrexgroup.photos.data.repository.photos

import com.itrexgroup.photos.data.database.PhotosDatabase
import com.itrexgroup.photos.data.database.entity.photos.Photo
import com.itrexgroup.photos.data.database.entity.photos.PhotosPage
import com.itrexgroup.photos.data.network.ApiInterface
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PhotosRepositoryImpl(
    private val apiInterface: ApiInterface,
    private val photosDatabase: PhotosDatabase
) : PhotosRepository {

    override fun loadPhotos(page: Int): Single<List<Photo>> {
        val photosDao = photosDatabase.getPhotosDao()
        return photosDao.getPhotosByPage(page)
            .flatMap { photosPage ->
                return@flatMap Single.just(photosPage.listPhotos)
            }.onErrorResumeNext {
                return@onErrorResumeNext

            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

    private fun insertIntoDatabase(photosPage: PhotosPage) {
        photosDatabase.getPhotosDao().insert(photosPage)
    }

}