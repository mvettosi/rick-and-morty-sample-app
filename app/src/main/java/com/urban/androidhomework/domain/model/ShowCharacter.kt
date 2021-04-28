package com.urban.androidhomework.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

/**
 * Data class representing a Rick and Morty character.
 * It contains any data related to a character that is used for the application business logic or
 * representation.
 */
@Parcelize
data class ShowCharacter(
        val name: String,
        val created: Date,
        val status: String?,
        val species: String?,
        val gender: String?,
        val image: String?,
        val locationId: Int?
) : Parcelable