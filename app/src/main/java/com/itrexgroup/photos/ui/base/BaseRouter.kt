package com.itrexgroup.photos.ui.base

import com.itrexgroup.photos.ui.utils.AnimationOptions

interface BaseRouter {

    fun navigateTo(fragment: BaseFragment, tag: String, addToBackStack: Boolean, animationOptions: AnimationOptions?)

    fun back()
}