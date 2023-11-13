package com.example.petfinder.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Breed(
    var primary: String,
    var secondary: String,
) : Parcelable