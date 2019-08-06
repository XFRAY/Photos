package com.itrexgroup.photos.ui.fragments.photos

import android.os.Bundle
import android.view.View
import com.itrexgroup.photos.R
import com.itrexgroup.photos.ui.base.BaseFlowFragment

class PhotosFlowFragment : BaseFlowFragment() {

    companion object {
        const val TAG = "PHOTOS_FLOW_FRAGMENT"
        fun newInstance() = PhotosFlowFragment()
    }

    override fun getLayoutResourceId() = R.layout.fragment_base_flow

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            navigateTo(PhotosFragment.newInstance(), PhotosFragment.TAG, false, null)
        }
    }
}