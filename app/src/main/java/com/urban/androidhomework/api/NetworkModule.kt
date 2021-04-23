package com.urban.androidhomework.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier

@Qualifier
annotation class RickAndMortyBaseUrl

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @RickAndMortyBaseUrl
    fun provideRickAndMortyBaseUrl() = RICK_AND_MORTY_BASE_URL

    @Provides
    fun provideRetrofit(@RickAndMortyBaseUrl baseUrl: String) = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    fun provideRickAndMortyApi(retrofit: Retrofit) = retrofit.create(RickAndMortyApi::class.java)

    internal companion object {
        const val RICK_AND_MORTY_BASE_URL = "https://rickandmortyapi.com/api/"
    }
}