package com.itrexgroup.photos.repository

import com.itrexgroup.photos.model.Photo
import io.reactivex.Single

interface PhotosRepository {

    fun loadPhotos(page: Int): Single<List<Photo>>

}