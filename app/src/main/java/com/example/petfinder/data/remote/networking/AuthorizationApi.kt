package com.example.petfinder.data.remote.networking

import com.example.petfinder.data.remote.dto.AuthorizationRequest
import com.example.petfinder.data.remote.dto.AuthorizationResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthorizationApi {

    @POST("v2/oauth2/token")
    fun getAuthenticationToken(
        @Body authorizationRequest: AuthorizationRequest = AuthorizationRequest.getAuthorizationRequest(),
    ): Single<AuthorizationResponse>
}