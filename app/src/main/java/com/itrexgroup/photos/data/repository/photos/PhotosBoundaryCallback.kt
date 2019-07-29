package com.itrexgroup.photos.data.repository.photos

import androidx.annotation.MainThread
import androidx.paging.PagedList
import com.itrexgroup.photos.data.database.entity.photos.Photo
import com.itrexgroup.photos.data.network.ApiInterface
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.util.concurrent.Executor

class PhotosBoundaryCallback(
    private val apiInterface: ApiInterface,
    private val handleResponse: (String, Single<Photo>) -> Unit,
    private val ioExecutor: Executor,
    private val networkPageSize: Int
) : PagedList.BoundaryCallback<Photo>() {

    val helper = PagingRequestHelper(ioExecutor)
    val networkState = helper.createStatusLiveData()

    /**
     * Database returned 0 items. We should query the backend for more items.
     */


    @MainThread
    override fun onZeroItemsLoaded() {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) {
            apiInterface.loadPhotos(networkPageSize)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(createSingleObserver(it))
        }
    }

    /**
     * User reached to the end of the list.
     */
    @MainThread
    override fun onItemAtEndLoaded(itemAtEnd: Photo) {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) {
            webservice.getTopAfter(
                subreddit = subredditName,
                after = itemAtEnd.name,
                limit = networkPageSize
            )
                .enqueue(createWebserviceCallback(it))
        }
    }

    /**
     * every time it gets new items, boundary callback simply inserts them into the database and
     * paging library takes care of refreshing the list if necessary.
     */
    private fun insertItemsIntoDb(
        listPhotos: List<Photo>,
        it: PagingRequestHelper.Request.Callback
    ) {
        ioExecutor.execute {
            handleResponse(subredditName, response.body())
            it.recordSuccess()
        }
    }

    override fun onItemAtFrontLoaded(itemAtFront: Photo) {
        // ignored, since we only ever append to what's in the DB
    }

    private fun createSingleObserver(callback: PagingRequestHelper.Request.Callback): SingleObserver<List<Photo>> {
        return object : SingleObserver<List<Photo>> {
            override fun onSuccess(listPhoto: List<Photo>) {
                insertItemsIntoDb(listPhoto,callback)
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onError(e: Throwable) {
                callback.recordFailure(e)
            }

        }
    }
}