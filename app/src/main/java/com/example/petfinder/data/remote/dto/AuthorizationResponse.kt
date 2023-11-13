package com.example.petfinder.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AuthorizationResponse(
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("expires_in")
    val expiresIn: Int,
    @SerializedName("access_token")
    val accessToken: String,
)