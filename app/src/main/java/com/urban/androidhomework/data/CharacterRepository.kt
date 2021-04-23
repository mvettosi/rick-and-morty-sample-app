package com.urban.androidhomework.data

import com.urban.androidhomework.api.RickAndMortyApi
import javax.inject.Inject

/**
 * Repository class for data relative to Rick and Morty characters.
 * This is where all data mapping, caching and some error handling should be done.
 */
class CharacterRepository @Inject constructor(private val rickAndMortyApi: RickAndMortyApi) {
    suspend fun getAllCharacters() = rickAndMortyApi.getAllCharacters()
}