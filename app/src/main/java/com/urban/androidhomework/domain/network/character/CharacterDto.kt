package com.urban.androidhomework.domain.network.character

import java.util.*

/**
 * Data Transfer Object for Rick and Morty characters, as returned from the remote api.
 * It contains any field, included in the REST response, that can be useful for the application.
 *
 * Note: because of how the default Gson deserializer works, any field in a DTO could be silently
 * set to null, so we need to declare all of them as nullable to ensure we are dealing with them.
 * Model validation and/or usage of a different deserializer is not in place because of time
 * restrictions and because overall we don't want to reject an entire list of dtos because of one
 * malformed one.
 */
data class CharacterDto(
        val id: Int? = null,
        val name: String? = null,
        val created: Date? = null,
        val status: String? = null,
        val species: String? = null,
        val gender: String? = null,
        val image: String? = null,
        val location: Location? = null
) {
    data class Location(
            val name: String? = null,
            val url: String? = null
    )

    fun getLocationId(): Int? =
            if (location?.url != null && LocationUrl.REGEX matches location.url) {
                location.url.substringAfter(LocationUrl.PREFIX).toInt()
            } else {
                null
            }

    private object LocationUrl {
        const val HTTPS = "https://"
        const val PREFIX = "location/"
        val REGEX = "$HTTPS[\\wT./]+/$PREFIX\\d+".toRegex()
    }
}