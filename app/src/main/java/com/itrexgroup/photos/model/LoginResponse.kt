package com.itrexgroup.photos.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("token_type")
    val tokeType: String,
    val scope: String,
    @SerializedName("created_at")
    val createdAt: String
)