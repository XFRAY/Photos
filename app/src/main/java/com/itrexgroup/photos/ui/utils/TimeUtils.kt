package com.itrexgroup.photos.ui.utils

import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {

    fun convertServerDateToLocaleDate(serverDate: String): String {
        val date = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.getDefault()).parse(serverDate)
        return SimpleDateFormat("dd-MM-yy hh:mm", Locale.getDefault()).format(date)
    }
}