package com.epmedu.animeal.api.wrapper

sealed interface ApiResult<out T> {

    data class Success<out T : Any>(val data: T) : ApiResult<T>

    data class Failure<T>(val error: Throwable) : ApiResult<T>
}