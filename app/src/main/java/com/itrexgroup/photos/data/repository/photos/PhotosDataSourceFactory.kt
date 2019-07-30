package com.itrexgroup.photos.data.repository.photos

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.itrexgroup.photos.data.database.entity.photos.Photo
import com.itrexgroup.photos.data.network.ApiInterface

class PhotosDataSourceFactory(private val photosDataSource: PhotosDataSource) : DataSource.Factory<Long, Photo>() {

    val photosDataSourceLiveData: MutableLiveData<PhotosDataSource> = MutableLiveData()

    override fun create(): DataSource<Long, Photo> {
        photosDataSourceLiveData.postValue(photosDataSource)
        return photosDataSource
    }

    fun getPhotosDataSource() = photosDataSource
}