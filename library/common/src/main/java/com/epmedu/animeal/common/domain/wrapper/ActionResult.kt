package com.epmedu.animeal.common.domain.wrapper

sealed interface ActionResult<out T> {

    data class Success<out T : Any>(val result: T) : ActionResult<T>

    data class Failure(val error: Throwable) : ActionResult<Nothing>
}