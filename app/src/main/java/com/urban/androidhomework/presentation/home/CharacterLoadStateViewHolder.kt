package com.urban.androidhomework.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.urban.androidhomework.databinding.CharacterLoadingViewItemBinding

/**
 * View Holder of the Load State of an item in the Home Fragment's paged character list.
 * It is responsible for initialising and updating the UI elements that display information regarding
 * the loading state of an item, including an error message and a retry button.
 */
class CharacterLoadStateViewHolder(
        private val binding: CharacterLoadingViewItemBinding,
        retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.retryButton.setOnClickListener { retry() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState is LoadState.Error
        binding.errorMsg.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): CharacterLoadStateViewHolder {
            val binding = CharacterLoadingViewItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
            )
            return CharacterLoadStateViewHolder(binding, retry)
        }
    }
}