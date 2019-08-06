package com.itrexgroup.photos.ui.adapters.photos

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.itrexgroup.photos.R
import com.itrexgroup.photos.data.database.entity.photos.Photo
import com.itrexgroup.photos.utils.TimeUtils

class PhotosHolder(view: View,
                   private val onItemClickListener: (view: View) -> Unit) : RecyclerView.ViewHolder(view) {

    private val cnstrBackground = view.findViewById<ConstraintLayout>(R.id.cnstrBackground)
    private val imgPhoto = view.findViewById<ImageView>(R.id.imgPhoto)
    private val txtAuthor = view.findViewById<TextView>(R.id.txtAuthor)
    private val txtLikes = view.findViewById<TextView>(R.id.txtLikes)
    private val txtCreatedAt = view.findViewById<TextView>(R.id.txtCreatedAt)
    private val imgProfilePhoto = view.findViewById<ImageView>(R.id.imgProfilePhoto)


    fun bindItem(item: Photo?) {
        item?.let {
            imgPhoto.transitionName = cnstrBackground.context.getString(R.string.photo_transition) + adapterPosition
            cnstrBackground.setOnClickListener {
                onItemClickListener.invoke(imgPhoto)
            }
            val photoUrl = item.urls
            val profileImage = item.author.profileImage
            val author = item.author.name
            cnstrBackground.setBackgroundColor(Color.parseColor(item.color))

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
            txtLikes.text = item.likes.toString()
            txtCreatedAt.text = TimeUtils.convertServerDateToLocaleDate(item.createdAt)
        }

    }
}