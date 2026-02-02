package com.epmedu.animeal.api.feeding

import OnCreateFeedingHistoryExtSubscription
import com.epmedu.animeal.api.AnimealApi
import com.epmedu.animeal.common.data.wrapper.ApiResult
import kotlinx.coroutines.flow.Flow
import type.FeedingStatus

internal class FeedingHistoryApiImpl(
    private val animealApi: AnimealApi
) : FeedingHistoryApi {

    override suspend fun getAllFeedingHistories(): ApiResult<GetHistoricalFeedingsQuery.Data> {
        return animealApi.launchQuery(
            query = GetHistoricalFeedingsQuery.builder()
                .build(),
            responseClass = GetHistoricalFeedingsQuery.Data::class.java
        )
    }

    override suspend fun getFeedingHistoriesBy(
        feedingPointId: String?,
        assignedModeratorId: String?,
        status: FeedingStatus?
    ): ApiResult<GetHistoricalFeedingsQuery.Data> {
        val requestBuilder = GetHistoricalFeedingsQuery.builder()

        feedingPointId?.let {
            requestBuilder.feedingPointId(feedingPointId)
        }
        status?.name?.let {
            requestBuilder.status(status.name)
        }
        assignedModeratorId?.let {
            requestBuilder.moderatorId(assignedModeratorId)
        }

        return animealApi.launchQuery(
            query = requestBuilder.build(),
            responseClass = GetHistoricalFeedingsQuery.Data::class.java
        )
    }

    override fun subscribeToFeedingHistoriesCreation(): Flow<OnCreateFeedingHistoryExtSubscription.Data> {
        return animealApi.launchSubscription(
            subscription = OnCreateFeedingHistoryExtSubscription.builder().build(),
            responseClass = OnCreateFeedingHistoryExtSubscription.Data::class.java
        )
    }
}