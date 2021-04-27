package com.urban.androidhomework.domain.model

import java.util.*

/**
 * Data class representing a Rick and Morty character.
 * It contains any data related to a character that is used for the application business logic or
 * representation.
 */
data class ShowCharacter(
        val name: String,
        val created: Date
)