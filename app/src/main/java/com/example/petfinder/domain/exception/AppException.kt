package com.example.petfinder.domain.exception

sealed class AppException : Exception() {
    class NotFoundException : AppException()
    class InvalidTokenException : AppException()
    class InternalServerErrorException : AppException()
    class UnknownException : AppException()
    class ForbiddenException : AppException()
    class BadRequestException : AppException()
}