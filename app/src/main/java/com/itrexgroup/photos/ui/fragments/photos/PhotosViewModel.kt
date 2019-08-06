package com.itrexgroup.photos.ui.fragments.photos

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.itrexgroup.photos.data.database.entity.photos.Photo
import com.itrexgroup.photos.data.network.NetworkState
import com.itrexgroup.photos.data.repository.photos.PhotosRepository
import com.itrexgroup.photos.ui.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable

class PhotosViewModel(
        application: Application,
        private val photosRepository: PhotosRepository
) : BaseViewModel(application) {

    private val compositeDisposable = CompositeDisposable()

    val listPhotosLiveData = MutableLiveData<List<Photo>>()
    val paginationNetworkStateLiveData = MutableLiveData<NetworkState>()
    val initialNetworkStateLiveData = MutableLiveData<NetworkState>()
    val refreshItemsLiveData = MutableLiveData<NetworkState>()


    private val listOfPhotos = ArrayList<Photo>()
    private var nextPage = 1

    fun loadFirstPagePhotos() {
        if (listOfPhotos.isNotEmpty()) {
            listPhotosLiveData.value = listOfPhotos
        }
        initialNetworkStateLiveData.value = NetworkState.LOADING
        compositeDisposable.add(
                photosRepository.loadPhotos(nextPage)
                        .subscribe({
                            listOfPhotos.addAll(it)
                            listPhotosLiveData.value = listOfPhotos
                            initialNetworkStateLiveData.value = NetworkState.LOADED
                            nextPage++
                        }, {
                            initialNetworkStateLiveData.value = NetworkState.FAILED
                        })
        )
    }

    fun loadNextPagePhotosIfExists() {
        if (paginationNetworkStateLiveData.value == NetworkState.LOADING) {
            return
        }
        paginationNetworkStateLiveData.value = NetworkState.LOADING
        compositeDisposable.add(
                photosRepository.loadPhotos(nextPage)
                        .subscribe({
                            listOfPhotos.addAll(it)
                            listPhotosLiveData.value = listOfPhotos
                            paginationNetworkStateLiveData.value = NetworkState.LOADED
                            nextPage++
                        }, {
                            paginationNetworkStateLiveData.value = NetworkState.FAILED
                        })
        )
    }

    fun refreshItems() {
        refreshItemsLiveData.value = NetworkState.LOADING
        compositeDisposable.add(
                photosRepository.refreshItems()
                        .subscribe({
                            nextPage = 2
                            listOfPhotos.clear()
                            listOfPhotos.addAll(it)
                            listPhotosLiveData.value = listOfPhotos
                            refreshItemsLiveData.value = NetworkState.LOADED
                        }, {
                            refreshItemsLiveData.value = NetworkState.FAILED
                        })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}