package com.itrexgroup.photos.data.repository.photos

import com.itrexgroup.photos.data.database.dao.PhotosDao
import com.itrexgroup.photos.data.network.ApiInterface
import com.itrexgroup.photos.data.database.entity.photos.Photo
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PhotosRepositoryImpl(
    private val apiInterface: ApiInterface,
    private val photosDao: PhotosDao
) : PhotosRepository {

    override fun loadPhotos(page: Int): Single<List<Photo>> {

        val listCachedPhotos = photosDao.getAllPhotos()

        return apiInterface.loadPhoto(page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSuccess {
                photosDao.insertAll(it)
            }
    }

}