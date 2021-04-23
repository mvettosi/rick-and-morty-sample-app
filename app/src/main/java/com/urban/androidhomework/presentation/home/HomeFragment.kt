package com.urban.androidhomework.presentation.home

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.urban.androidhomework.R
import com.urban.androidhomework.databinding.HomeFragmentBinding
import com.urban.androidhomework.domain.Character
import com.urban.androidhomework.presentation.utils.CharactersAdapter
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.home_fragment) {
    private val viewModel by viewModels<HomeViewModel>()
    private val binding by viewBinding(HomeFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.characters.observe(viewLifecycleOwner, ::updateCharacterList)
    }

    private fun updateCharacterList(character: Character) {
        val adapter = CharactersAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                character.results.map { it.name }.toTypedArray()
        )
        binding.charactersList.adapter = adapter
        binding.charactersList.setOnItemClickListener { parent, _, position, _ ->
            val name = parent.getItemAtPosition(position) as String
            findNavController().navigate(HomeFragmentDirections.actionCharacter(name))
        }
    }
}