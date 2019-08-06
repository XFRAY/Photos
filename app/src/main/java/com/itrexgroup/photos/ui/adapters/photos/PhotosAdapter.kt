package com.itrexgroup.photos.ui.adapters.photos

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.itrexgroup.photos.R
import com.itrexgroup.photos.data.database.entity.photos.Photo
import com.itrexgroup.photos.data.network.NetworkState
import com.itrexgroup.photos.ui.base.adapter.NetworkStateViewHolder
import com.itrexgroup.photos.utils.inflate

class PhotosAdapter(private val onItemClickListener:(view: View) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {

        private const val ITEM_PHOTO = R.layout.item_photo
        private const val ITEM_NETWORK_STATE = R.layout.item_network_state

        val photosComparator = object : DiffUtil.ItemCallback<Photo>() {
            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean =
                    oldItem == newItem

            override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean =
                    oldItem.id == newItem.id
        }
    }

    var items: List<Photo> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_PHOTO -> PhotosHolder(parent.inflate(R.layout.item_photo), onItemClickListener)
            ITEM_NETWORK_STATE -> NetworkStateViewHolder(parent.inflate(R.layout.item_network_state))
            else -> throw IllegalArgumentException("unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ITEM_PHOTO -> (holder as PhotosHolder).bindItem(items[position])
            ITEM_NETWORK_STATE -> (holder as NetworkStateViewHolder).bindItem(networkState)
        }
    }

    fun setNetworkState(networkState: NetworkState) {
        if (!items.isNullOrEmpty()) {
            val previousState = this.networkState
            val hadExtraRow = hasExtraRow()
            this.networkState = networkState
            val hasExtraRow = hasExtraRow()
            if (hadExtraRow != hasExtraRow) {
                if (hadExtraRow) {
                    notifyItemRemoved(itemCount)
                } else {
                    notifyItemInserted(itemCount)
                }
            } else if (hasExtraRow && previousState !== networkState) {
                notifyItemChanged(itemCount - 1)
            }
        }
    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            ITEM_NETWORK_STATE
        } else {
            ITEM_PHOTO
        }
    }

    override fun getItemCount(): Int {
        return items.size + if (hasExtraRow()) 1 else 0
    }

}