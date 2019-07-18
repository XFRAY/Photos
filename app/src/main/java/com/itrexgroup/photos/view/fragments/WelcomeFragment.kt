package com.itrexgroup.photos.view.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.itrexgroup.photos.R
import com.itrexgroup.photos.model.AnimationOptions
import com.itrexgroup.photos.view.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_welcome.*

class WelcomeFragment : BaseFragment() {

    companion object {
        const val TAG = "WELCOME_FRAGMENT_TAG"
        fun newInstance() = WelcomeFragment()
    }

    override fun getLayoutResourceId(): Int = R.layout.fragment_welcome

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        btnLogin.setOnClickListener {
            val animationOptions =
                AnimationOptions(R.anim.slide_in_left, R.anim.slide_out_right, 0, R.anim.slide_out_right)
            router?.navigateTo(LoginFragment.newInstance(), LoginFragment.TAG, "login", animationOptions)
        }
    }
}