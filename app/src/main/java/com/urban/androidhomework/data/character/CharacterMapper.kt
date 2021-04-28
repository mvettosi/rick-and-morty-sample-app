package com.urban.androidhomework.data.character

import com.urban.androidhomework.domain.model.ShowCharacter
import com.urban.androidhomework.domain.network.character.CharacterDto
import javax.inject.Inject

/**
 * Simple mapper that converts a {@link CharacterDto} into a {@link ShowCharacter}
 */
class CharacterMapper @Inject constructor() {
    fun map(characterDto: CharacterDto) =
            ShowCharacter(
                    created = characterDto.created,
                    name = characterDto.name,
                    status = characterDto.status,
                    species = characterDto.species,
                    gender = characterDto.gender,
                    image = characterDto.image,
                    locationId = characterDto.getLocationId()
            )
}