package com.epmedu.animeal.api.message

import com.epmedu.animeal.common.data.wrapper.ApiResult

interface MessageApi {

    suspend fun createFeedingPointIssue(
        feedingPointId: String,
        body: String,
        images: List<String>
    ): ApiResult<String>
}