package com.itrexgroup.photos.view.fragments

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.itrexgroup.photos.R
import com.itrexgroup.photos.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_photo_details.*

class PhotoDetailsFragment : BaseFragment() {

    companion object {
        private const val URL_KEY = "URL_KEY"
        private const val PHOTO_TRANSITION_NAME_KEY = "PHOTO_TRANSITION_NAME_KEY"
        const val TAG = "PHOTO_DETAIL_FRAGMENT_TAG"

        fun newInstance(url: String, photoTransitionName: String) = PhotoDetailsFragment().apply {
            arguments = Bundle().apply {
                putString(URL_KEY, url)
                putString(PHOTO_TRANSITION_NAME_KEY, photoTransitionName)
            }
        }
    }

    override fun getLayoutResourceId() = R.layout.fragment_photo_details

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imgPhoto.transitionName = arguments?.getString(PHOTO_TRANSITION_NAME_KEY)
        Glide.with(context!!)
                .load(arguments?.getString(URL_KEY))
                .into(imgPhoto)
    }

}