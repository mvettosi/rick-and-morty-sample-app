package com.urban.androidhomework.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.urban.androidhomework.api.RickAndMortyApi
import com.urban.androidhomework.domain.model.ShowCharacter
import retrofit2.HttpException
import java.io.IOException

/**
 * Paging DataSource from the JetPack's Paging library, to page and consume characters from the show.
 */
class CharacterPagingSource(
        private val rickAndMortyApi: RickAndMortyApi,
) : PagingSource<Int, ShowCharacter>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ShowCharacter> {
        try {
            // Fetch the http response for the requested page
            val result = rickAndMortyApi.getAllCharacters(params.key)

            // Map results to Character instances
            val showCharacters: List<ShowCharacter> = result.results.map {
                ShowCharacter(
                        name = it.name,
                        created = it.created,
                        status = it.status,
                        species = it.species,
                        gender = it.gender,
                        image = it.image
                )
            }

            // Build page result
            return LoadResult.Page(
                    data = showCharacters,
                    prevKey = result.info.getPrevPage(),
                    nextKey = result.info.getNextPage()
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ShowCharacter>) =
            state.anchorPosition?.let { anchorPosition ->
                state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                        ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
            }

}