package com.urban.androidhomework.domain

data class Character(
    val results: List<CharacterData>
) {
    data class CharacterData(
        val id: Int,
        val name: String
    )
}
