package com.example.petfinder.data.storage

interface Storage {
    fun putDataString(key: String, data: String)
    fun getDataString(key: String): String
}