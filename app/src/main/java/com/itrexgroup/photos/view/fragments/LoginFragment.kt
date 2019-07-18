package com.itrexgroup.photos.view.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.itrexgroup.photos.BuildConfig
import com.itrexgroup.photos.R
import com.itrexgroup.photos.view.fragments.base.BaseFragment
import com.itrexgroup.photos.vm.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment() {

    companion object {
        private const val URL = "https://unsplash.com/oauth/authorize?client_id=" + BuildConfig.UNSPLASH_CLIENT_ID +
                "&response_type=code&scope=public+read_user&redirect_uri=" + BuildConfig.UNSPLASH_REDIRECT_URI

        const val TAG = "LOGIN_FRAGMENT_TAG"
        fun newInstance() = LoginFragment()
    }

    private val viewModel: LoginViewModel by viewModel()

    override fun getLayoutResourceId() = R.layout.fragment_login

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView.webViewClient = webViewClient
        webView.loadUrl(URL)
    }

    private val webViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            viewModel.handleCodeFromUrlAndLogin(url)
            return super.shouldOverrideUrlLoading(view, url)
        }

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            request?.let {
                viewModel.handleCodeFromUrlAndLogin(it.url.toString())
            }
            return super.shouldOverrideUrlLoading(view, request)
        }
    }
}
