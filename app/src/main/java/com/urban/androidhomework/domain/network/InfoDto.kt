package com.urban.androidhomework.domain.network

/**
 * Data Transfer Object used for any Rick and Morty REST api responses.
 * It contains any field, included in the HTTP response, that can be useful for the application.
 *
 * Note: because of how the default Gson deserializer works, any field in a DTO could be silently
 * set to null, so we need to declare all of them as nullable to ensure we are dealing with them.
 * Model validation and/or usage of a different deserializer is not in place because of time
 * restrictions and because overall we don't want to reject an entire list of dtos because of one
 * malformed one.
 */
data class InfoDto(
        val count: Int?,
        val pages: Int?,
        val next: String?,
        val prev: String?
) {
    fun getPrevPage() = getPageFrom(prev)
    fun getNextPage() = getPageFrom(next)

    private fun getPageFrom(url: String?): Int? =
            if (url != null && PageUrl.REGEX matches url) {
                url.substringAfter(PageUrl.PREFIX).toInt()
            } else {
                null
            }

    private object PageUrl {
        const val HTTPS = "https://"
        const val PREFIX = "page="
        val REGEX = "$HTTPS[\\wT./]+\\?$PREFIX\\d+".toRegex()
    }
}