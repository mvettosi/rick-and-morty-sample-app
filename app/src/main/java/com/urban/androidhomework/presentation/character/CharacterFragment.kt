package com.urban.androidhomework.presentation.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.urban.androidhomework.R
import com.urban.androidhomework.databinding.FragmentCharacterBinding
import com.urban.androidhomework.databinding.HomeFragmentBinding
import com.urban.androidhomework.domain.model.ShowLocation
import com.urban.androidhomework.presentation.general.GlideApp
import com.urban.androidhomework.presentation.home.HomeViewModel
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterFragment : Fragment(R.layout.fragment_character) {
    private val viewModel by viewModels<CharacterViewModel>()
    private val binding by viewBinding(FragmentCharacterBinding::bind)
    private val args: CharacterFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.selectedName.text = args.charInfo.name
        initView()
    }

    private fun initView() = with(binding) {
        selectedName.text = args.charInfo.name

        GlideApp.with(binding.root.context)
                .load(args.charInfo.image ?: R.raw.unnamed)
                .into(characterImage)

        args.charInfo.status?.let { charinfoStatus.text = it }
        args.charInfo.species?.let { charinfoSpecies.text = it }
        args.charInfo.gender?.let { charinfoGender.text = it }

        val locationId = args.charInfo.locationId
        if (locationId != null) {
            lifecycleScope.launch {
                viewModel.getLocation(locationId).collect { setLocationInfo(it)}
            }
        } else {
            setLocationInfo(null)
        }
    }

    private fun setLocationInfo(location: ShowLocation?) = with(binding) {
        val unknown = getString(R.string.unknown)
        locationName.text = location?.name ?: unknown
        locationType.text = location?.type ?: unknown
        locationDimensions.text = location?.dimension ?: unknown
    }
}