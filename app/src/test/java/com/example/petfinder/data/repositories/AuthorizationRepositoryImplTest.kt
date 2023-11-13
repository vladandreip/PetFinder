package com.example.petfinder.data.repositories

import com.example.petfinder.data.remote.dto.AuthorizationResponse
import com.example.petfinder.data.remote.networking.AuthorizationApi
import com.example.petfinder.data.storage.Storage
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class AuthorizationRepositoryImplTest {

    @Mock
    lateinit var storage: Storage

    @Mock
    lateinit var authorizationApi: AuthorizationApi

    private lateinit var authorizationApiImpl: AuthorizationRepository

    @Before
    fun setup() {
        authorizationApiImpl = AuthorizationRepositoryImpl(storage, authorizationApi)
    }

    @Test
    fun `given get auth token from api, then return correct bearer token`() {
        whenever(authorizationApi.getAuthenticationToken()).thenReturn(Single.just(
            AuthorizationResponse(
                tokenType = "bearer",
                expiresIn = 0,
                accessToken = FAKE_TOKEN
            )
        ))

        val testSubscriber = authorizationApiImpl.getAuthorizationToken(true).test()

        val bearerToken = "Bearer $FAKE_TOKEN"
        testSubscriber.assertValue(bearerToken)
    }

    @Test
    fun `given get auth token from api, then store token`() {
        whenever(authorizationApi.getAuthenticationToken()).thenReturn(Single.just(
            AuthorizationResponse(
                tokenType = "bearer",
                expiresIn = 0,
                accessToken = FAKE_TOKEN
            )
        ))

        val testSubscriber = authorizationApiImpl.getAuthorizationToken(true).test()

        val bearerToken = "Bearer $FAKE_TOKEN"
        verify(storage).putDataString(any(), eq(bearerToken))
        testSubscriber.assertComplete()
    }

    @Test
    fun `given get auth token from storage, then return token from storage`() {
        val bearerToken = "Bearer $FAKE_TOKEN"
        whenever(storage.getDataString(any())).thenReturn(bearerToken)

        val testSubscriber = authorizationApiImpl.getAuthorizationToken(false).test()

        verify(storage).getDataString(any())
        testSubscriber.assertComplete()
    }

    companion object {
        private const val FAKE_TOKEN = "fakeToken"
    }
}