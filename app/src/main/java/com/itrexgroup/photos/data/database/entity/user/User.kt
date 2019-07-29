package com.itrexgroup.photos.data.database.entity.user

import com.google.gson.annotations.SerializedName

data class User(val id: String,
                val name: String,
                val username: String,
                @SerializedName("first_name")
                val firstName: String,
                @SerializedName("last_name")
                val lastName: String)