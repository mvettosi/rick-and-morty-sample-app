package com.urban.androidhomework.domain.network.location

/**
 * Data Transfer Object for Rick and Morty locations, as returned from the remote api.
 * It contains any field, included in the REST response, that can be useful for the application.
 *
 * Note: because of how the default Gson deserializer works, any field in a DTO could be silently
 * set to null, so we need to declare all of them as nullable to ensure we are dealing with them.
 * Model validation and/or usage of a different deserializer is not in place because of time
 * restrictions and because overall we don't want to reject an entire list of dtos because of one
 * malformed one.
 */
data class LocationDto (
        val id: Int? = null,
        val name: String? = null,
        val type: String? = null,
        val dimension: String? = null,
        val residents: List<String?>? = null
)