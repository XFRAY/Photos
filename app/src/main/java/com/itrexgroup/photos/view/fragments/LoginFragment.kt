package com.itrexgroup.photos.view.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.itrexgroup.photos.R
import com.itrexgroup.photos.view.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*
import java.util.*

class LoginFragment : BaseFragment() {

    companion object {
        const val TAG = "LOGIN_FRAGMENT_TAG"
        fun newInstance() = LoginFragment()
    }

    override fun getLayoutResourceId(): Int = R.layout.fragment_login

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        val postData =
            "client_id=5fe66adbcb966a5c1e813074b67f364731f8d07f752f9a95b0ef52761be59fe2&client_secret=f0561de0466f105045a4fd455224ba87a77d76e3b8b9a7278ee7b8f91966aee7&redirect_uri=https://lolkek.com&code=123&grant_type=authorization_code"
        webView.postUrl("https://unsplash.com/token", postData.toByteArray())
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val url = view?.url
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
    }

    private fun setupClickListeners() {
        btnLogin.setOnClickListener {
            router?.navigateTo(PhotosFragment.newInstance(), PhotosFragment.TAG, null)
        }
    }
}