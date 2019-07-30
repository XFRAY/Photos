package com.itrexgroup.photos.data.repository.photos

import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import com.itrexgroup.photos.data.database.entity.photos.Photo
import com.itrexgroup.photos.data.network.ApiInterface
import com.itrexgroup.photos.data.network.NetworkState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class PhotosDataSource(private val apiInterface: ApiInterface) : ItemKeyedDataSource<Long, Photo>() {

    val initialLoadStateLiveData = MutableLiveData<NetworkState>()
    val paginatedNetworkStateLiveData = MutableLiveData<NetworkState>()
    private val compositeDisposable = CompositeDisposable()
    private var pageNumber = 1L
    private var params: LoadParams<Long>? = null
    private var callback: LoadCallback<Photo>? = null

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Photo>) {
        initialLoadStateLiveData.postValue(NetworkState.LOADING)
        val disposable = apiInterface.loadPhotos(pageNumber)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                callback.onResult(it)
                initialLoadStateLiveData.postValue(NetworkState.LOADED)
                pageNumber++
            }, {
                initialLoadStateLiveData.postValue(NetworkState(NetworkState.Status.FAILED, it.message))
            })
        compositeDisposable.add(disposable)
    }

    override fun getKey(item: Photo) = pageNumber

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Photo>) {

    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Photo>) {
        this.params = params
        this.callback = callback
        paginatedNetworkStateLiveData.postValue(NetworkState.LOADING)
        val disposable = apiInterface.loadPhotos(page = params.key)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                callback.onResult(it)
                paginatedNetworkStateLiveData.postValue(NetworkState.LOADED)
                pageNumber++
            }, {
                paginatedNetworkStateLiveData.postValue(NetworkState(NetworkState.Status.FAILED, it.message))
            })
        compositeDisposable.add(disposable)
    }

    fun retryPagination() {
        if (params != null && callback != null) {
            loadAfter(params!!, callback!!)
        }
    }

    fun clear() {
        compositeDisposable.clear()
        pageNumber = 1
    }

}