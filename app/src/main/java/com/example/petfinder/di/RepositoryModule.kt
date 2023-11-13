package com.example.petfinder.di

import com.example.petfinder.data.repositories.AnimalsRepositoryImpl
import com.example.petfinder.data.repositories.AuthorizationRepository
import com.example.petfinder.data.repositories.AuthorizationRepositoryImpl
import com.example.petfinder.domain.repositories.AnimalsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Singleton
    @Binds
    fun provideAnimalsRepository(
        animalsRepositoryImpl: AnimalsRepositoryImpl
    ): AnimalsRepository

    @Singleton
    @Binds
    fun provideAuthRepository(
        authorizationRepository: AuthorizationRepositoryImpl
    ): AuthorizationRepository
}