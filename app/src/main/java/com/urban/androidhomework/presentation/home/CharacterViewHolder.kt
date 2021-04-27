package com.urban.androidhomework.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.urban.androidhomework.databinding.CharacterViewItemBinding
import com.urban.androidhomework.domain.model.ShowCharacter

class CharacterViewHolder(
        private val binding: CharacterViewItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(character: ShowCharacter?) = with(binding) {
        if (character?.name != null) {
            characterName.text = character.name
            binding.root.setOnClickListener { view ->
                view.findNavController().navigate(HomeFragmentDirections.actionCharacter(character.name))
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): CharacterViewHolder {
            val binding = CharacterViewItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
            )
            return CharacterViewHolder(binding)
        }
    }
}