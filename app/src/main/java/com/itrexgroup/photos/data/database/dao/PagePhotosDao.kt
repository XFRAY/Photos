package com.itrexgroup.photos.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.itrexgroup.photos.data.database.entity.photos.Photo
import com.itrexgroup.photos.data.database.entity.photos.PhotosPage
import io.reactivex.Single

@Dao
interface PagePhotosDao {

    @Query("SELECT * FROM PhotosPage WHERE page=:pageId")
    fun getPhotosByPage(pageId: Int): LiveData<PhotosPage>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(photo: PhotosPage)

    @Delete
    fun delete(photo: PhotosPage)

}