package com.urban.androidhomework.api

import com.google.gson.Gson
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import retrofit2.Retrofit
import java.time.Month
import java.time.ZoneId
import java.util.*

class NetworkModuleTest {
    private lateinit var underTest: NetworkModule

    @Before
    fun setUp() {
        underTest = NetworkModule()
    }

    @Test
    fun provideRetrofit() {
        // Arrange
        // Act
        val actual = underTest.provideRetrofit(NetworkModule.RICK_AND_MORTY_BASE_URL, Gson())

        // Assert
        assertTrue(actual.baseUrl().isHttps)
        assertEquals(NetworkModule.RICK_AND_MORTY_BASE_URL, actual.baseUrl().toString())
        assertTrue(actual.converterFactories().size > 0)
    }

    @Test
    fun provideRickAndMortyApi() {
        // Arrange
        val retrofit = mockk<Retrofit>(relaxed = true)
        val expected = mockk<RickAndMortyApi>()
        every { retrofit.create(RickAndMortyApi::class.java) } returns expected

        // Act
        val actual = underTest.provideRickAndMortyApi(retrofit)

        // Assert
        assertEquals(expected, actual)
    }
}