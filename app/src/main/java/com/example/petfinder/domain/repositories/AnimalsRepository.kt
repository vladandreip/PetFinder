package com.example.petfinder.domain.repositories

import com.example.petfinder.domain.model.Animals
import com.example.petfinder.util.Resource
import io.reactivex.rxjava3.core.Single

interface AnimalsRepository {
    fun getAnimalsBy(animalType: String): Single<Resource<Animals>>
}