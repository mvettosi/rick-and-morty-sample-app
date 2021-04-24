package com.urban.androidhomework.presentation.home

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.urban.androidhomework.R
import com.urban.androidhomework.databinding.HomeFragmentBinding
import com.urban.androidhomework.domain.Character
import com.urban.androidhomework.presentation.utils.CharactersAdapter
import com.urban.androidhomework.usecase.Failure
import com.urban.androidhomework.usecase.Loading
import com.urban.androidhomework.usecase.ResourceStatus
import com.urban.androidhomework.usecase.Success
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.home_fragment) {
    private val viewModel by viewModels<HomeViewModel>()
    private val binding by viewBinding(HomeFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.characters.observe(viewLifecycleOwner, ::handleCharaterUpdate)
        binding.swiperefresh.setOnRefreshListener(viewModel::loadCharacters)
    }

    private fun handleCharaterUpdate(status: ResourceStatus<Character>) = when (status) {
        is Loading -> startLoading()
        is Success -> updateCharacterList(status.data)
        is Failure -> errorLoading()
    }

    private fun startLoading() = with(binding) {
        swiperefresh.isRefreshing = true
    }

    private fun updateCharacterList(character: Character) = with(binding) {
        swiperefresh.isRefreshing = false

        val adapter = CharactersAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                character.results.map { it.name }.toTypedArray()
        )
        charactersList.adapter = adapter
        charactersList.setOnItemClickListener { parent, _, position, _ ->
            val name = parent.getItemAtPosition(position) as String
            findNavController().navigate(HomeFragmentDirections.actionCharacter(name))
        }

    }

    private fun errorLoading() = with(binding) {
        swiperefresh.isRefreshing = false

        Snackbar.make(root, R.string.error_loading, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.action_retry) { viewModel.loadCharacters() }
                .show()
    }
}