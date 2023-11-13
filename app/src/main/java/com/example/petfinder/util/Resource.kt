package com.example.petfinder.util

sealed interface Resource<out T> {
    data class Content<T>(val data: T) : Resource<T>
    data class Error(val exception: Throwable? = null) : Resource<Nothing>
    data class Loading(val isLoading: Boolean) : Resource<Nothing>
}