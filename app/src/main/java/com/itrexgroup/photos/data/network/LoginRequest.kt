package com.itrexgroup.photos.data.network

class LoginRequest(val client_id: String,
                   val client_secret: String,
                   val redirect_uri: String,
                   val code: String,
                   val grant_type: String)