package com.example.petfinder.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Animal(
    var id: Int,
    var gender: String,
    var status: String,
    var name: String,
    var breeds: Breed,
    var size: String,
    var distance: String,
) : Parcelable