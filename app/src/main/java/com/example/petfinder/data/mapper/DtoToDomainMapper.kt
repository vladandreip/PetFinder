package com.example.petfinder.data.mapper

import com.example.petfinder.data.remote.dto.AnimalsDto
import com.example.petfinder.domain.model.Animal
import com.example.petfinder.domain.model.Animals
import com.example.petfinder.domain.model.Breed

fun AnimalsDto.toAnimals() = Animals(
    animals = this.animals.map {
        Animal(
            id = it.id,
            gender = it.gender ?: UNKNOWN,
            name = it.name ?: UNKNOWN,
            status = it.status ?: UNKNOWN,
            breeds = Breed(
                it.breeds.primary ?: UNKNOWN,
                it.breeds.secondary ?: UNKNOWN
            ),
            size = it.size ?: UNKNOWN,
            distance = it.distance ?: UNKNOWN
        )
    }
)

private const val UNKNOWN = "Unknown"