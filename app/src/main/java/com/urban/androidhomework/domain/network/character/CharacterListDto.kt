package com.urban.androidhomework.domain.network.character

import com.urban.androidhomework.domain.network.InfoDto

/**
 * Data Transfer Object for Rick and Morty characters list response.
 * It contains any field, included in the REST response, that can be useful for the application.
 */
data class CharacterListDto(
        val info: InfoDto,
        val results: List<CharacterDto>
)
