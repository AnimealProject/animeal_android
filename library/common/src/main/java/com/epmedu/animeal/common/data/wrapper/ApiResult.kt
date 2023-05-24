package com.epmedu.animeal.common.data.wrapper

sealed class ApiResult<out T>(open val data: T?) {

    data class Success<out T : Any>(override val data: T) : ApiResult<T>(data)

    data class Failure<T>(val error: Throwable) : ApiResult<T>(null)
}