package com.itrexgroup.photos.view.activities

import android.view.View
import com.itrexgroup.photos.view.base.BaseFragment
import com.itrexgroup.photos.view.base.BaseRouter

interface Router : BaseRouter {

    fun navigateToWithViewTransition(fragment: BaseFragment, tag: String, view: View, backStackName: String?)

}