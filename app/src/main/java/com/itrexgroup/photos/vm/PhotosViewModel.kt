package com.itrexgroup.photos.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.itrexgroup.photos.entity.photos.Photo
import com.itrexgroup.photos.repository.PhotosRepository
import com.itrexgroup.photos.vm.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable

class PhotosViewModel(
    application: Application,
    private val photosRepository: PhotosRepository
) : BaseViewModel(application) {

    private val compositeDisposable = CompositeDisposable()

    val listPhotosLiveData = MutableLiveData<List<Photo>>()
    val progressLiveData = MutableLiveData<Boolean>()

    private val listOfPhotos = ArrayList<Photo>()
    private var currentPage = 1
    private var isLoad = false

    fun loadFirstPagePhotos() {
        if (isLoad) {
            return
        }
        if (listOfPhotos.isNotEmpty()) {
            listPhotosLiveData.value = listOfPhotos
        }
        progressLiveData.value = true
        compositeDisposable.add(
            photosRepository.loadPhotos(currentPage)
                .subscribe({
                    listOfPhotos.addAll(it)
                    listPhotosLiveData.value = listOfPhotos
                    progressLiveData.value = false
                }, {
                    progressLiveData.value = false
                })
        )
    }

    fun loadNextPagePhotosIfExists() {
        if (isLoad) {
            return
        }
        currentPage++
        compositeDisposable.add(
            photosRepository.loadPhotos(currentPage)
                .subscribe({
                    listOfPhotos.addAll(it)
                    listPhotosLiveData.value = listOfPhotos
                }, {

                })
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}