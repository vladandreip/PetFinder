package com.example.petfinder.data.mapper

import com.example.petfinder.domain.exception.ApiExceptionMapper
import com.example.petfinder.domain.exception.AppException.BadRequestException
import com.example.petfinder.domain.exception.AppException.ForbiddenException
import com.example.petfinder.domain.exception.AppException.InternalServerErrorException
import com.example.petfinder.domain.exception.AppException.InvalidTokenException
import com.example.petfinder.domain.exception.AppException.NotFoundException
import com.example.petfinder.domain.exception.AppException.UnknownException
import javax.inject.Inject
import okhttp3.ResponseBody

class ApiExceptionMapperImpl @Inject constructor() : ApiExceptionMapper {

    override fun mapApiError(statusCode: Int, errorBody: ResponseBody?): Exception {
        return when (statusCode) {
            NOT_FOUND -> NotFoundException()
            UNAUTHORIZED -> throw InvalidTokenException()
            INTERNAL_SERVER_ERROR -> InternalServerErrorException()
            FORBIDDEN -> ForbiddenException()
            BAD_REQUEST -> BadRequestException()
            else -> UnknownException()
        }
    }

    companion object {
        const val BAD_REQUEST = 400
        const val UNAUTHORIZED = 401
        const val FORBIDDEN = 403
        const val INTERNAL_SERVER_ERROR = 500
        const val NOT_FOUND = 404
    }
}