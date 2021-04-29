package com.urban.androidhomework.presentation.home

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

/**
 * Simple adapter for the Home Fragment's paged list Load State representation
 */
class CharacterLoadStateAdapter(
        private val retry: () -> Unit
) : LoadStateAdapter<CharacterLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: CharacterLoadStateViewHolder, loadState: LoadState) =
            holder.bind(loadState)

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
            CharacterLoadStateViewHolder.create(parent, retry)
}