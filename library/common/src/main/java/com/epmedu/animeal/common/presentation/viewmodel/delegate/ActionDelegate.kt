package com.epmedu.animeal.common.presentation.viewmodel.delegate

import com.epmedu.animeal.common.domain.wrapper.ActionResult

interface ActionDelegate {

    suspend fun performAction(
        action: suspend () -> ActionResult<Unit>,
        onSuccess: suspend () -> Unit = {},
        onError: () -> Unit = {},
    )

    suspend fun <T> performAction(
        action: suspend () -> ActionResult<T>,
        onSuccess: suspend (T) -> Unit,
        onError: () -> Unit
    )

    suspend fun <T> performAction(
        action: suspend () -> T
    ): T
}