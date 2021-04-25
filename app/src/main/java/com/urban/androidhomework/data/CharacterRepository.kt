package com.urban.androidhomework.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.urban.androidhomework.api.RickAndMortyApi
import com.urban.androidhomework.domain.Character
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Repository class for data relative to Rick and Morty characters.
 * This is where all data mapping, caching and some error handling should be done.
 */
class CharacterRepository @Inject constructor(
        private val rickAndMortyApi: RickAndMortyApi
) : PagingSource<Int, Character.CharacterData>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character.CharacterData> {
        val currentLoadingPageKey = params.key ?: 1

        return try {
            val response = rickAndMortyApi.getAllCharacters(currentLoadingPageKey)
            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            LoadResult.Page(
                    data = response.results,
                    prevKey = prevKey,
                    nextKey = currentLoadingPageKey + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character.CharacterData>) =
            state.anchorPosition?.let { anchorPosition ->
                state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?:
                state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
            }

}