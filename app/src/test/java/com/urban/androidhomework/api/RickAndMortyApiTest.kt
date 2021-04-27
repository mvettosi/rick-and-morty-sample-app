package com.urban.androidhomework.api

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import java.time.Month
import java.time.ZoneId

class RickAndMortyApiTest {
    lateinit var server: MockWebServer
    private val networkModule = NetworkModule()

    @Before
    fun setUp() {
        server = MockWebServer()
    }

    @After
    fun shutdown() {
        server.shutdown()
    }

    @Test
    fun `api call can be serialized in our model`() = runBlocking {
        // Arrange
        server.apply {
            enqueue(MockResponse().setBody(TEST_CHARACTERS))
        }
        val baseUrl = server.url("")
        val retrofit = networkModule.provideRetrofit(
                baseUrl.toUrl().toString(),
                networkModule.provideGson()
        )
        val underTest = networkModule.provideRickAndMortyApi(retrofit)

        // Act
        val result = underTest.getAllCharacters(1)

        // Assert
        assertNotNull(result.info)
        assertEquals(671, result.info.count)
        assertEquals(34, result.info.pages)
        assertEquals("https://rickandmortyapi.com/api/character/?page=2", result.info.next)
        assertNull(result.info.prev)

        assertNotNull(result.results)
        assertFalse(result.results.isEmpty())
        assertEquals(1, result.results.size)
        assertEquals(1, result.results[0].id)
        assertEquals("Rick Sanchez", result.results[0].name)

        val created = result.results[0].created
        val localCreated = created.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        assertEquals(2017, localCreated.year)
        assertEquals(Month.NOVEMBER, localCreated.month)
        assertEquals(4, localCreated.dayOfMonth)
    }

    private companion object {
        const val TEST_CHARACTERS =
                """
                    {
                      "info": {
                        "count": 671,
                        "pages": 34,
                        "next": "https://rickandmortyapi.com/api/character/?page=2",
                        "prev": null
                      },
                      "results": [
                        {
                          "id": 1,
                          "name": "Rick Sanchez",
                          "status": "Alive",
                          "species": "Human",
                          "type": "",
                          "gender": "Male",
                          "origin": {
                            "name": "Earth",
                            "url": "https://rickandmortyapi.com/api/location/1"
                          },
                          "location": {
                            "name": "Earth",
                            "url": "https://rickandmortyapi.com/api/location/20"
                          },
                          "image": "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                          "episode": [
                            "https://rickandmortyapi.com/api/episode/1",
                            "https://rickandmortyapi.com/api/episode/2"
                          ],
                          "url": "https://rickandmortyapi.com/api/character/1",
                          "created": "2017-11-04T18:48:46.250Z"
                        }
                      ]
                    }
                """
    }
}