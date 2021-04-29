package com.urban.androidhomework.presentation.home

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.urban.androidhomework.domain.model.ShowCharacter
import javax.inject.Inject

/**
 * PagingDataAdapter for the paged list of characters displayed in the Home Fragment
 */
class CharactersAdapter @Inject constructor()
    : PagingDataAdapter<ShowCharacter, CharacterViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            CharacterViewHolder.create(parent)

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) =
            holder.bind(getItem(position))

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<ShowCharacter>() {
            override fun areItemsTheSame(oldItem: ShowCharacter, newItem: ShowCharacter): Boolean =
                    oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: ShowCharacter, newItem: ShowCharacter): Boolean =
                    oldItem == newItem
        }
    }
}