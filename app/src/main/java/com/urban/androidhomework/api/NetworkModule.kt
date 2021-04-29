package com.urban.androidhomework.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier

/**
 * Annotation class used to identify the base url of the Rick and Morty api as an injectable String
 */
@Qualifier
annotation class RickAndMortyBaseUrl

/**
 * Hilt Module providing information regarding how to construct objects related to the networking
 * layer.
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @RickAndMortyBaseUrl
    fun provideRickAndMortyBaseUrl() = RICK_AND_MORTY_BASE_URL

    @Provides
    fun provideGson() = GsonBuilder().setDateFormat(TIME_FORMAT).create()

    @Provides
    fun provideRetrofit(@RickAndMortyBaseUrl baseUrl: String, gson: Gson) = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    fun provideRickAndMortyApi(retrofit: Retrofit) = retrofit.create(RickAndMortyApi::class.java)

    internal companion object {
        const val TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
        const val RICK_AND_MORTY_BASE_URL = "https://rickandmortyapi.com/api/"
    }
}