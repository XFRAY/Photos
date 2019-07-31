package com.itrexgroup.photos.ui.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.transition.TransitionInflater
import com.itrexgroup.photos.R
import com.itrexgroup.photos.utils.AnimationOptions
import com.itrexgroup.photos.ui.fragments.main.MainFlowFragment
import com.itrexgroup.photos.ui.base.BaseFragment
import com.itrexgroup.photos.ui.base.OnBackPressed
import com.itrexgroup.photos.ui.fragments.login.LoginFlowFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity(), Router {

    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            observeViewModel()
        }
    }

    private fun observeViewModel() {
        viewModel.userLoggedLiveEvent.observe(this, Observer {
            if (it) {
                navigateTo(MainFlowFragment.newInstance(), MainFlowFragment.TAG, false, null)
            } else {
                navigateTo(LoginFlowFragment.newInstance(), LoginFlowFragment.TAG, false, null)
            }
        })

    }

    override fun back() {
        onBackPressed()
    }

    override fun navigateTo(
            fragment: BaseFragment,
            tag: String,
            addToBackStack: Boolean,
            animationOptions: AnimationOptions?
    ) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val oldFragment = supportFragmentManager.findFragmentByTag(tag) as? BaseFragment?
        animationOptions?.let {
            fragmentTransaction.setCustomAnimations(
                animationOptions.enterAnimation,
                animationOptions.exitAnimation,
                animationOptions.popEnterAnimation,
                animationOptions.popExitAnimation
            )
        }
        if (oldFragment == null) {
            fragmentTransaction.replace(R.id.fragmentContainer, fragment, tag)
            if (addToBackStack) {
                fragmentTransaction.addToBackStack(tag)
            }
        } else {
            fragmentTransaction.replace(R.id.fragmentContainer, oldFragment)
        }
        fragmentTransaction.commit()
    }

    override fun navigateToWithViewTransition(fragment: BaseFragment, tag: String, view: View, backStackName: String?) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment, tag)
        backStackName?.let {
            fragmentTransaction.addToBackStack(backStackName)
        }

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        currentFragment?.let {
            it.sharedElementReturnTransition =
                TransitionInflater.from(this).inflateTransition(R.transition.photo_transition)
            it.exitTransition = TransitionInflater.from(this)
                .inflateTransition(android.R.transition.no_transition)
        }

        fragment.sharedElementEnterTransition = TransitionInflater.from(this)
            .inflateTransition(R.transition.photo_transition)
        fragment.enterTransition = TransitionInflater.from(this)
            .inflateTransition(android.R.transition.no_transition)

        fragmentTransaction.addSharedElement(view, view.transitionName)
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as? OnBackPressed
        fragment?.let {
            if (!it.onBackPressed()) {
                super.onBackPressed()
            }
        } ?: super.onBackPressed()

    }
}
