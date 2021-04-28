package com.urban.androidhomework.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.urban.androidhomework.TestData
import com.urban.androidhomework.api.RickAndMortyApi
import com.urban.androidhomework.domain.model.ShowCharacter
import com.urban.androidhomework.domain.network.PagedListDto
import com.urban.androidhomework.domain.network.character.CharacterDto
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
import java.util.*

class CharacterPagingSourceTest {
    private val rickAndMortyApi = mockk<RickAndMortyApi>(relaxed = true)
    private lateinit var underTest: CharacterPagingSource

    @Before
    fun setUp() {
        underTest = CharacterPagingSource(rickAndMortyApi)
    }

    @Test
    fun `load handles successful response of first page`() = runBlocking {
        // Arrange
        val response = TestData.CHARACTER_RESPONSE_FIRST_PAGE
        coEvery { rickAndMortyApi.getAllCharacters(1) } returns response
        val expected = listOf(TestData.SHOW_CHARACTER)

        // Act
        val actual = underTest.load(PagingSource.LoadParams.Refresh(1, 20, true))

        // Assert
        assertTrue(actual is PagingSource.LoadResult.Page)
        assertEquals(expected, (actual as PagingSource.LoadResult.Page).data)
        assertNull(actual.prevKey)
        assertEquals(2, actual.nextKey)
    }

    @Test
    fun `load handles successful response of second page`() = runBlocking {
        // Arrange
        val response = TestData.CHARACTER_RESPONSE_SECOND_PAGE
        coEvery { rickAndMortyApi.getAllCharacters(2) } returns response
        val expected = listOf(TestData.SHOW_CHARACTER)

        // Act
        val actual = underTest.load(PagingSource.LoadParams.Refresh(2, 20, true))

        // Assert
        assertTrue(actual is PagingSource.LoadResult.Page)
        assertEquals(expected, (actual as PagingSource.LoadResult.Page).data)
        assertEquals(1, actual.prevKey)
        assertNull(actual.nextKey)
    }

    @Test
    fun `load handles successful response with a character with no name`() = runBlocking {
        // Arrange
        val response = PagedListDto(
                info = TestData.INFO_SECOND_PAGE,
                results = listOf(
                        CharacterDto(name = "one", created = Date()),
                        CharacterDto(created = Date()),
                        CharacterDto(name = "three", created = Date())
                )
        )
        coEvery { rickAndMortyApi.getAllCharacters(2) } returns response

        // Act
        val actual = underTest.load(PagingSource.LoadParams.Refresh(2, 20, true))

        // Assert
        assertTrue(actual is PagingSource.LoadResult.Page)
        assertEquals(2, (actual as PagingSource.LoadResult.Page).data.size)
    }

    @Test
    fun `load handles successful response with a character with no creation date`() = runBlocking {
        // Arrange
        val response = PagedListDto(
                info = TestData.INFO_SECOND_PAGE,
                results = listOf(
                        CharacterDto(name = "one", created = Date()),
                        CharacterDto(name = "two"),
                        CharacterDto(name = "three", created = Date())
                )
        )
        coEvery { rickAndMortyApi.getAllCharacters(2) } returns response

        // Act
        val actual = underTest.load(PagingSource.LoadParams.Refresh(2, 20, true))

        // Assert
        assertTrue(actual is PagingSource.LoadResult.Page)
        assertEquals(2, (actual as PagingSource.LoadResult.Page).data.size)
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
        val pagingState = mockk<PagingState<Int, ShowCharacter>>()
        every { pagingState.anchorPosition } returns null

        // Act
        val actual = underTest.getRefreshKey(pagingState)

        // Assert
        assertNull(actual)
    }

    @Test
    fun `getRefreshKey handles null closestPageToPosition`() {
        // Arrange
        val pagingState = mockk<PagingState<Int, ShowCharacter>>()
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
        val pagingState = mockk<PagingState<Int, ShowCharacter>>()
        every { pagingState.anchorPosition } returns 1
        val loadResult = mockk<PagingSource.LoadResult.Page<Int, ShowCharacter>>()
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
        val pagingState = mockk<PagingState<Int, ShowCharacter>>()
        every { pagingState.anchorPosition } returns 1
        val loadResult = mockk<PagingSource.LoadResult.Page<Int, ShowCharacter>>()
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
        val pagingState = mockk<PagingState<Int, ShowCharacter>>()
        every { pagingState.anchorPosition } returns 1
        val loadResult = mockk<PagingSource.LoadResult.Page<Int, ShowCharacter>>()
        every { pagingState.closestPageToPosition(1) } returns loadResult
        every { loadResult.prevKey } returns null
        every { loadResult.nextKey } returns 3

        // Act
        val actual = underTest.getRefreshKey(pagingState)

        // Assert
        assertEquals(2, actual)
    }
}