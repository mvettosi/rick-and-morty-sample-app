package com.urban.androidhomework.api

import com.urban.androidhomework.domain.network.PagedListDto
import com.urban.androidhomework.domain.network.character.CharacterDto
import com.urban.androidhomework.domain.network.location.LocationDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit interface definition to access the Rick and Morty REST apis.
 */
interface RickAndMortyApi {
    /**
     * Returns a result containing a paginated list of the characters from the show.
     * Each page contains at most 20 items.
     *
     * @param pageNumber the page number to load. If not provided, the first one will be used.
     */
    @GET("character/")
    suspend fun getAllCharacters(@Query("page") pageNumber: Int? = FIRST_PAGE): PagedListDto<CharacterDto>

    /**
     * Retrieves information regarding a character from the show.
     *
     * @param id the id of the character to load
     */
    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): CharacterDto

    /**
     * Retrieves information regarding a location from the show.
     *
     * @param id the id of the location to load
     */
    @GET("location/{id}")
    suspend fun getLocation(@Path("id") id: Int): LocationDto

    private companion object {
        const val FIRST_PAGE = 1
    }
}