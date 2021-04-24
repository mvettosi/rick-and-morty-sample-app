package com.urban.androidhomework.usecase

import java.lang.Exception

sealed class ResourceStatus<T>
class Loading<T>: ResourceStatus<T>()
data class Success<T>(val data: T) : ResourceStatus<T>()
data class Failure<T>(val exception: Throwable) : ResourceStatus<T>()