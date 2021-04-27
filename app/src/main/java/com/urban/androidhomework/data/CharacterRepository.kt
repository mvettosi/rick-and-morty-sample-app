package com.urban.androidhomework.data

import com.urban.androidhomework.api.RickAndMortyApi
import java.util.*
import javax.inject.Inject

/**
 * Repository class for data relative to Rick and Morty characters.
 */
class CharacterRepository @Inject constructor(
        private val rickAndMortyApi: RickAndMortyApi
) {
    fun getPagingSource() = CharacterPagingSource(rickAndMortyApi)
}