package com.itrexgroup.photos.ui.adapters.photos

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.itrexgroup.photos.R
import com.itrexgroup.photos.data.database.entity.photos.Photo
import com.itrexgroup.photos.data.network.NetworkState
import com.itrexgroup.photos.ui.base.adapter.NetworkStateViewHolder
import com.itrexgroup.photos.utils.inflate

class PhotosAdapter :
    PagedListAdapter<Photo, RecyclerView.ViewHolder>(photosComparator) {

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

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_PHOTO -> PhotosHolder(parent.inflate(R.layout.item_photo))
            ITEM_NETWORK_STATE -> NetworkStateViewHolder(parent.inflate(R.layout.item_network_state))
            else -> throw IllegalArgumentException("unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ITEM_PHOTO -> (holder as PhotosHolder).bindItem(getItem(position))
            ITEM_NETWORK_STATE -> (holder as NetworkStateViewHolder).bindItem(networkState)
        }
    }

    fun setNetworkState(networkState: NetworkState) {
        if (!currentList.isNullOrEmpty()) {
            val previousState = this.networkState
            val hadExtraRow = hasExtraRow()
            this.networkState = networkState
            val hasExtraRow = hasExtraRow()
            if (hadExtraRow != hasExtraRow) {
                if (hadExtraRow) {
                    notifyItemRemoved(super.getItemCount())
                } else {
                    notifyItemInserted(super.getItemCount())
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
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

}