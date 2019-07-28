package com.itrexgroup.photos.view.fragments

import android.os.Bundle
import android.view.View
import com.itrexgroup.photos.R
import com.itrexgroup.photos.view.base.BaseFragment

class LikedPhotosFragment : BaseFragment() {

    companion object {
        const val TAG = "LIKED_PHOTOS_FRAGMENT_TAG"
        fun newInstance() = LikedPhotosFragment()
    }

    override fun getLayoutResourceId() = R.layout.fragment_liked_photos

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}