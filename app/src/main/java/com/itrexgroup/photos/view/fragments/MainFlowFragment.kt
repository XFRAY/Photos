package com.itrexgroup.photos.view.fragments

import android.os.Bundle
import android.util.SparseArray
import android.view.View
import androidx.annotation.IdRes
import com.itrexgroup.photos.R
import com.itrexgroup.photos.view.fragments.base.BaseFragment
import com.itrexgroup.photos.view.fragments.base.OnBackPressed
import kotlinx.android.synthetic.main.fragment_main_flow.*

class MainFlowFragment : BaseFragment(), OnBackPressed {

    private var savedStateSparseArray = SparseArray<SavedState>()
    private var currentSelectItemId = R.id.photos

    companion object {
        const val TAG = "MAIN_FLOW_FRAGMENT_TAG"
        fun newInstance() = MainFlowFragment()

        private const val SAVED_STATE_CONTAINER_KEY = "ContainerKey"
        private const val SAVED_STATE_CURRENT_TAB_KEY = "CurrentTabKey"
    }

    override fun getLayoutResourceId() = R.layout.fragment_main_flow

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedInstanceState?.let {
            savedStateSparseArray = it.getSparseParcelableArray(SAVED_STATE_CONTAINER_KEY)
            currentSelectItemId = it.getInt(SAVED_STATE_CURRENT_TAB_KEY)
        }
        setupBottomNavigationView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSparseParcelableArray(SAVED_STATE_CONTAINER_KEY, savedStateSparseArray)
        outState.putInt(SAVED_STATE_CURRENT_TAB_KEY, currentSelectItemId)
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
    }

    private fun replaceFragment(fragment: BaseFragment, tag: String, @IdRes itemId: Int) {
        val fragmentTransaction = childFragmentManager.beginTransaction()
        val oldFragment = childFragmentManager.findFragmentByTag(tag) as BaseFragment?

        if (oldFragment == null) {
         //   savedFragmentState(itemId)
          //  fragment.setInitialSavedState(savedStateSparseArray[itemId])
            fragmentTransaction.replace(R.id.childFragmentContainer, fragment, tag)
            fragmentTransaction.addToBackStack("stack")
        } else {
            fragmentTransaction.replace(R.id.childFragmentContainer, oldFragment)
        }
        fragmentTransaction.commit()
    }

    private fun savedFragmentState(actionId: Int) {
        val currentFragment = childFragmentManager.findFragmentById(R.id.childFragmentContainer)
        currentFragment?.let {
            savedStateSparseArray.put(currentSelectItemId, childFragmentManager.saveFragmentInstanceState(it))
        }
        currentSelectItemId = actionId
    }

    override fun onBackPressed(): Boolean {
        childFragmentManager.fragments.forEach { fragment ->
            if (fragment != null && fragment.isVisible) {
                with(fragment.childFragmentManager) {
                    if (backStackEntryCount > 0) {
                        popBackStack()
                        return@with
                    }
                }
            }
        }
        return true
    }
}