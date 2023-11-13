package com.example.petfinder.data.repositories

import com.example.petfinder.data.remote.networking.AuthorizationApi
import com.example.petfinder.data.storage.Storage
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject


class AuthorizationRepositoryImpl @Inject constructor(
    private val storage: Storage,
    private val authorizationApi: AuthorizationApi,
) : AuthorizationRepository {

    override fun getAuthorizationToken(forceUpdate: Boolean): Single<String> =
        if (forceUpdate) {
            getTokenFromApi()
        } else {
            Single.just(getAuthTokenFromStorage())
        }

    private fun getTokenFromApi(): Single<String> =
        authorizationApi
            .getAuthenticationToken()
            .map {
                setStorageAuthToken(it.accessToken)
                "$TOKEN_TYPE ${it.accessToken}"
            }

    private fun getAuthTokenFromStorage(): String =
        storage.getDataString(TOKEN_KEY)

    private fun setStorageAuthToken(token: String) {
        storage.putDataString(TOKEN_KEY, "$TOKEN_TYPE $token")
    }

    companion object {
        private const val TOKEN_KEY = "TOKEN_KEY"
        private const val TOKEN_TYPE = "Bearer"
    }
}

