package com.itrexgroup.photos.data.database

import android.content.Context
import androidx.room.*
import com.itrexgroup.photos.data.database.dao.PagePhotosDao
import com.itrexgroup.photos.data.database.entity.photos.Photo
import com.itrexgroup.photos.data.database.entity.photos.PhotosPage

@Database(
    entities = [PhotosPage::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ListPhotosTypeConverter::class)
abstract class PhotosDatabase : RoomDatabase() {

    companion object {

        private const val NAME = "PHOTOS_DATABASE"

        fun createDatabase(context: Context): PhotosDatabase =
            Room.databaseBuilder(context, PhotosDatabase::class.java, NAME)
                .fallbackToDestructiveMigration()
                .build()

    }

    abstract fun getPhotosDao(): PagePhotosDao


}