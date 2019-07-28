package com.itrexgroup.photos.view.base

import com.itrexgroup.photos.model.AnimationOptions
import com.itrexgroup.photos.view.base.BaseFragment

interface BaseRouter {

    fun navigateTo(fragment: BaseFragment, tag: String, addToBackStack: Boolean, animationOptions: AnimationOptions?)

    fun back()
}