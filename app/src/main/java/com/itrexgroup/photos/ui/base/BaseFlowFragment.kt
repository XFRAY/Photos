package com.itrexgroup.photos.ui.base

import com.itrexgroup.photos.R
import com.itrexgroup.photos.utils.AnimationOptions

abstract class BaseFlowFragment : BaseFragment(), BaseRouter, OnBackPressed {

    override fun navigateTo(
            fragment: BaseFragment,
            tag: String,
            addToBackStack: Boolean,
            animationOptions: AnimationOptions?
    ) {
        val fragmentTransaction = childFragmentManager.beginTransaction()
        animationOptions?.let {
            fragmentTransaction.setCustomAnimations(
                    animationOptions.enterAnimation,
                    animationOptions.exitAnimation,
                    animationOptions.popEnterAnimation,
                    animationOptions.popExitAnimation
            )
        }
        fragmentTransaction.replace(R.id.childFragmentContainer, fragment, tag)
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(tag)
        }
        fragmentTransaction.commit()
    }

    override fun back() {
        router?.back()
    }


    override fun onBackPressed(): Boolean {
        if (childFragmentManager.backStackEntryCount > 1) {
            childFragmentManager.popBackStack()
            return true
        }
        return false
    }

}