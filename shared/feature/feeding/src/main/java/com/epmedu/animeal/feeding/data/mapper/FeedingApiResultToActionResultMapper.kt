package com.epmedu.animeal.feeding.data.mapper

import com.epmedu.animeal.api.wrapper.ApiResult
import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.feeding.data.error.WrongResponseError

internal fun ApiResult<String>.toActionResult(feedingPointId: String): ActionResult {
    return when (this) {
        is ApiResult.Success -> {
            when {
                data.contains(feedingPointId) -> ActionResult.Success
                else -> ActionResult.Failure(WrongResponseError())
            }
        }
        is ApiResult.Failure -> ActionResult.Failure(error)
    }
}