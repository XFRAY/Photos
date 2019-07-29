package com.itrexgroup.photos.ui.activities

import android.view.View
import com.itrexgroup.photos.ui.base.BaseFragment
import com.itrexgroup.photos.ui.base.BaseRouter

interface Router : BaseRouter {

    fun navigateToWithViewTransition(fragment: BaseFragment, tag: String, view: View, backStackName: String?)

}