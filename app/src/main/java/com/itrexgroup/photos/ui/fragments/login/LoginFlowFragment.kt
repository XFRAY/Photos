package com.itrexgroup.photos.ui.fragments.login

import android.os.Bundle
import android.view.View
import com.itrexgroup.photos.R
import com.itrexgroup.photos.ui.base.BaseFlowFragment
import com.itrexgroup.photos.ui.fragments.main.MainFlowFragment
import com.itrexgroup.photos.utils.AnimationOptions

class LoginFlowFragment : BaseFlowFragment(), WelcomeFragmentParent, LoginFragmentParent {

    companion object {
        const val TAG = "LOGIN_FLOW_FRAGMENT_TAG"
        fun newInstance() = LoginFlowFragment()
    }

    override fun getLayoutResourceId() = R.layout.fragment_base_flow

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            navigateTo(WelcomeFragment.newInstance(), WelcomeFragment.TAG, true, null)
        }
    }

    override fun login() {
        val animationOptions =
                AnimationOptions(
                        R.anim.slide_in_left,
                        R.anim.slide_out_right,
                        0,
                        R.anim.slide_out_right
                )
        navigateTo(
                LoginFragment.newInstance(),
                LoginFragment.TAG, true, animationOptions
        )
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
}