package com.urban.androidhomework.data

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.urban.androidhomework.api.RickAndMortyApi
import com.urban.androidhomework.domain.Character
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

class CharacterRepositoryTest {
    private val rickAndMortyApi = mockk<RickAndMortyApi>(relaxed = true)
    private lateinit var underTest: CharacterRepository

    @Before
    fun setUp() {
        underTest = CharacterRepository(rickAndMortyApi)
    }

    @Test
    fun `load handles successful response of first page`() = runBlocking {
        // Arrange
        val expected = Character(listOf(Character.CharacterData(1, "test")))
        coEvery { rickAndMortyApi.getAllCharacters(1) } returns expected

        // Act
        val actual = underTest.load(PagingSource.LoadParams.Refresh(1, 20, true))

        // Assert
        assertTrue(actual is PagingSource.LoadResult.Page)
        assertEquals(expected.results, (actual as PagingSource.LoadResult.Page).data)
        assertNull(actual.prevKey)
        assertEquals(2, actual.nextKey)
    }

    @Test
    fun `load handles successful response of second page`() = runBlocking {
        // Arrange
        val expected = Character(listOf(Character.CharacterData(1, "test")))
        coEvery { rickAndMortyApi.getAllCharacters(2) } returns expected

        // Act
        val actual = underTest.load(PagingSource.LoadParams.Refresh(2, 20, true))

        // Assert
        assertTrue(actual is PagingSource.LoadResult.Page)
        assertEquals(expected.results, (actual as PagingSource.LoadResult.Page).data)
        assertEquals(1, actual.prevKey)
        assertEquals(3, actual.nextKey)
    }

    @Test
    fun `load handles error response`() = runBlocking {
        // Arrange
        val expected = IOException()
        coEvery { rickAndMortyApi.getAllCharacters(1) } throws expected

        // Act
        val actual = underTest.load(PagingSource.LoadParams.Refresh(1, 20, true))

        // Assert
        assertTrue(actual is PagingSource.LoadResult.Error)
        assertEquals(expected, (actual as PagingSource.LoadResult.Error).throwable)
    }

    @Test
    fun `getRefreshKey handles null anchorPosition`() {
        // Arrange
        val pagingState = mockk<PagingState<Int, Character.CharacterData>>()
        every { pagingState.anchorPosition } returns null

        // Act
        val actual = underTest.getRefreshKey(pagingState)

        // Assert
        assertNull(actual)
    }

    @Test
    fun `getRefreshKey handles null closestPageToPosition`() {
        // Arrange
        val pagingState = mockk<PagingState<Int, Character.CharacterData>>()
        every { pagingState.anchorPosition } returns 1
        every { pagingState.closestPageToPosition(1) } returns null

        // Act
        val actual = underTest.getRefreshKey(pagingState)

        // Assert
        assertNull(actual)
    }

    @Test
    fun `getRefreshKey handles null prevKey and nextKey`() {
        // Arrange
        val pagingState = mockk<PagingState<Int, Character.CharacterData>>()
        every { pagingState.anchorPosition } returns 1
        val loadResult = mockk<PagingSource.LoadResult.Page<Int, Character.CharacterData>>()
        every { pagingState.closestPageToPosition(1) } returns loadResult
        every { loadResult.prevKey } returns null
        every { loadResult.nextKey } returns null

        // Act
        val actual = underTest.getRefreshKey(pagingState)

        // Assert
        assertNull(actual)
    }

    @Test
    fun `getRefreshKey handles existing prevKey`() {
        // Arrange
        val pagingState = mockk<PagingState<Int, Character.CharacterData>>()
        every { pagingState.anchorPosition } returns 1
        val loadResult = mockk<PagingSource.LoadResult.Page<Int, Character.CharacterData>>()
        every { pagingState.closestPageToPosition(1) } returns loadResult
        every { loadResult.prevKey } returns 1
        every { loadResult.nextKey } returns null

        // Act
        val actual = underTest.getRefreshKey(pagingState)

        // Assert
        assertEquals(2, actual)
    }

    @Test
    fun `getRefreshKey handles existing nextKey`() {
        // Arrange
        val pagingState = mockk<PagingState<Int, Character.CharacterData>>()
        every { pagingState.anchorPosition } returns 1
        val loadResult = mockk<PagingSource.LoadResult.Page<Int, Character.CharacterData>>()
        every { pagingState.closestPageToPosition(1) } returns loadResult
        every { loadResult.prevKey } returns null
        every { loadResult.nextKey } returns 3

        // Act
        val actual = underTest.getRefreshKey(pagingState)

        // Assert
        assertEquals(2, actual)
    }
}