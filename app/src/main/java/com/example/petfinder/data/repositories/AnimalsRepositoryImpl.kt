package com.example.petfinder.data.repositories

import com.example.petfinder.data.mapper.toAnimals
import com.example.petfinder.data.remote.networking.PetApi
import com.example.petfinder.domain.exception.ApiExceptionMapper
import com.example.petfinder.domain.exception.AppException
import com.example.petfinder.domain.model.Animals
import com.example.petfinder.domain.repositories.AnimalsRepository
import com.example.petfinder.util.Resource
import com.example.petfinder.util.Resource.Content
import com.example.petfinder.util.Resource.Error
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class AnimalsRepositoryImpl @Inject constructor(
    private val api: PetApi,
    private val authApi: AuthorizationRepository,
    private val apiExceptionMapper: ApiExceptionMapper
) : AnimalsRepository {

    override fun getAnimalsBy(animalType: String): Single<Resource<Animals>> {
        return Single.just(Unit)
            .subscribeOn(Schedulers.io())
            .flatMap {
                authApi.getAuthorizationToken()
            }.flatMap { token ->
                api.getAnimalsBy(animalType = animalType, token = token)
                    .map { response ->
                        if (response.isSuccessful) {
                            response.body()?.let {
                                Content(data = it.toAnimals())
                            } ?: Error(exception = apiExceptionMapper.mapApiError(statusCode = response.code(), errorBody = response.errorBody()))
                        } else {
                            Error(exception = apiExceptionMapper.mapApiError(statusCode = response.code(), errorBody = response.errorBody()))
                        }
                    }
            }.retryWhen { errors ->
                errors.flatMap { error ->
                    if (error is AppException.InvalidTokenException) {
                        Flowable.just(
                            authApi.getAuthorizationToken(
                                forceUpdate = true
                            ).blockingGet()
                        )
                    } else {
                        Flowable.error(error)
                    }
                }
            }
    }
}