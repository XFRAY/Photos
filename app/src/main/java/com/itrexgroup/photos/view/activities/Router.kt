package com.itrexgroup.photos.view.activities

import android.view.View
import com.itrexgroup.photos.model.AnimationOptions
import com.itrexgroup.photos.view.fragments.base.BaseFragment

interface Router {

    fun navigateTo(fragment: BaseFragment, tag: String, backStackName: String?, animationOptions: AnimationOptions?)

    fun navigateToWithViewTransition(fragment: BaseFragment, tag: String, view: View, backStackName: String?)

}