package com.urban.androidhomework.api

import com.urban.androidhomework.domain.Character
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApi {
    @GET("character/")
    suspend fun getAllCharacters(@Query("page") pageNumber: Int): Character
}