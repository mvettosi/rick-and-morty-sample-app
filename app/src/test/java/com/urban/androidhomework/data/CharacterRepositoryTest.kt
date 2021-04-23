package com.urban.androidhomework.data

import com.urban.androidhomework.api.RickAndMortyApi
import com.urban.androidhomework.domain.Character
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CharacterRepositoryTest {
    private val rickAndMortyApi = mockk<RickAndMortyApi>(relaxed = true)
    private lateinit var underTest: CharacterRepository

    @Before
    fun setUp() {
        underTest = CharacterRepository(rickAndMortyApi)
    }

    @Test
    fun `returns the object retrieved from api`() = runBlocking {
        // Arrange
        val expected = Character(listOf(Character.CharacterData(1, "test")))
        coEvery { rickAndMortyApi.getAllCharacters() } returns expected

        // Act
        val actual = underTest.getAllCharacters()

        // Assert
        assertEquals(expected, actual)
    }
}