package com.epmedu.animeal.push.data.repository

import com.epmedu.animeal.api.push.PushApi
import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.push.data.mapper.toActionResult
import com.epmedu.animeal.push.domain.repository.PushRepository
import javax.inject.Inject

internal class PushRepositoryImpl @Inject constructor(
    private val pushApi: PushApi
) : PushRepository {

    override suspend fun registerDevice(
        deviceToken: String,
    ): ActionResult<Unit> {
        return pushApi.registerDevice(deviceToken).toActionResult()
    }
}