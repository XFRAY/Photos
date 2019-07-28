package com.itrexgroup.photos.view.fragments.login

import android.os.Bundle
import android.view.View
import com.itrexgroup.photos.R
import com.itrexgroup.photos.model.AnimationOptions
import com.itrexgroup.photos.view.base.BaseRouter
import com.itrexgroup.photos.view.fragments.MainFlowFragment
import com.itrexgroup.photos.view.base.BaseFragment
import com.itrexgroup.photos.view.activities.OnBackPressed

class LoginFlowFragment : BaseFragment(), BaseRouter, WelcomeFragmentParent, LoginFragmentParent, OnBackPressed {

    companion object {
        const val TAG = "LOGIN_FLOW_FRAGMENT_TAG"
        fun newInstance() = LoginFlowFragment()
    }

    override fun getLayoutResourceId() = R.layout.login_flow_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            navigateTo(WelcomeFragment.newInstance(), WelcomeFragment.TAG, true, null)
        }
    }

    override fun login() {
        val animationOptions =
            AnimationOptions(R.anim.slide_in_left, R.anim.slide_out_right, 0, R.anim.slide_out_right)
        navigateTo(
            LoginFragment.newInstance(),
            LoginFragment.TAG, true, animationOptions
        )
    }

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

    override fun userLogged() {
        router?.navigateTo(
            MainFlowFragment.newInstance(),
            MainFlowFragment.TAG, false, null
        )
    }

    override fun userLoggedError() {
        showToast(getString(R.string.error_login_failed))
        back()
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