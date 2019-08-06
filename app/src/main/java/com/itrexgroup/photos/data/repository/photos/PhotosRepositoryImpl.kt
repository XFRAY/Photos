package com.itrexgroup.photos.data.repository.photos

import com.itrexgroup.photos.data.database.PhotosDatabase
import com.itrexgroup.photos.data.database.entity.photos.Photo
import com.itrexgroup.photos.data.database.entity.photos.PhotosPage
import com.itrexgroup.photos.data.database.entity.user.User
import com.itrexgroup.photos.data.network.ApiInterface
import io.reactivex.Single
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
                    return@onErrorResumeNext apiInterface.loadPhotos(page)
                            .doOnSuccess {
                                insertPhotosIntoDatabase(page, it)
                            }

                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

    }

    override fun refreshItems(): Single<List<Photo>> {
        val page = 1
        return apiInterface.loadPhotos(page)
                .doOnSuccess {
                    clearCachePhotos()
                    insertPhotosIntoDatabase(page, it)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

    }

    private fun clearCachePhotos() {
        photosDatabase.getPhotosDao().deleteAll()
    }

    private fun insertPhotosIntoDatabase(page: Int, listPhoto: List<Photo>) {
        photosDatabase.getPhotosDao().insert(PhotosPage(page, listPhoto))
    }

    override fun getUserByUsername(username: String): Single<User> {
        return apiInterface.getUserByUsername(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}