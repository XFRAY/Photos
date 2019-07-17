package com.itrexgroup.photos.view.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.TransitionInflater
import com.itrexgroup.photos.R
import com.itrexgroup.photos.view.fragments.WelcomeFragment
import com.itrexgroup.photos.view.fragments.base.BaseFragment


class MainActivity : AppCompatActivity(), Router {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            navigateTo(WelcomeFragment.newInstance(), WelcomeFragment.TAG, null)
        }
    }

    override fun navigateTo(fragment: BaseFragment, tag: String, backStackName: String?) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val oldFragment = supportFragmentManager.findFragmentByTag(tag) as? BaseFragment?
        if (oldFragment == null) {
            fragmentTransaction.replace(R.id.fragmentContainer, fragment, null)
        } else {
            fragmentTransaction.replace(R.id.fragmentContainer, oldFragment)
        }
        backStackName?.let {
            fragmentTransaction.addToBackStack(backStackName)
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
}
