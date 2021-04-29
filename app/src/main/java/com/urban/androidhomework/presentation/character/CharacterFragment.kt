package com.urban.androidhomework.presentation.character

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.urban.androidhomework.R
import com.urban.androidhomework.databinding.FragmentCharacterBinding
import com.urban.androidhomework.domain.model.ShowCharacter
import com.urban.androidhomework.domain.model.ShowLocation
import com.urban.androidhomework.presentation.general.GlideApp
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Fragment class containing the logic of the Character Detail screen.
 *
 * If provided with direct Character information data, it displays it immediately, and triggers an
 * asynchronous fetch of the related location data.
 *
 * If no direct data is available, but a character id is provided, both the character data, and later
 * the location data referenced in it, are asynchronously fetched and displayed.
 *
 * If no form of character information is provided, or if any part of the request data cannot be
 * fetched for any reason, the unavailable information elements display generic "Unknown" label.
 */
@AndroidEntryPoint
class CharacterFragment : Fragment(R.layout.fragment_character) {
    private val viewModel by viewModels<CharacterViewModel>()
    private val binding by viewBinding(FragmentCharacterBinding::bind)
    private val args: CharacterFragmentArgs by navArgs()

    private lateinit var unknownLabel: String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        unknownLabel = getString(R.string.unknown)

        val charInfo = args.charInfo
        if (charInfo != null) {
            // Direct character data provided, load it directly
            initView(charInfo)
        } else if (args.id > 0) {
            // Character ID available, start fetching character data and display it on success
            lifecycleScope.launch {
                viewModel.getCharInfo(args.id).collect { retrievedCharInfo ->
                    retrievedCharInfo?.let { initView(it) }
                }
            }
        } else {
            // No form of character data available, initialise UI to generic "Unknown" data
            initView(null)
        }
    }

    private fun initView(charInfo: ShowCharacter?) = with(binding) {
        selectedName.text = charInfo?.name

        GlideApp.with(binding.root.context)
                .load(charInfo?.image ?: R.raw.unnamed)
                .into(characterImage)

        charinfoStatus.text = charInfo?.status ?: unknownLabel
        charinfoSpecies.text = charInfo?.species ?: unknownLabel
        charinfoGender.text = charInfo?.gender ?: unknownLabel

        val locationId = charInfo?.locationId
        if (locationId != null) {
            // Location ID available, start fetching location data and display it on success
            lifecycleScope.launch {
                viewModel.getLocation(locationId).collect { setLocationInfo(it) }
            }
        } else {
            // No location ID available, set related fields to Unknown
            setLocationInfo(null)
        }
    }

    private fun setLocationInfo(location: ShowLocation?) = with(binding) {
        locationName.text = location?.name ?: unknownLabel
        locationType.text = location?.type ?: unknownLabel
        locationDimensions.text = location?.dimension ?: unknownLabel
    }
}