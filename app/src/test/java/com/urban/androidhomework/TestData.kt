package com.urban.androidhomework

import com.urban.androidhomework.domain.model.ShowCharacter
import com.urban.androidhomework.domain.network.InfoDto
import com.urban.androidhomework.domain.network.character.CharacterDto
import com.urban.androidhomework.domain.network.PagedListDto
import com.urban.androidhomework.domain.network.location.LocationDto
import java.util.*

object TestData {
    const val PREV = 1
    const val NEXT = 2
    const val PAGE_URL = "https://rickandmortyapi.com/api/character/?page="
    const val LOCATION_URL = "https://rickandmortyapi.com/api/location/"

    private val INFO_FIRST_PAGE = InfoDto(
            count = 1,
            pages = 2,
            prev = null,
            next = "${PAGE_URL}${NEXT}"
    )

    val INFO_SECOND_PAGE = InfoDto(
            count = 2,
            pages = 2,
            prev = "${PAGE_URL}${PREV}",
            next = null
    )

    private val CHARACTER_DTO = CharacterDto(
            id = 1,
            name = "test",
            created = Date(),
            status = "Test status",
            species = "Test Species",
            gender = "Male",
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            location = CharacterDto.Location(
                    name = "Earth",
                    url = "https://rickandmortyapi.com/api/location/20"
            )
    )

    val LOCATION_DTO = LocationDto(
            id = 1,
            name = "Test",
            type = "Test",
            dimension = "Test",
            residents = listOf("Test", null, "Test2")
    )

    val CHARACTER_RESPONSE_FIRST_PAGE = PagedListDto(
            info = INFO_FIRST_PAGE,
            results = listOf(CHARACTER_DTO)
    )

    val CHARACTER_RESPONSE_SECOND_PAGE = PagedListDto(
            info = INFO_SECOND_PAGE,
            results = listOf(CHARACTER_DTO)
    )

    val SHOW_CHARACTER = ShowCharacter(
            name = CHARACTER_DTO.name!!,
            created = CHARACTER_DTO.created!!,
            status = CHARACTER_DTO.status,
            species = CHARACTER_DTO.species,
            gender = CHARACTER_DTO.gender,
            image = CHARACTER_DTO.image,
            locationId = 20
    )
}