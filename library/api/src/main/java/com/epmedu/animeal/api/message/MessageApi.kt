package com.epmedu.animeal.api.message

import com.epmedu.animeal.common.data.wrapper.ApiResult

interface MessageApi {

    suspend fun sendMessage(feedingPointId: String, messageType: String, body: String): ApiResult<String>
}