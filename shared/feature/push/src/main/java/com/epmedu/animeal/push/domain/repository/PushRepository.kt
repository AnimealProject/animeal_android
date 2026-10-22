package com.epmedu.animeal.push.domain.repository

import com.epmedu.animeal.common.domain.wrapper.ActionResult

@Suppress("ComplexInterface")
interface PushRepository {

    suspend fun registerDevice(
        deviceToken: String
    ): ActionResult<Unit>
}