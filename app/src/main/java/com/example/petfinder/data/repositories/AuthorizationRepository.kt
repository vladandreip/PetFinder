package com.example.petfinder.data.repositories

import io.reactivex.rxjava3.core.Single

interface AuthorizationRepository {
    fun getAuthorizationToken(forceUpdate: Boolean = false): Single<String>
}