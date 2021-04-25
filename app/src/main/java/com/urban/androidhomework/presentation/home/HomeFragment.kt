package com.urban.androidhomework.presentation.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.urban.androidhomework.R
import com.urban.androidhomework.databinding.HomeFragmentBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.home_fragment) {
    private val viewModel by viewModels<HomeViewModel>()
    private val binding by viewBinding(HomeFragmentBinding::bind)

    private val charactersAdapter = CharactersAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        lifecycleScope.launch {
            viewModel.characters.collect {
                charactersAdapter.submitData(it)
            }
        }
    }

    private fun initView() = with(binding) {
        characterList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = charactersAdapter.withLoadStateHeaderAndFooter(
                    header = CharacterLoadStateAdapter { charactersAdapter.retry() },
                    footer = CharacterLoadStateAdapter { charactersAdapter.retry() }
            )
        }

        charactersAdapter.addLoadStateListener { loadState ->
            Timber.d("Character list loading state changed: $loadState")
            val refreshState = loadState.source.refresh
            characterList.isVisible = refreshState is LoadState.NotLoading
            progressBar.isVisible = refreshState is LoadState.Loading
            retryButton.isVisible = refreshState is LoadState.Error

            if (refreshState is LoadState.Error) {
                Toast.makeText(
                        requireContext(),
                        "Error: ${refreshState.error}",
                        Toast.LENGTH_LONG
                ).show()
            }
        }

        retryButton.setOnClickListener { charactersAdapter.retry() }
    }
}