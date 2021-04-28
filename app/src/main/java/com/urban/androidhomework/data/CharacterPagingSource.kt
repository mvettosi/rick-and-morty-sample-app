package com.urban.androidhomework.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.urban.androidhomework.api.RickAndMortyApi
import com.urban.androidhomework.domain.model.ShowCharacter
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.util.*

/**
 * Paging DataSource from the JetPack's Paging library, to page and consume characters from the show.
 */
class CharacterPagingSource(
        private val rickAndMortyApi: RickAndMortyApi,
) : PagingSource<Int, ShowCharacter>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ShowCharacter> {
        try {
            // Fetch the http response for the requested page
            val response = rickAndMortyApi.getAllCharacters(params.key)

            val showCharacters: List<ShowCharacter> = response
                    .results
                    // Only take characters that have a name and a creation date
                    ?.filter { characterDto ->
                        (characterDto.name != null && characterDto.created != null).also { valid ->
                            if (!valid) {
                                Timber.w("Ignoring invalid show character: $characterDto")
                            }
                        }
                    }
                    // Map results to ShowCharacter instances
                    ?.map {
                        ShowCharacter(
                                // These fields are guaranteed to never be null because instances
                                // without them are filtered out in the earlier step
                                name = it.name as String,
                                created = it.created as Date,

                                // These are left as nullable for the UI layer to decide how to deal
                                // with them
                                status = it.status,
                                species = it.species,
                                gender = it.gender,
                                image = it.image,
                                locationId = it.getLocationId()
                        )
                    } ?: emptyList()

            // Build page result
            return LoadResult.Page(
                    data = showCharacters,
                    prevKey = response.info?.getPrevPage(),
                    nextKey = response.info?.getNextPage()
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