package com.example.petfinder.domain.exception

import okhttp3.ResponseBody

interface ApiExceptionMapper {
    fun mapApiError(statusCode: Int, errorBody: ResponseBody?): Exception
}