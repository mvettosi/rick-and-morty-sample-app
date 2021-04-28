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
            initView(charInfo)
        } else if (args.id > 0) {
            lifecycleScope.launch {
                viewModel.getCharInfo(args.id).collect { retrievedCharInfo ->
                    retrievedCharInfo?.let { initView(it) }
                }
            }
        }
    }

    private fun initView(charInfo: ShowCharacter) = with(binding) {
        selectedName.text = charInfo.name

        GlideApp.with(binding.root.context)
                .load(charInfo.image ?: R.raw.unnamed)
                .into(characterImage)

        charinfoStatus.text = charInfo.status ?: unknownLabel
        charinfoSpecies.text = charInfo.species ?: unknownLabel
        charinfoGender.text = charInfo.gender ?: unknownLabel

        val locationId = charInfo.locationId
        if (locationId != null) {
            lifecycleScope.launch {
                viewModel.getLocation(locationId).collect { setLocationInfo(it) }
            }
        } else {
            setLocationInfo(null)
        }
    }

    private fun setLocationInfo(location: ShowLocation?) = with(binding) {
        locationName.text = location?.name ?: unknownLabel
        locationType.text = location?.type ?: unknownLabel
        locationDimensions.text = location?.dimension ?: unknownLabel
    }
}