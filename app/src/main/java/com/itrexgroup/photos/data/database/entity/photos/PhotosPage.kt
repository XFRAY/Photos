package com.itrexgroup.photos.data.database.entity.photos

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.itrexgroup.photos.data.database.ListPhotosTypeConverter

@Entity
data class PhotosPage(
    @PrimaryKey
    val page: Int,
    @TypeConverters(ListPhotosTypeConverter::class)
    val listPhotos: List<Photo>
)