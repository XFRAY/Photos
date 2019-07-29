package com.itrexgroup.photos.data.database.entity.photos

import com.google.gson.annotations.SerializedName

data class Author(val name: String,
                  @SerializedName("profile_image")
                  val profileImage: ProfileImage)