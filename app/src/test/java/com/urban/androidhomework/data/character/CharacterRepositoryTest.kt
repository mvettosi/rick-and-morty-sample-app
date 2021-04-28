package com.urban.androidhomework.data.character

import com.urban.androidhomework.TestData
import com.urban.androidhomework.api.RickAndMortyApi
import com.urban.androidhomework.data.LocationRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.IOException

class CharacterRepositoryTest {
    private val rickAndMortyApi = mockk<RickAndMortyApi>(relaxed = true)
    private val characterMapper = mockk<CharacterMapper>(relaxed = true)
    private lateinit var underTest: CharacterRepository

    @Before
    fun setUp() {
        underTest = CharacterRepository(rickAndMortyApi, characterMapper)
    }

    @Test
    fun `getCharacter maps success correctly`() = runBlocking {
        // Arrange
        val expected = TestData.CHARACTER_DTO
        coEvery { rickAndMortyApi.getCharacter(1) } returns expected
        every { characterMapper.map(expected) } returns TestData.SHOW_CHARACTER

        // Act
        val actual = underTest.getCharacter(1).first()

        // Assert
        assertTrue(actual.isSuccess)
        assertEquals(expected.name, actual.getOrThrow().name)
        assertEquals(expected.created, actual.getOrThrow().created)
        assertEquals(expected.status, actual.getOrThrow().status)
        assertEquals(expected.species, actual.getOrThrow().species)
        assertEquals(expected.gender, actual.getOrThrow().gender)
        assertEquals(expected.image, actual.getOrThrow().image)
    }

    @Test
    fun `getCharacter maps exception correctly`() = runBlocking {
        // Arrange
        val expected = IOException()
        coEvery { rickAndMortyApi.getCharacter(1) } throws expected

        // Act
        val actual = underTest.getCharacter(1).first()

        // Assert
        assertTrue(actual.isFailure)
        assertEquals(expected, actual.exceptionOrNull())
    }
}