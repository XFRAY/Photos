package com.itrexgroup.photos.vm

import android.app.Application
import com.itrexgroup.photos.ui.base.BaseViewModel

class MainFlowViewModel(application: Application) : BaseViewModel(application) {


    private val listOfCheckedItemsIds = ArrayList<Int>()

    lateinit var activeFragmentTag: String

    fun isListItemsEmpty() = listOfCheckedItemsIds.isEmpty()

    fun removeItem(itemId: Int) {
        listOfCheckedItemsIds.remove(itemId)
    }

    fun addItem(itemId: Int) {
        listOfCheckedItemsIds.add(itemId)
    }

    fun removeLast() {
        listOfCheckedItemsIds.removeAt(listOfCheckedItemsIds.lastIndex)
    }

    fun getLastItem() = listOfCheckedItemsIds.last()

}