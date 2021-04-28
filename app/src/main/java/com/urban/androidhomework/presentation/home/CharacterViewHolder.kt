package com.urban.androidhomework.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.urban.androidhomework.R
import com.urban.androidhomework.databinding.CharacterViewItemBinding
import com.urban.androidhomework.domain.model.ShowCharacter
import com.urban.androidhomework.presentation.general.GlideApp
import com.urban.androidhomework.presentation.general.toDateString

class CharacterViewHolder(
        private val binding: CharacterViewItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(character: ShowCharacter?) = with(binding) {
        characterName.text = character?.name ?: binding.root.context.getString(R.string.unknown)
        characterDate.text = character?.created?.toDateString()

        GlideApp.with(binding.root.context)
                .load(character?.image ?: R.raw.unnamed)
                .into(characterImage)

        binding.root.setOnClickListener { view ->
            view.findNavController().navigate(
                    HomeFragmentDirections
                            .actionCharacter()
                            .apply { charInfo = character }
            )
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