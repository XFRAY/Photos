package com.itrexgroup.photos.repository.photos

import com.itrexgroup.photos.entity.photos.Photo
import io.reactivex.Single

interface PhotosRepository {

    fun loadPhotos(page: Int): Single<List<Photo>>

}