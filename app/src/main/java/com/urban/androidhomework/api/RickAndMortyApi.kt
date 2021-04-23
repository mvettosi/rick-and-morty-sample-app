package com.urban.androidhomework.api

import com.urban.androidhomework.domain.Character
import retrofit2.http.GET

interface RickAndMortyApi {
    @GET("character/")
    suspend fun getAllCharacters(): Character
}