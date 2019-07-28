package com.itrexgroup.photos.entity.photos

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Photo(
        @PrimaryKey
        val id: String,
        @SerializedName("created_at")
        val createdAt: String,
        val color: String,
        val likes: Int,
        val urls: Urls,
        @SerializedName("user")
        val author: Author
)
