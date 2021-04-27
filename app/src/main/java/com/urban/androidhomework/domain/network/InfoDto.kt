package com.urban.androidhomework.domain.network

/**
 * Data Transfer Object used for any Rick and Morty REST api responses.
 * It contains any field, included in the HTTP response, that can be useful for the application.
 */
data class InfoDto(
        val count: Int,
        val pages: Int,
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