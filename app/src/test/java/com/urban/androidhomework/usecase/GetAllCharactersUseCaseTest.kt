package com.urban.androidhomework.usecase

import com.urban.androidhomework.data.CharacterRepository
import com.urban.androidhomework.domain.Character
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class GetAllCharactersUseCaseTest {
    private val characterRepository = mockk<CharacterRepository>(relaxed = true)
    private lateinit var underTest: GetAllCharactersUseCase

    @Before
    fun setUp() {
        underTest = GetAllCharactersUseCase(characterRepository)
    }

    @Test
    fun `check that returned value is emitted in the flow`() = runBlocking {
        // Arrange
        val expected = Character(listOf(Character.CharacterData(1, "test")))
        coEvery { characterRepository.getAllCharacters() } returns expected

        // Act
        val actual = underTest().first()

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `check that exception is handled in the flow`() = runBlocking {
        // Arrange
        coEvery { characterRepository.getAllCharacters() } throws Exception()

        // Act
        val actual = underTest().first()

        // Assert
        assertTrue(actual.results.isEmpty())
    }
}