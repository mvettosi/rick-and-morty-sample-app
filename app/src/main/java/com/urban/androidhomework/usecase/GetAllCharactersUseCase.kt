package com.urban.androidhomework.usecase

import com.urban.androidhomework.data.CharacterRepository
import com.urban.androidhomework.domain.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class GetAllCharactersUseCase @Inject constructor(
        private val characterRepository: CharacterRepository
) {
    operator fun invoke(): Flow<ResourceStatus<Character>> = flow {
        emit(Loading<Character>())
        emit(Success(characterRepository.getAllCharacters()))
    }.catch { e ->
        Timber.e(e, "Exception while retrieving the list of characters")
        emit(Failure(e))
    }
}