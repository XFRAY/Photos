package com.itrexgroup.photos.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.itrexgroup.photos.data.database.dao.PhotosDao
import com.itrexgroup.photos.data.database.entity.photos.Photo


@Database(
    entities = [Photo::class],
    version = 1,
    exportSchema = false
)
//@TypeConverters(HealtherDatabaseConverters::class)
abstract class PhotosDatabase: RoomDatabase() {

    companion object {

        private const val NAME = "PHOTOS_DATABASE"

        fun createDatabase(context: Context): PhotosDatabase =
            Room.databaseBuilder(context, PhotosDatabase::class.java, NAME)
                .fallbackToDestructiveMigration()
                .build()

    }

    abstract fun getPhotoDao(): PhotosDao

}