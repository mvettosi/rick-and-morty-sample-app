package com.urban.androidhomework.presentation.general

import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


fun Date.toLocalDate() = toInstant().atZone(ZoneId.systemDefault()).toLocalDate()

fun Date.toDateString() = toLocalDate().format(DateTimeFormatter.ofPattern("dd MMMM, yyyy"))