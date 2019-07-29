package com.itrexgroup.photos.ui.fragments.login

import android.content.Context
import android.os.Bundle
import android.view.View
import com.itrexgroup.photos.R
import com.itrexgroup.photos.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_welcome.*
import java.lang.ClassCastException

class WelcomeFragment : BaseFragment() {

    companion object {
        const val TAG = "WELCOME_FRAGMENT_TAG"
        fun newInstance() = WelcomeFragment()
    }

    private lateinit var parent: WelcomeFragmentParent

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (parentFragment as? WelcomeFragmentParent)?.let { it ->
            parent = it
        } ?: throw ClassCastException("$parentFragment should implement WelcomeFragmentParent")
    }

    override fun getLayoutResourceId(): Int = R.layout.fragment_welcome

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        btnLogin.setOnClickListener {
            parent.login()
        }
    }
}