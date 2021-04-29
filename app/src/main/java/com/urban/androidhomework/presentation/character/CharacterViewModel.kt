package com.urban.androidhomework.presentation.character

import androidx.lifecycle.ViewModel
import com.urban.androidhomework.data.LocationRepository
import com.urban.androidhomework.data.character.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * View Model class for the Character Fragment class.
 * Provides methods to fetch character and location data.
 */
@HiltViewModel
class CharacterViewModel @Inject constructor(
        private val characterRepository: CharacterRepository,
        private val locationRepository: LocationRepository
) : ViewModel() {

    fun getCharInfo(id: Int) =
            characterRepository
                    .getCharacter(id)
                    .map { it.getOrNull() }

    fun getLocation(id: Int) =
            locationRepository
                    .getLocation(id)
                    .map { it.getOrNull() }
}