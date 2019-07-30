package com.itrexgroup.photos.data.database.entity.photos

import com.google.gson.annotations.SerializedName


data class Photo(
        val id: String,
        @SerializedName("created_at")
        val createdAt: String,
        val color: String,
        val likes: Int,
        val urls: Urls,
        @SerializedName("user")
        val author: Author
)
