package com.example.petfinder.data.remote.networking

import com.example.petfinder.data.remote.dto.AnimalsDto
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface PetApi {

    @GET("v2/animals")
    fun getAnimalsBy(
        @Query("type") animalType: String,
        @Header("Authorization") token: String,
    ): Single<Response<AnimalsDto>>
}
