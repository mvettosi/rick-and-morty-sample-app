package com.urban.androidhomework.domain.model

/**
 * Data class representing a Rick and Morty location.
 * It contains any data related to a character location that is used for the application business
 * logic or representation.
 */
data class ShowLocation (
        val name: String?,
        val type: String?,
        val dimension: String?,
        val residents: List<String>?
)