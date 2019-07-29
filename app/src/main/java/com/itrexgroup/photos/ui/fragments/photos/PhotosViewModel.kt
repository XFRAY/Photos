package com.itrexgroup.photos.ui.fragments.photos

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.itrexgroup.photos.data.database.entity.photos.Photo
import com.itrexgroup.photos.data.repository.photos.PhotosRepository
import com.itrexgroup.photos.ui.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable

class PhotosViewModel(
    application: Application,
    private val photosRepository: PhotosRepository
) : BaseViewModel(application) {

    private val compositeDisposable = CompositeDisposable()

    val listPhotosLiveData = MutableLiveData<PagedList<Photo>>()
    val progressLiveData = MutableLiveData<Boolean>()

    fun loadFirstPagePhotos() {

        progressLiveData.value = true
        compositeDisposable.add(
            photosRepository.loadPhotos(listPhotosLiveData)
                .subscribe({

                    progressLiveData.value = false
                }, {
                    progressLiveData.value = false
                })
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}