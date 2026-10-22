package com.epmedu.animeal.push.data.mapper

import com.epmedu.animeal.common.data.wrapper.ApiResult
import com.epmedu.animeal.common.domain.wrapper.ActionResult

internal fun ApiResult<String>.toActionResult(): ActionResult<Unit> {
    return when (this) {
        is ApiResult.Success -> ActionResult.Success(Unit)
        is ApiResult.Failure -> ActionResult.Failure(error)
    }
}