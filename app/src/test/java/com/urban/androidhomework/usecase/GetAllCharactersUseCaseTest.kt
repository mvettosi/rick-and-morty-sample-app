package com.urban.androidhomework.usecase

import com.urban.androidhomework.data.CharacterRepository
import com.urban.androidhomework.domain.Character
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
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
        val actual = underTest().toList()

        // Assert
        assertEquals(2, actual.size)
        assertTrue(actual[0] is Loading<Character>)
        assertTrue(actual[1] is Success<Character>)
        assertEquals(expected, (actual[1] as Success<Character>).data)
    }

    @Test
    fun `check that exception is handled in the flow`() = runBlocking {
        // Arrange
        val expected = Exception()
        coEvery { characterRepository.getAllCharacters() } throws expected

        // Act
        val actual = underTest().toList()

        // Assert
        assertEquals(2, actual.size)
        assertTrue(actual[0] is Loading<Character>)
        assertTrue(actual[1] is Failure<Character>)
        assertEquals(expected, (actual[1] as Failure<Character>).exception)
    }
}