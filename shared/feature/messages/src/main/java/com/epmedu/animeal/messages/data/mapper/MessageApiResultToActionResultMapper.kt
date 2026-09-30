package com.epmedu.animeal.messages.data.mapper

import com.epmedu.animeal.common.data.wrapper.ApiResult
import com.epmedu.animeal.common.domain.wrapper.ActionResult

internal fun ApiResult<String>.toActionResult(id: String): ActionResult<Unit> {
    return when (this) {
        is ApiResult.Success -> {
            when {
                data.contains(id) -> ActionResult.Success(Unit)
                else -> ActionResult.Failure(
                    IllegalStateException("Message with id=$id was not found in the response")
                )
            }
        }
        is ApiResult.Failure -> {
            ActionResult.Failure(error)
        }
    }
}