package com.urban.androidhomework.presentation.general

import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Extension function to easily convert a Date instance into a LocalDate one that uses the default
 * zone id.
 */
fun Date.toLocalDate() = toInstant().atZone(ZoneId.systemDefault()).toLocalDate()

/**
 * Extension function to quickly convert a Date class into a human readable format for UI display
 */
fun Date.toDateString() = toLocalDate().format(DateTimeFormatter.ofPattern("dd MMMM, yyyy"))