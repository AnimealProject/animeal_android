package com.epmedu.animeal.common.domain.wrapper

sealed interface ActionResult {

    object Success : ActionResult

    data class Failure(val error: Throwable) : ActionResult
}