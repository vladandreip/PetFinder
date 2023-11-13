package com.example.petfinder.data.repositories

import com.example.petfinder.data.mapper.toAnimals
import com.example.petfinder.data.remote.dto.AnimalsDto
import com.example.petfinder.data.remote.networking.PetApi
import com.example.petfinder.domain.exception.ApiExceptionMapper
import com.example.petfinder.domain.repositories.AnimalsRepository
import com.example.petfinder.util.Resource
import io.reactivex.rxjava3.core.Single
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class AnimalsRepositoryImplTest {

    @Mock
    lateinit var api: PetApi

    @Mock
    lateinit var authApi: AuthorizationRepository

    @Mock
    lateinit var apiExceptionMapper: ApiExceptionMapper

    @Mock
    lateinit var responseBody: ResponseBody

    private lateinit var animalsRepository: AnimalsRepository

    @Before
    fun setup() {
        animalsRepository = AnimalsRepositoryImpl(
            api = api,
            authApi = authApi,
            apiExceptionMapper = apiExceptionMapper
        )
    }

    @Test
    fun `given successful response, then return Content`() {
        whenever(authApi.getAuthorizationToken()).thenReturn(Single.just(FAKE_TOKEN))

        val responseBody = AnimalsDto(
            animals = emptyList()
        )
        val successfulResponse = Response.success(
            responseBody
        )

        whenever(api.getAnimalsBy(DOG_TYPE, FAKE_TOKEN)).thenReturn(Single.just(successfulResponse))

        val testSubscriber = animalsRepository.getAnimalsBy(DOG_TYPE).test()

        verify(authApi).getAuthorizationToken()
        verify(api).getAnimalsBy(DOG_TYPE, FAKE_TOKEN)
        testSubscriber.assertValue {
            it is Resource.Content && it.data == responseBody.toAnimals()
        }
        testSubscriber.assertComplete()
    }

    @Test
    fun `given unSuccessful response, then return Error`() {
        whenever(authApi.getAuthorizationToken()).thenReturn(Single.just(FAKE_TOKEN))

        val errorResponse = Response.error<AnimalsDto>(400, responseBody)

        whenever(api.getAnimalsBy(DOG_TYPE, FAKE_TOKEN)).thenReturn(Single.just(errorResponse))

        val testSubscriber = animalsRepository.getAnimalsBy(DOG_TYPE).test()

        verify(authApi).getAuthorizationToken()
        verify(api).getAnimalsBy(DOG_TYPE, FAKE_TOKEN)
        verify(apiExceptionMapper).mapApiError(400, responseBody)
        testSubscriber.assertValue {
            it is Resource.Error
        }
        testSubscriber.assertComplete()
    }

    companion object {
        private const val DOG_TYPE = "dog"
        private const val FAKE_TOKEN = "fakeToken"
    }
}