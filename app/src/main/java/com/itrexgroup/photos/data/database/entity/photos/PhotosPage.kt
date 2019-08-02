package com.itrexgroup.photos.data.database.entity.photos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PhotosPage(
    @PrimaryKey
    val page: Int,
    val listPhotos: List<Photo>
)