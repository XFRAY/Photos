package com.itrexgroup.photos.view.fragments.login

import android.content.Context
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.Observer
import com.itrexgroup.photos.BuildConfig
import com.itrexgroup.photos.R
import com.itrexgroup.photos.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment() {

    companion object {
        private const val SUCCESS_LOGIN_URL_PREFIX = "https://unsplash.com/oauth/authorize/native?code="
        private const val URL = "https://unsplash.com/oauth/authorize?client_id=" + BuildConfig.UNSPLASH_CLIENT_ID +
                "&response_type=code&scope=public+read_user&redirect_uri=" + BuildConfig.UNSPLASH_REDIRECT_URI

        const val TAG = "LOGIN_FRAGMENT_TAG"
        fun newInstance() = LoginFragment()
    }

    private val viewModel: LoginViewModel by viewModel()

    override fun getLayoutResourceId() = R.layout.fragment_login

    private lateinit var parent: LoginFragmentParent

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (parentFragment as? LoginFragmentParent)?.let { it ->
            parent = it
        } ?: throw ClassCastException("$parentFragment should implement LoginFragmentParent")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeViewModelEvents()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView.webViewClient = webViewClient
        webView.loadUrl(URL)
    }

    private fun observeViewModelEvents() {
        viewModel.errorLiveEvent.observe(this, Observer {
            parent.userLoggedError()
        })

        viewModel.userLoggedLiveEvent.observe(this, Observer {
            parent.userLogged()
        })
    }


    private val webViewClient = object : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            url?.let {
                if (it.startsWith(SUCCESS_LOGIN_URL_PREFIX)) {
                    viewModel.handleCodeFromUrlAndLogin(url)
                    return false
                }
            }
            return super.shouldOverrideUrlLoading(view, url)

        }

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            request?.let {
                val url = it.url.toString()
                if (url.startsWith(SUCCESS_LOGIN_URL_PREFIX)) {
                    viewModel.handleCodeFromUrlAndLogin(url)
                    return false
                }
            }
            return super.shouldOverrideUrlLoading(view, request)
        }
    }
}
