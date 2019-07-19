package com.itrexgroup.photos.view.adapters

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.itrexgroup.photos.R
import com.itrexgroup.photos.entity.photos.Photo
import com.itrexgroup.photos.utils.TimeUtils
import com.itrexgroup.photos.utils.inflate

class PhotosAdapter(private val onItemClickListener: (view: View, url: String, photoTransitionName: String) -> Unit) :
    RecyclerView.Adapter<PhotosAdapter.Holder>() {

    var listPhotos: List<Photo> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(parent.inflate(R.layout.item_photo), onItemClickListener)
    }

    override fun getItemCount() = listPhotos.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = listPhotos[position]
        holder.bindItem(item)
    }

    class Holder(
        view: View,
        private val onItemClickListener: (view: View, url: String, photoTransitionName: String) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        private val cnstrBackground = view.findViewById<ConstraintLayout>(R.id.cnstrBackground)
        private val imgPhoto = view.findViewById<ImageView>(R.id.imgPhoto)
        private val txtAuthor = view.findViewById<TextView>(R.id.txtAuthor)
        private val txtLikes = view.findViewById<TextView>(R.id.txtLikes)
        private val txtCreatedAt = view.findViewById<TextView>(R.id.txtCreatedAt)
        private val imgProfilePhoto = view.findViewById<ImageView>(R.id.imgProfilePhoto)

        fun bindItem(item: Photo) {
            val photoUrl = item.urls
            val profileImage = item.author.profileImage
            val author = item.author.name
            cnstrBackground.setBackgroundColor(Color.parseColor(item.color))
            cnstrBackground.setOnClickListener {
                val transitionName = cnstrBackground.context.getString(R.string.photo_transition) + adapterPosition
                imgPhoto.transitionName = transitionName
                onItemClickListener.invoke(imgPhoto, photoUrl.full, transitionName)
            }

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