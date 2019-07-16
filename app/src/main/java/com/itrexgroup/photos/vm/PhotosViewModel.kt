package com.itrexgroup.photos.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.itrexgroup.photos.model.Photo
import com.itrexgroup.photos.repository.PhotosRepository
import com.itrexgroup.photos.vm.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable

class PhotosViewModel(
    application: Application,
    private val photosRepository: PhotosRepository
) : BaseViewModel(application) {

    private val compositeDisposable = CompositeDisposable()

    var recyclerViewPosition = 0

    val listPhotosLiveData = MutableLiveData<List<Photo>>()
    val progressLiveData = MutableLiveData<Boolean>()

    fun loadPhotos() {
        if (listPhotosLiveData.value != null) {
            return
        }
        progressLiveData.value = true
        compositeDisposable.add(
            photosRepository.loadPhotos()
                .subscribe({
                    listPhotosLiveData.value = it
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