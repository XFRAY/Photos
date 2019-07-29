package com.itrexgroup.photos.ui.adapters

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.itrexgroup.photos.R
import com.itrexgroup.photos.data.database.entity.photos.Photo
import com.itrexgroup.photos.ui.utils.TimeUtils
import com.itrexgroup.photos.ui.utils.inflate

class PhotosAdapter :
    PagedListAdapter<Photo, PhotosAdapter.Holder>(photosComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(parent.inflate(R.layout.item_photo))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = getItem(position)
        holder.bindItem(item)
    }

    class Holder(view: View) : RecyclerView.ViewHolder(view) {

        private val cnstrBackground = view.findViewById<ConstraintLayout>(R.id.cnstrBackground)
        private val imgPhoto = view.findViewById<ImageView>(R.id.imgPhoto)
        private val txtAuthor = view.findViewById<TextView>(R.id.txtAuthor)
        private val txtLikes = view.findViewById<TextView>(R.id.txtLikes)
        private val txtCreatedAt = view.findViewById<TextView>(R.id.txtCreatedAt)
        private val imgProfilePhoto = view.findViewById<ImageView>(R.id.imgProfilePhoto)

        fun bindItem(item: Photo?) {
            item?.let {
                val photoUrl = it.urls
                val profileImage = it.author.profileImage
                val author = it.author.name
                cnstrBackground.setBackgroundColor(Color.parseColor(it.color))

                val photoRequestOptions = RequestOptions()
                    .centerCrop()
                Glide.with(imgPhoto.context)
                    .load(photoUrl.full)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .thumbnail(
                        Glide.with(imgPhoto.context)
                            .load(photoUrl.thumb)
                            .apply(photoRequestOptions)
                    )
                    .apply(photoRequestOptions)
                    .into(imgPhoto)

                val profilePhotoRequestOptions = RequestOptions().circleCrop()

                Glide.with(imgProfilePhoto.context)
                    .load(profileImage.large)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .thumbnail(
                        Glide.with(imgProfilePhoto.context)
                            .load(profileImage.small)
                            .apply(profilePhotoRequestOptions)
                    )
                    .apply(profilePhotoRequestOptions)
                    .into(imgProfilePhoto)

                txtAuthor.text = author
                txtLikes.text = it.likes.toString()
                txtCreatedAt.text = TimeUtils.convertServerDateToLocaleDate(it.createdAt)

            }
        }
    }

    companion object {
        val photosComparator = object : DiffUtil.ItemCallback<Photo>() {
            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean =
                oldItem.id == newItem.id

        }
    }

}