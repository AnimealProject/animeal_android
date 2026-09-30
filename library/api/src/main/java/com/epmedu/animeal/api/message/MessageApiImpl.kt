package com.epmedu.animeal.api.message

import CreateFeedingPointIssueMutation
import com.epmedu.animeal.api.AnimealApi
import com.epmedu.animeal.common.data.wrapper.ApiResult

internal class MessageApiImpl(
    private val animealApi: AnimealApi,
) : MessageApi {

    override suspend fun createFeedingPointIssue(
        feedingPointId: String,
        body: String,
        images: List<String>
    ): ApiResult<String> {
        val mutation = CreateFeedingPointIssueMutation(
            feedingPointId,
            body,
            images
        )
        return animealApi.launchMutation(
            mutation = mutation,
            responseClass = String::class.java
        )
    }
}