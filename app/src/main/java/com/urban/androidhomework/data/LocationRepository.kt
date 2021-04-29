package com.urban.androidhomework.data

import com.urban.androidhomework.api.RickAndMortyApi
import com.urban.androidhomework.domain.model.ShowLocation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

/**
 * Repository class for data relative to Rick and Morty locations.
 */
class LocationRepository @Inject constructor(
        private val rickAndMortyApi: RickAndMortyApi
) {
    /**
     * Returns a flow emitting a show location
     */
    fun getLocation(id: Int): Flow<Result<ShowLocation>> = flow {
        rickAndMortyApi.getLocation(id).let {
            emit(Result.success(ShowLocation(
                    name = it.name,
                    type = it.type,
                    dimension = it.dimension,
                    residents = it.residents?.filterNotNull()
            )))
        }

    }.catch {
        Timber.e(it, "Error while retrieving location data")
        emit(Result.failure(it))
    }
}