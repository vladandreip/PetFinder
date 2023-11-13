package com.example.petfinder.di

import android.content.Context
import com.example.petfinder.data.mapper.ApiExceptionMapperImpl
import com.example.petfinder.data.remote.networking.AuthorizationApi
import com.example.petfinder.data.remote.networking.PetApi
import com.example.petfinder.data.storage.EncryptedStorageImpl
import com.example.petfinder.data.storage.Storage
import com.example.petfinder.domain.exception.ApiExceptionMapper
import com.example.petfinder.presentation.uirendererror.UiErrorHandler
import com.example.petfinder.presentation.uirendererror.UiErrorHandlerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = "https://api.petfinder.com/"

    @Provides
    @Singleton
    fun providePetApi(): PetApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor().apply {
                            setLevel(HttpLoggingInterceptor.Level.BODY)
                        }
                    ).build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create()

    @Provides
    @Singleton
    fun provideAuthorizationApi(): AuthorizationApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor().apply {
                            setLevel(HttpLoggingInterceptor.Level.BODY)
                        }
                    ).build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create()

    @Provides
    @Singleton
    fun provideStorage(@ApplicationContext appContext: Context): Storage =
        EncryptedStorageImpl(appContext)

    @Provides
    @Singleton
    fun provideUiErrorHandler(): UiErrorHandler = UiErrorHandlerImpl()

    @Provides
    @Singleton
    fun provideApiExceptionMapper(): ApiExceptionMapper = ApiExceptionMapperImpl()

}