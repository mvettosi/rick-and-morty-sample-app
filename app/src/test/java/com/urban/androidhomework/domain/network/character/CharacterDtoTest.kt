package com.urban.androidhomework.domain.network.character

import com.urban.androidhomework.TestData
import com.urban.androidhomework.domain.network.InfoDto
import org.junit.Assert.*
import org.junit.Test

class CharacterDtoTest {
    private lateinit var underTest: CharacterDto

    @Test
    fun `getLocationId returns null on null location`() {
        // Arrange
        underTest = CharacterDto()

        // Act
        val actual = underTest.getLocationId()

        // Assert
        assertNull(actual)
    }

    @Test
    fun `getLocationId returns null on null location url`() {
        // Arrange
        underTest = CharacterDto(location = CharacterDto.Location())

        // Act
        val actual = underTest.getLocationId()

        // Assert
        assertNull(actual)
    }

    @Test
    fun `getNextPage returns null on empty location url`() {
        // Arrange
        underTest = CharacterDto(
                location = CharacterDto.Location(
                        url = ""
                )
        )

        // Act
        val actual = underTest.getLocationId()

        // Assert
        assertNull(actual)
    }

    @Test
    fun `getNextPage returns null on invalid url`() {
        // Arrange
        underTest = CharacterDto(
                location = CharacterDto.Location(
                        url = "some invalid url"
                )
        )

        // Act
        val actual = underTest.getLocationId()

        // Assert
        assertNull(actual)
    }

    @Test
    fun `getNextPage returns null on url with missing page`() {
        // Arrange
        underTest = CharacterDto(
                location = CharacterDto.Location(
                        url = TestData.LOCATION_URL
                )
        )

        // Act
        val actual = underTest.getLocationId()

        // Assert
        assertNull(actual)
    }

    @Test
    fun `getNextPage returns correct prev and next page number`() {
        // Arrange
        underTest = CharacterDto(
                location = CharacterDto.Location(
                        url = "${TestData.LOCATION_URL}1"
                )
        )

        // Act
        val actual = underTest.getLocationId()

        // Assert
        assertEquals(1, actual)
    }
}