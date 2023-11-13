package com.example.petfinder.data.mapper

import com.example.petfinder.domain.exception.AppException.BadRequestException
import com.example.petfinder.domain.exception.AppException.ForbiddenException
import com.example.petfinder.domain.exception.AppException.InternalServerErrorException
import com.example.petfinder.domain.exception.AppException.InvalidTokenException
import com.example.petfinder.domain.exception.AppException.NotFoundException
import com.example.petfinder.domain.exception.AppException.UnknownException
import org.junit.Assert
import org.junit.Test


class ApiExceptionMapperImplTest {

    private val mapper = ApiExceptionMapperImpl()

    @Test
    fun `given 400 code, mapper returns BadRequestException`() {
        val exception = mapper.mapApiError(BAD_REQUEST, null)
        Assert.assertTrue(exception is BadRequestException)
    }

    @Test(expected = InvalidTokenException::class)
    fun `given 401 code, mapper throws InvalidTokenException`() {
        val exception = mapper.mapApiError(UNAUTHORIZED, null)
        Assert.assertTrue(exception is InvalidTokenException)
    }

    @Test
    fun `given 403 code, mapper returns ForbiddenException`() {
        val exception = mapper.mapApiError(FORBIDDEN, null)
        Assert.assertTrue(exception is ForbiddenException)
    }

    @Test
    fun `given 500 code, mapper returns InternalServerErrorException`() {
        val exception = mapper.mapApiError(INTERNAL_SERVER_ERROR, null)
        Assert.assertTrue(exception is InternalServerErrorException)
    }

    @Test
    fun `given 404 code, mapper returns NotFoundException`() {
        val exception = mapper.mapApiError(NOT_FOUND, null)
        Assert.assertTrue(exception is NotFoundException)
    }

    @Test
    fun `given random code, mapper returns UnknownException`() {
        val exception = mapper.mapApiError(RANDOM, null)
        Assert.assertTrue(exception is UnknownException)
    }

    companion object {
        const val BAD_REQUEST = 400
        const val UNAUTHORIZED = 401
        const val FORBIDDEN = 403
        const val INTERNAL_SERVER_ERROR = 500
        const val NOT_FOUND = 404
        const val RANDOM = 999
    }
}