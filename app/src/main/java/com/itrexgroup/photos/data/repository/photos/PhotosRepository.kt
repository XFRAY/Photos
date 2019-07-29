package com.itrexgroup.photos.data.repository.photos

import androidx.lifecycle.MutableLiveData
import com.itrexgroup.photos.data.database.entity.Listing
import com.itrexgroup.photos.data.database.entity.photos.Photo

interface PhotosRepository {

    fun loadPhotos(photosLiveData: MutableLiveData<Photo>): Listing<Photo>

}