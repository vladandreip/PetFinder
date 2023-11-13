package com.example.petfinder.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AuthorizationRequest(
    @SerializedName("grant_type")
    val grantType: String,
    @SerializedName("client_id")
    val clientId: String,
    @SerializedName("client_secret")
    val clientSecret: String,
) {
    companion object {
        fun getAuthorizationRequest() = AuthorizationRequest(
            grantType = "client_credentials",
            clientId = "8FvB92COL3loJkRHBozGPLOVKZTG4CgXal6Dou6EjsH5lj2SXB",
            clientSecret = "zcYSA3CrhG6yW1dc539o8rAVgj7ecwLUaYHTSe3s",
        )
    }
}