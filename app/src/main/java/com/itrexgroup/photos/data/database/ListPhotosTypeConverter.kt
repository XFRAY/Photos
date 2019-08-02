package com.itrexgroup.photos.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.itrexgroup.photos.data.database.entity.photos.Photo
import java.util.*


class ListPhotosTypeConverter {


    @TypeConverter
    fun stringToListPhotos(data: String?): List<Photo> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<ArrayList<Photo>>() {

        }.type
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun listPhotosToString(listPhotos: List<Photo>): String {
        return Gson().toJson(listPhotos)
    }
}