package com.itrexgroup.photos.ui.fragments.photos

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.itrexgroup.photos.data.database.entity.photos.Photo
import com.itrexgroup.photos.data.repository.photos.PhotosDataSource
import com.itrexgroup.photos.data.repository.photos.PhotosDataSourceFactory
import com.itrexgroup.photos.ui.base.BaseViewModel
import java.util.concurrent.Executors

class PhotosViewModel(
    application: Application,
    private val photosDataSourceFactory: PhotosDataSourceFactory
) : BaseViewModel(application) {

    val listPhotosLiveData: LiveData<PagedList<Photo>>

    private val dataSource = photosDataSourceFactory.getPhotosDataSource()

    val initialLoadStateLiveData = dataSource.initialLoadStateLiveData
    val paginationLoadStateLiveData = dataSource.paginatedNetworkStateLiveData

    init {

        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(10)
            .setPageSize(10)
            .build()

        listPhotosLiveData = LivePagedListBuilder(photosDataSourceFactory, pagedListConfig)
            .build()
    }

    override fun onCleared() {
        super.onCleared()

    }
}