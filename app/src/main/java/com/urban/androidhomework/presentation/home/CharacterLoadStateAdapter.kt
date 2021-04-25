package com.urban.androidhomework.presentation.home

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class CharacterLoadStateAdapter(
        private val retry: () -> Unit
) : LoadStateAdapter<CharacterLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: CharacterLoadStateViewHolder, loadState: LoadState) =
            holder.bind(loadState)

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
            CharacterLoadStateViewHolder.create(parent, retry)
}