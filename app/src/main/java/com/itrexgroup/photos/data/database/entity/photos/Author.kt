package com.itrexgroup.photos.data.database.entity.photos

import androidx.room.Embedded
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class Author(
    @PrimaryKey
    val name: String,
    @Embedded
    @SerializedName("profile_image")
    val profileImage: ProfileImage
)