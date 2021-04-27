package com.urban.androidhomework.domain.network.character

import java.util.*

/**
 * Data Transfer Object for Rick and Morty characters, as returned from the remote api.
 * It contains any field, included in the REST response, that can be useful for the application.
 */
data class CharacterDto(
    val id: Int,
    val name: String,
    val created: Date,
    val status: String,
    val species: String,
    val gender: String,
    val image: String
)