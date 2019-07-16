package com.itrexgroup.photos.view.fragments.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.itrexgroup.photos.view.activities.Router

abstract class BaseFragment : Fragment() {

    protected var router: Router? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as? Router)?.let {
            router = it
        } ?: throw ClassCastException("$context should implement Router")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutResourceId(), container, false)
    }

    protected abstract fun getLayoutResourceId(): Int

    override fun onDetach() {
        super.onDetach()
        router = null
    }

}