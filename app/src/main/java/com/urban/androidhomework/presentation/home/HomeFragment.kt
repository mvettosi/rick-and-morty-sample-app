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
import com.google.android.material.datepicker.MaterialDatePicker
import com.urban.androidhomework.R
import com.urban.androidhomework.databinding.HomeFragmentBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import java.time.ZoneId
import java.util.*
import javax.inject.Inject

/**
 * Fragment containing the logic for the landing page of the app, displaying the list of characters
 * along with image and creation date.
 * These characters are loaded on demand while scrolling the list, are recycled when possible, and
 * it is possible to filter them by creation date without the need to download them again.
 */
@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.home_fragment) {
    private val viewModel by viewModels<HomeViewModel>()
    private val binding by viewBinding(HomeFragmentBinding::bind)

    @Inject lateinit var charactersAdapter: CharactersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        loadCharacters()
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
            datetimeFilter.isVisible = refreshState is LoadState.NotLoading
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

        datetimeFilter.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .build()
            datePicker.addOnPositiveButtonClickListener { dateSelected ->
                loadCharacters(Date(dateSelected))
            }
            datePicker.show(childFragmentManager, datePicker.toString())
        }

        cancelFilter.setOnClickListener { loadCharacters() }
    }

    private fun loadCharacters(dateFilter: Date? = null) {
        lifecycleScope.launch {
            viewModel.getCharacterFlow(dateFilter).collect {
                charactersAdapter.submitData(it)
            }
        }
        binding.cancelFilter.isVisible = dateFilter != null
    }
}