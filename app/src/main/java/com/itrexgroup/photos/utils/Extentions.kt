package com.itrexgroup.photos.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import com.google.android.material.bottomnavigation.BottomNavigationView

fun ViewGroup.inflate(layoutId: Int): View =
    LayoutInflater.from(context).inflate(layoutId, this, false)

fun BottomNavigationView.checkItem(@IdRes itemId: Int) {
    menu.findItem(itemId).isChecked = true
}