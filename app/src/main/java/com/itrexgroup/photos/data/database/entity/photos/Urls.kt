package com.itrexgroup.photos.data.database.entity.photos

import androidx.room.Entity
import androidx.room.PrimaryKey


data class Urls(

    val raw: String,
    val full: String,
    val regular: String,
    val thumb: String
)