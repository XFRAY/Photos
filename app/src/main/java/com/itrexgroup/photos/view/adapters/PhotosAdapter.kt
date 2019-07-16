package com.itrexgroup.photos.view.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.alpha
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.itrexgroup.photos.R
import com.itrexgroup.photos.model.Photo
import com.itrexgroup.photos.utils.TimeUtils

class PhotosAdapter(private val onItemClickListener: (view: View, url: String, photoTransitionName: String) -> Unit)
    : RecyclerView.Adapter<PhotosAdapter.Holder>() {

    var listPhotos: List<Photo> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false), onItemClickListener)
    }

    override fun getItemCount() = listPhotos.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = listPhotos[position]
        holder.bindItem(item)
    }

    class Holder(view: View,
                 private val onItemClickListener: (view: View, url: String, photoTransitionName: String) -> Unit)
        : RecyclerView.ViewHolder(view) {

        private val cnstrBackground = view.findViewById<ConstraintLayout>(R.id.cnstrBackground)
        private val imgPhoto = view.findViewById<ImageView>(R.id.imgPhoto)
        private val txtAuthor = view.findViewById<TextView>(R.id.txtAuthor)
        private val txtLikes = view.findViewById<TextView>(R.id.txtLikes)
        private val txtCreatedAt = view.findViewById<TextView>(R.id.txtCreatedAt)

        fun bindItem(item: Photo) {
            val url = item.urls
            val author = item.user.name
            cnstrBackground.setBackgroundColor(Color.parseColor(item.color))
            cnstrBackground.setOnClickListener {
                val transitionName = cnstrBackground.context.getString(R.string.photo_transition) + adapterPosition
                imgPhoto.transitionName = transitionName
                onItemClickListener.invoke(imgPhoto, url.full, transitionName)
            }

            val requestOptions = RequestOptions()
                    .centerCrop()
            Glide.with(imgPhoto.context)
                    .load(url.full)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .thumbnail(
                            Glide.with(imgPhoto.context)
                                    .load(url.thumb)
                                    .apply(requestOptions)
                    )
                    .apply(requestOptions)
                    .into(imgPhoto)

            txtAuthor.text = author
            txtLikes.text = item.likes.toString()
            txtCreatedAt.text = TimeUtils.convertServerDateToLocaleDate(item.createdAt)


        }
    }

}