package com.example.petfinder.data.remote.dto

data class AnimalDto(
    var id: Int,
    var gender: String?,
    var status: String?,
    var name: String?,
    var breeds: BreedDto,
    var size: String?,
    var distance: String?
)