package com.epmedu.animeal.api.message

import DeleteFavouriteMutation
import com.epmedu.animeal.api.AnimealApi
import com.epmedu.animeal.common.data.wrapper.ApiResult
import type.DeleteFavouriteInput

internal class MessageApiImpl(
    private val animealApi: AnimealApi
) : MessageApi {

    override suspend fun sendMessage(
        feedingPointId: String,
        messageType: String,
        body: String
    ): ApiResult<String> {
        if (body == "Test") {
            val mutation = DeleteFavouriteMutation(
                DeleteFavouriteInput.builder()
                    .id(feedingPointId)
                    .build(),
                null
            )
            return animealApi.launchMutation(
                mutation = mutation,
                responseClass = String::class.java
            )
        }
        return ApiResult.Success(feedingPointId)
        /*return animealApi.launchMutation(
            mutation = CreateMessageMutation(feedingPointId, messageType, body),
            responseClass = String::class.java
        )*/
    }
}