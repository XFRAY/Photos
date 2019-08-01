package com.itrexgroup.photos.data.database.entity.photos

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Photo(
    val id: String,
    @SerializedName("created_at")
    val createdAt: String,
    val color: String,
    val likes: Int,
    @Embedded
    val urls: Urls,
    @Embedded
    @SerializedName("user")
    val author: Author
)
