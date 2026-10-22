package com.epmedu.animeal.api.push

import com.epmedu.animeal.common.data.wrapper.ApiResult

interface PushApi {

    suspend fun registerDevice(
        deviceToken: String
    ): ApiResult<String>
}