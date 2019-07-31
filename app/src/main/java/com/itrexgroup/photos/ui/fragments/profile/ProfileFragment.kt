package com.itrexgroup.photos.ui.fragments.profile

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.itrexgroup.photos.R
import com.itrexgroup.photos.data.database.entity.user.User
import com.itrexgroup.photos.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment() {

    companion object {
        const val TAG = "PROFILE_FRAGMENT_TAG"
        fun newInstance() = ProfileFragment()
    }

    private val viewModel: UserProfileViewModel by viewModel()

    override fun getLayoutResourceId() = R.layout.fragment_profile

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCurrentUser()
    }

    private fun observeViewModel() {
        viewModel.userLiveData.observe(this, Observer {
            initUser(it)
        })
    }

    private fun initUser(user: User) {
        txtUsername.text = user.name
    }

}