package com.itrexgroup.photos.view.fragments

import android.os.Bundle
import android.view.View
import com.itrexgroup.photos.R
import com.itrexgroup.photos.view.fragments.base.BaseFragment

class ProfileFragment : BaseFragment() {

    companion object {
        const val TAG = "PROFILE_FRAGMENT_TAG"
        fun newInstance() = ProfileFragment()
    }

    override fun getLayoutResourceId() = R.layout.fragment_profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}