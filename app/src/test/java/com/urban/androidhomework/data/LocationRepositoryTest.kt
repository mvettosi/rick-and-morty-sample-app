package com.urban.androidhomework.data

import com.urban.androidhomework.TestData
import com.urban.androidhomework.api.RickAndMortyApi
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.IOException

class LocationRepositoryTest {
    private val rickAndMortyApi = mockk<RickAndMortyApi>(relaxed = true)
    private lateinit var underTest: LocationRepository

    @Before
    fun setUp() {
        underTest = LocationRepository(rickAndMortyApi)
    }

    @Test
    fun `getLocation maps success correctly`() = runBlocking {
        // Arrange
        val expected = TestData.LOCATION_DTO
        coEvery { rickAndMortyApi.getLocation(1) } returns expected

        // Act
        val actual = underTest.getLocation(1).first()

        // Assert
        assertTrue(actual.isSuccess)
        assertEquals(expected.name, actual.getOrThrow().name)
        assertEquals(expected.type, actual.getOrThrow().type)
        assertEquals(expected.dimension, actual.getOrThrow().dimension)
        assertEquals(2, actual.getOrThrow().residents?.size)
        assertEquals(expected.residents?.filterNotNull(), actual.getOrThrow().residents)
    }

    @Test
    fun `getLocation maps exception correctly`() = runBlocking {
        // Arrange
        val expected = IOException()
        coEvery { rickAndMortyApi.getLocation(1) } throws expected

        // Act
        val actual = underTest.getLocation(1).first()

        // Assert
        assertTrue(actual.isFailure)
        assertEquals(expected, actual.exceptionOrNull())
    }
}