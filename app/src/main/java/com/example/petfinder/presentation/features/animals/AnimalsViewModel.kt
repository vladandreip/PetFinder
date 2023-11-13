package com.example.petfinder.presentation.features.animals

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.petfinder.domain.model.Animals
import com.example.petfinder.domain.usecases.GetAnimalsUseCase
import com.example.petfinder.util.Resource
import com.example.petfinder.util.Resource.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@HiltViewModel
class AnimalsViewModel @Inject constructor(
    private val getAnimalsUseCase: GetAnimalsUseCase
) : ViewModel() {
    private val animals: MutableLiveData<Resource<Animals>> = MutableLiveData()
    val animalsLiveData = animals

    fun getAnimalsBy(animalType: String) = getAnimalsUseCase(animalType)
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe {
            animals.value = Loading(true)
        }
        .doOnSuccess {
            animals.value = Loading(false)
        }
        .doOnError {
            animals.value = Loading(false)
        }
        .subscribe({ response ->
            animals.value = response
        }, {
            animals.value = Error(it)
        })

}