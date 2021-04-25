package com.urban.androidhomework.presentation.home

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.urban.androidhomework.domain.Character

class CharactersAdapter: PagingDataAdapter<Character.CharacterData, CharacterViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            CharacterViewHolder.create(parent)

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) =
            holder.bind(getItem(position))

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Character.CharacterData>() {
            override fun areItemsTheSame(oldItem: Character.CharacterData, newItem: Character.CharacterData): Boolean =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Character.CharacterData, newItem: Character.CharacterData): Boolean =
                    oldItem == newItem
        }
    }
}