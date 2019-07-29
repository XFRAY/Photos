package com.itrexgroup.photos.data.repository.photos

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.itrexgroup.photos.data.database.dao.PhotosDao
import com.itrexgroup.photos.data.database.entity.Listing
import com.itrexgroup.photos.data.network.ApiInterface
import com.itrexgroup.photos.data.database.entity.photos.Photo
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PhotosRepositoryImpl(
    private val apiInterface: ApiInterface,
    private val photosDao: PhotosDao
) : PhotosRepository {

    override fun loadPhotos(listOfPhotosLiveData: MutableLiveData<Photo>): Listing<Photo> {

        val boundaryCallback = PhotosBoundaryCallback(
            apiInterface = apiInterface
             ,
            handleResponse = this::insertResultIntoDb,
            ioExecutor = ioExecutor,
            networkPageSize = pageSize)
        // we are using a mutable live data to trigger refresh requests which eventually calls
        // refresh method and gets a new live data. Each refresh request by the user becomes a newly
        // dispatched data in refreshTrigger
        val refreshTrigger = MutableLiveData<Unit>()
        val refreshState = Transformations.switchMap(refreshTrigger) {
            refresh(subReddit)
        }
        // We use toLiveData Kotlin extension function here, you could also use LivePagedListBuilder
        val livePagedList = LivePagedListBuilder(db.posts().postsBySubreddit(subReddit), pageSize)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return Listing(
            pagedList = livePagedList,
            networkState = boundaryCallback.networkState,
            retry = {
                boundaryCallback.helper.retryAllFailed()
            },
            refresh = {
                refreshTrigger.value = null
            },
            refreshState = refreshState
        )
    }


}