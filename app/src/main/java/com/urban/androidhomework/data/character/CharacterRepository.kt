package com.urban.androidhomework.data.character

import com.urban.androidhomework.api.RickAndMortyApi
import com.urban.androidhomework.domain.model.ShowCharacter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

/**
 * Repository class for data relative to Rick and Morty characters.
 */
class CharacterRepository @Inject constructor(
        private val rickAndMortyApi: RickAndMortyApi,
        private val characterMapper: CharacterMapper
) {
    /**
     * Return a new instance of the Paging Source for a Rick and Morty character
     */
    fun getPagingSource() = CharacterPagingSource(rickAndMortyApi, characterMapper)

    /**
     * Returns a flow emitting a flow of a show character
     */
    fun getCharacter(id: Int): Flow<Result<ShowCharacter>> = flow {
        rickAndMortyApi.getCharacter(id).let {
            emit(Result.success(characterMapper.map(it)))
        }
    }.catch {
        Timber.e(it, "Error while retrieving character data")
        emit(Result.failure(it))
    }
}