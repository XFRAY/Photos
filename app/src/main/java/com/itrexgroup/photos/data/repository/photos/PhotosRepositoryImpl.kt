package com.itrexgroup.photos.data.repository.photos

import com.itrexgroup.photos.data.database.PhotosDatabase
import com.itrexgroup.photos.data.database.entity.photos.Photo
import com.itrexgroup.photos.data.database.entity.photos.PhotosPage
import com.itrexgroup.photos.data.network.ApiInterface
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.operators.single.SingleJust
import io.reactivex.schedulers.Schedulers

class PhotosRepositoryImpl(
    private val apiInterface: ApiInterface,
    private val photosDatabase: PhotosDatabase
) : PhotosRepository {

    override fun loadPhotos(page: Int): Single<List<Photo>> {

        val photosDao = photosDatabase.getPhotosDao()
        val cachedPageLiveData = photosDao.getPhotosByPage(page)
        return if (cachedPageLiveData.value == null) {
            apiInterface.loadPhotos(page)
                .doOnSuccess {
                    insertIntoDatabase(PhotosPage(page, it))
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        } else {
            Single.just(cachedPageLiveData.value!!.listPhotos)
        }
    }

    private fun insertIntoDatabase(photosPage: PhotosPage) {
        photosDatabase.getPhotosDao().insert(photosPage)
    }

}