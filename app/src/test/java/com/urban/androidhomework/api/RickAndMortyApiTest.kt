package com.urban.androidhomework.api

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

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
        val retrofit = networkModule.provideRetrofit(baseUrl.toUrl().toString())
        val underTest = networkModule.provideRickAndMortyApi(retrofit)

        // Act
        val result = underTest.getAllCharacters()

        // Assert
        assertNotNull(result.results)
        assertFalse(result.results.isEmpty())
        assertEquals(1, result.results.size)
        assertEquals(1, result.results[0].id)
        assertEquals("Rick Sanchez", result.results[0].name)
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