package com.itrexgroup.photos.ui.base.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.itrexgroup.photos.data.network.NetworkState
import kotlinx.android.synthetic.main.item_network_state.view.*

class NetworkStateViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val progressBar = view.progressBar
    private val txtUpdate = view.txtUpdate

    fun bindItem(item: NetworkState?) {
        item.let {
            when (it) {
                NetworkState.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    txtUpdate.visibility = View.GONE
                }
                NetworkState.FAILED -> {
                    txtUpdate.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                }
            }
        }
    }
}