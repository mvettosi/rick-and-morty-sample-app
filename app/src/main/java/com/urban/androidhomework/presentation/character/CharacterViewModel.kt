package com.urban.androidhomework.presentation.character

import androidx.lifecycle.ViewModel
import com.urban.androidhomework.data.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
        private val locationRepository: LocationRepository
) : ViewModel() {

    fun getLocation(id: Int) =
            locationRepository
                    .getLocation(id)
                    .map { it.getOrNull() }
}