package com.itrexgroup.photos.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itrexgroup.photos.data.database.entity.photos.Photo

@Dao
interface PhotosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(listPhotos: List<Photo>)

    @Query("SELECT * FROM Photo")
    fun getAllPhotos(): LiveData<List<Photo>>
}