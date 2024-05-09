package com.epmedu.animeal.api.feeding

import OnCreateFeedingHistoryExtSubscription
import SearchFeedingHistoriesQuery
import com.epmedu.animeal.common.data.wrapper.ApiResult
import kotlinx.coroutines.flow.Flow
import type.FeedingStatus
import type.FeedingStatus.approved
import type.FeedingStatus.outdated
import type.FeedingStatus.rejected

interface FeedingHistoryApi {

    suspend fun getAllFeedingHistories(): ApiResult<SearchFeedingHistoriesQuery.Data>

    /**
     * Returns feeding histories filtered by parameters that are not `null`.
     * @param feedingPointId Id of associated feeding point.
     * @param assignedModeratorId Id of assigned moderator.
     * @param status Feeding status of feeding histories.
     * Can be [approved], [rejected] or [outdated].
     * `null` by default.
     */
    suspend fun getFeedingHistoriesBy(
        feedingPointId: String? = null,
        assignedModeratorId: String? = null,
        status: FeedingStatus? = null
    ): ApiResult<SearchFeedingHistoriesQuery.Data>

    fun subscribeToFeedingHistoriesCreation(): Flow<OnCreateFeedingHistoryExtSubscription.Data>
}