package com.itrexgroup.photos.ui.fragments.main

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import com.itrexgroup.photos.R
import com.itrexgroup.photos.ui.base.BaseFragment
import com.itrexgroup.photos.ui.base.OnBackPressed
import com.itrexgroup.photos.ui.fragments.LikedPhotosFragment
import com.itrexgroup.photos.ui.fragments.photos.PhotosFragment
import com.itrexgroup.photos.ui.fragments.profile.ProfileFragment
import kotlinx.android.synthetic.main.fragment_main_flow.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFlowFragment : BaseFragment(), OnBackPressed {

    private val viewModel: MainFlowViewModel by viewModel()

    companion object {
        const val TAG = "MAIN_FLOW_FRAGMENT_TAG"
        fun newInstance() = MainFlowFragment()
    }

    override fun getLayoutResourceId() = R.layout.fragment_main_flow

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.activeFragmentTag = PhotosFragment.TAG
        }
        setupBottomNavigationView()
    }

    private fun setupBottomNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener {
            val fragment: BaseFragment
            val tag: String
            when (it.itemId) {
                R.id.photos -> {
                    fragment = PhotosFragment.newInstance()
                    tag = PhotosFragment.TAG
                }

                R.id.likedPhotos -> {
                    fragment = LikedPhotosFragment.newInstance()
                    tag = LikedPhotosFragment.TAG
                }

                R.id.profile -> {
                    fragment = ProfileFragment.newInstance()
                    tag = ProfileFragment.TAG
                }
                else -> {
                    fragment = PhotosFragment.newInstance()
                    tag = PhotosFragment.TAG
                }
            }
            replaceFragment(fragment, tag, it.itemId)
            return@setOnNavigationItemSelectedListener true
        }
        if (viewModel.isListItemsEmpty()) {
            bottomNavigationView.selectedItemId = R.id.photos
        }

        bottomNavigationView.setOnNavigationItemReselectedListener {

        }

    }

    private fun replaceFragment(fragment: BaseFragment, tag: String, @IdRes itemId: Int) {
        val fragmentTransition = childFragmentManager.beginTransaction()
        val activeFragment = childFragmentManager.findFragmentByTag(viewModel.activeFragmentTag) as BaseFragment?
        if (activeFragment == null) {
            fragmentTransition.add(R.id.childFragmentContainer, fragment, tag)
        } else {
            val oldFragment = childFragmentManager.findFragmentByTag(tag) as BaseFragment?
            if (oldFragment == null) {
                fragmentTransition.add(R.id.childFragmentContainer, fragment, tag).hide(activeFragment)
            } else {
                fragmentTransition.hide(activeFragment).show(oldFragment)
                viewModel.removeItem(itemId)
            }

        }
        fragmentTransition.commit()
        viewModel.activeFragmentTag = tag
        viewModel.addItem(itemId)

    }

    override fun onBackPressed(): Boolean {
        if (!viewModel.isListItemsEmpty()) {
            viewModel.removeLast()
            if (viewModel.isListItemsEmpty()) {
                return false
            }
            val fragment = childFragmentManager.findFragmentByTag(viewModel.activeFragmentTag) as BaseFragment?
            fragment?.let {
                childFragmentManager.beginTransaction().remove(fragment).commit()
            }
            bottomNavigationView.selectedItemId = viewModel.getLastItem()
            return true
        }
        return false
    }
}