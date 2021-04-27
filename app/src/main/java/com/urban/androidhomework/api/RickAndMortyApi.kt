package com.urban.androidhomework.api

import com.urban.androidhomework.domain.network.character.CharacterListDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit interface definition to access the Rick and Morty REST apis.
 */
interface RickAndMortyApi {
    /**
     * Returns a result containing a paginated list of the characters from the show.
     * Each page contains at most 20 items.
     * If not provided, the first page will be used for the query.
     */
    @GET("character/")
    suspend fun getAllCharacters(@Query("page") pageNumber: Int? = FIRST_PAGE): CharacterListDto

    private companion object {
        const val FIRST_PAGE = 1
    }
}