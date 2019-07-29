package com.itrexgroup.photos.data.repository.photos

import com.itrexgroup.photos.data.database.entity.photos.Photo
import io.reactivex.Single

interface PhotosRepository {

    fun loadPhotos(page: Int): Single<List<Photo>>

}