package com.urban.androidhomework.domain.network

import com.urban.androidhomework.TestData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class InfoDtoTest {
    private lateinit var underTest: InfoDto

    @Test
    fun `getPrevPage returns null on null prev url`() {
        // Arrange
        underTest = InfoDto(
                count = 1,
                pages = 1,
                prev = null,
                next = "${TestData.PAGE_URL}${TestData.NEXT}"
        )

        // Act
        val actual = underTest.getPrevPage()

        // Assert
        assertNull(actual)
    }

    @Test
    fun `getPrevPage returns null on empty prev url`() {
        // Arrange
        underTest = InfoDto(
                count = 1,
                pages = 1,
                prev = "",
                next = "${TestData.PAGE_URL}${TestData.NEXT}"
        )

        // Act
        val actual = underTest.getPrevPage()

        // Assert
        assertNull(actual)
    }

    @Test
    fun `getPrevPage returns null on invalid url`() {
        // Arrange
        underTest = InfoDto(
                count = 1,
                pages = 1,
                prev = "some invalid url",
                next = "${TestData.PAGE_URL}${TestData.NEXT}"
        )

        // Act
        val actual = underTest.getPrevPage()

        // Assert
        assertNull(actual)
    }

    @Test
    fun `getPrevPage returns null on url with missing page`() {
        // Arrange
        underTest = InfoDto(
                count = 1,
                pages = 1,
                prev = TestData.PAGE_URL,
                next = "${TestData.PAGE_URL}${TestData.NEXT}"
        )

        // Act
        val actual = underTest.getPrevPage()

        // Assert
        assertNull(actual)
    }


    @Test
    fun `getNextPage returns null on null prev url`() {
        // Arrange
        underTest = InfoDto(
                count = 1,
                pages = 1,
                prev = "${TestData.PAGE_URL}${TestData.PREV}",
                next = null
        )

        // Act
        val actual = underTest.getNextPage()

        // Assert
        assertNull(actual)
    }

    @Test
    fun `getNextPage returns null on empty prev url`() {
        // Arrange
        underTest = InfoDto(
                count = 1,
                pages = 1,
                prev = "${TestData.PAGE_URL}${TestData.PREV}",
                next = ""
        )

        // Act
        val actual = underTest.getNextPage()

        // Assert
        assertNull(actual)
    }

    @Test
    fun `getNextPage returns null on invalid url`() {
        // Arrange
        underTest = InfoDto(
                count = 1,
                pages = 1,
                prev = "${TestData.PAGE_URL}${TestData.PREV}",
                next = "some invalid url"
        )

        // Act
        val actual = underTest.getNextPage()

        // Assert
        assertNull(actual)
    }

    @Test
    fun `getNextPage returns null on url with missing page`() {
        // Arrange
        underTest = InfoDto(
                count = 1,
                pages = 1,
                prev = "${TestData.PAGE_URL}${TestData.PREV}",
                next = TestData.PAGE_URL
        )

        // Act
        val actual = underTest.getNextPage()

        // Assert
        assertNull(actual)
    }

    @Test
    fun `getNextPage returns correct prev and next page number`() {
        // Arrange
        underTest = InfoDto(
                count = 1,
                pages = 1,
                prev = "${TestData.PAGE_URL}${TestData.PREV}",
                next = "${TestData.PAGE_URL}${TestData.NEXT}"
        )

        // Act
        val actualPrev = underTest.getPrevPage()
        val actualNext = underTest.getNextPage()

        // Assert
        assertEquals(TestData.PREV, actualPrev)
        assertEquals(TestData.NEXT, actualNext)
    }
}