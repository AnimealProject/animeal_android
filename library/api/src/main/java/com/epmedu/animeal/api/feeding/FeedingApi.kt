package com.epmedu.animeal.api.feeding

import SearchFeedingsQuery
import com.amplifyframework.datastore.generated.model.Feeding
import com.epmedu.animeal.common.data.wrapper.ApiResult
import kotlinx.coroutines.flow.Flow
import type.FeedingStatus
import type.FeedingStatus.inProgress
import type.FeedingStatus.pending

interface FeedingApi {

    fun getUserFeedings(userId: String): Flow<List<Feeding>>

    suspend fun getAllFeedings(): ApiResult<SearchFeedingsQuery.Data>

    /**
     * Returns feedings filtered by parameters that are not `null`.
     * @param feedingPointId Id of associated feeding point.
     * @param assignedModeratorId Id of assigned moderator.
     * @param status Feeding status of feedings.
     * Can be [inProgress] or [pending].
     * `null` by default.
     */
    suspend fun getFeedingsBy(
        feedingPointId: String? = null,
        assignedModeratorId: String? = null,
        status: FeedingStatus? = null,
    ): ApiResult<SearchFeedingsQuery.Data>

    fun subscribeToFeedingsUpdates(): Flow<OnUpdateFeedingExtSubscription.Data>

    fun subscribeToFeedingsCreation(): Flow<OnCreateFeedingExtSubscription.Data>

    fun subscribeToFeedingsDeletion(): Flow<OnDeleteFeedingExtSubscription.Data>
}