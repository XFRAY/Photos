package com.itrexgroup.photos.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

fun ViewGroup.inflate(layoutId: Int): View =
        LayoutInflater.from(context).inflate(layoutId, this, false)

fun BottomNavigationView.checkItem(@IdRes itemId: Int) {
    menu.findItem(itemId).isChecked = true
}

fun Fragment.getColor(@ColorRes color: Int) =
        ContextCompat.getColor(requireContext(), color)