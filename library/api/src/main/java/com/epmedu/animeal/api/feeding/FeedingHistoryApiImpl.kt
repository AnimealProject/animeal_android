package com.epmedu.animeal.api.feeding

import OnCreateFeedingHistoryExtSubscription
import SearchFeedingHistoriesQuery
import com.epmedu.animeal.api.AnimealApi
import com.epmedu.animeal.api.feeding.FeedingFilters.feedingsCreatedAtFilterInput
import com.epmedu.animeal.common.data.wrapper.ApiResult
import kotlinx.coroutines.flow.Flow
import type.FeedingStatus
import type.SearchableFeedingHistoryFilterInput
import type.SearchableStringFilterInput

internal class FeedingHistoryApiImpl(
    private val animealApi: AnimealApi
) : FeedingHistoryApi {

    override suspend fun getAllFeedingHistories(): ApiResult<SearchFeedingHistoriesQuery.Data> {
        return animealApi.launchQuery(
            query = SearchFeedingHistoriesQuery.builder()
                .filter(
                    SearchableFeedingHistoryFilterInput.builder()
                        .createdAt(feedingsCreatedAtFilterInput)
                        .build()
                )
                .build(),
            responseClass = SearchFeedingHistoriesQuery.Data::class.java
        )
    }

    override suspend fun getFeedingHistoriesBy(
        feedingPointId: String?,
        assignedModeratorId: String?,
        status: FeedingStatus?
    ): ApiResult<SearchFeedingHistoriesQuery.Data> {
        val filterBuilder = SearchableFeedingHistoryFilterInput.builder()

        feedingPointId?.let {
            filterBuilder.feedingPointId(
                SearchableStringFilterInput.builder()
                    .eq(feedingPointId)
                    .build()
            )
        }
        status?.name?.let {
            filterBuilder.status(
                SearchableStringFilterInput.builder()
                    .eq(status.name)
                    .build()
            )
        }
        assignedModeratorId?.let {
            filterBuilder.assignedModerators(
                SearchableStringFilterInput.builder()
                    .eq(assignedModeratorId)
                    .build()
            )
        }

        val query = SearchFeedingHistoriesQuery.builder()
            .filter(filterBuilder.build())
            .build()

        return animealApi.launchQuery(
            query = query,
            responseClass = SearchFeedingHistoriesQuery.Data::class.java
        )
    }

    override fun subscribeToFeedingHistoriesCreation(): Flow<OnCreateFeedingHistoryExtSubscription.Data> {
        return animealApi.launchSubscription(
            subscription = OnCreateFeedingHistoryExtSubscription.builder().build(),
            responseClass = OnCreateFeedingHistoryExtSubscription.Data::class.java
        )
    }
}