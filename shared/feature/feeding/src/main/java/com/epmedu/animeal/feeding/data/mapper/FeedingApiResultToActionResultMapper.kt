package com.epmedu.animeal.feeding.data.mapper

import com.epmedu.animeal.common.data.wrapper.ApiResult
import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.feeding.data.error.WrongResponseError

internal fun ApiResult<String>.toActionResult(id: String): ActionResult<Unit> {
    return when (this) {
        is ApiResult.Success -> {
            when {
                data.contains(id) -> ActionResult.Success(Unit)
                else -> ActionResult.Failure(WrongResponseError())
            }
        }
        is ApiResult.Failure -> {
            ActionResult.Failure(error)
        }
    }
}