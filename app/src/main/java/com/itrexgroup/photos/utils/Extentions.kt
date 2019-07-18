package com.itrexgroup.photos.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflate(layoutId: Int): View =
    LayoutInflater.from(context).inflate(layoutId, this, false)