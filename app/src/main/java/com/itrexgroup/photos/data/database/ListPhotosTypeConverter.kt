package com.itrexgroup.photos.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.itrexgroup.photos.data.database.entity.photos.Photo
import java.util.*


class ListPhotosTypeConverter {

    val gson = Gson()
    @TypeConverter
    fun stringListPhotos(data: String?): List<Photo> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<Photo>>() {
        }.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun listPhotosToString(someObjects: List<Photo>): String {
        return gson.toJson(someObjects)
    }
}