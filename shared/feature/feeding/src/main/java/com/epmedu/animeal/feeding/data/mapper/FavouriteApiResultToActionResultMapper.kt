package com.epmedu.animeal.feeding.data.mapper

import com.epmedu.animeal.common.data.wrapper.ApiResult
import com.epmedu.animeal.common.domain.wrapper.ActionResult

internal fun ApiResult<Unit>?.toActionResult() = when (this) {
    is ApiResult.Success -> ActionResult.Success(Unit)
    is ApiResult.Failure -> ActionResult.Failure(error)
    else -> ActionResult.Failure(IllegalStateException("Favourite feeding point is not found"))
}