package com.itrexgroup.photos.data.database.dao

import androidx.room.*
import com.itrexgroup.photos.data.database.entity.photos.PhotosPage
import io.reactivex.Single

@Dao
interface PagePhotosDao {


    @Query("SELECT * FROM PhotosPage WHERE page=:pageId")
    fun getPhotosByPage(pageId: Int): Single<PhotosPage>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(photo: PhotosPage): Long

    @Query("SELECT COUNT(page) FROM PhotosPage")
    fun getCountOfCachedPages(): Int

    @Query("DELETE FROM PhotosPage")
    fun deleteAll()

}