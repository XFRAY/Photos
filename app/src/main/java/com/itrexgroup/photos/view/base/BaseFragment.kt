package com.itrexgroup.photos.view.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment : Fragment() {

    protected var router: BaseRouter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as? BaseRouter)?.let {
            router = it
        } ?: throw ClassCastException("$context should implement Router")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutResourceId(), container, false)
    }

    protected abstract fun getLayoutResourceId(): Int

    protected fun showSnackBar(message: String) {
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show()
        }
    }

    protected fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDetach() {
        super.onDetach()
        router = null
    }

}