package com.itrexgroup.photos.data.repository.photos

import com.itrexgroup.photos.data.database.entity.photos.Photo
import com.itrexgroup.photos.data.database.entity.user.User
import io.reactivex.Single

interface PhotosRepository {

    fun loadPhotos(page: Int): Single<List<Photo>>
    fun refreshItems(): Single<List<Photo>>
    fun getUserByUsername(username: String): Single<User>
}