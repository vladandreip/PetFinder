package com.example.petfinder.domain.usecases

import com.example.petfinder.domain.repositories.AnimalsRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class GetAnimalsUseCase @Inject constructor(
    private val repository: AnimalsRepository,
) {

    operator fun invoke(animalType: String) =
        repository.getAnimalsBy(animalType)
            .observeOn(
                AndroidSchedulers.mainThread()
            )
}