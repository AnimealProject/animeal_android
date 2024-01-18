package com.epmedu.animeal.api.feeding

import SearchFeedingHistoriesQuery
import com.amplifyframework.datastore.generated.model.Feeding
import com.epmedu.animeal.common.data.wrapper.ApiResult
import kotlinx.coroutines.flow.Flow
import type.FeedingStatus
import type.FeedingStatus.inProgress
import type.FeedingStatus.pending
import type.FeedingStatus.approved
import type.FeedingStatus.rejected
import type.FeedingStatus.outdated

interface FeedingApi {

    fun getUserFeedings(userId: String): Flow<List<Feeding>>

    suspend fun getAllFeedings(): ApiResult<SearchFeedingsQuery.Data>

    /**
     * Returns feedings with provided [status] associated to provided [feedingPointId].
     * `null` in [status] means ignoring filtering by it.
     * @param feedingPointId Id of associated feeding point.
     * @param status Feeding status of feeding histories.
     * Can be [inProgress] or [pending].
     * `null` by default.
     */
    suspend fun getFeedingsBy(
        feedingPointId: String,
        status: FeedingStatus? = null
    ): ApiResult<SearchFeedingsQuery.Data>

    suspend fun getAllFeedingHistories(): ApiResult<SearchFeedingHistoriesQuery.Data>

    /**
     * Returns feeding histories with provided [status] associated to provided [feedingPointId].
     * `null` in [status] means ignoring filtering by it.
     * @param feedingPointId Id of associated feeding point.
     * @param status Feeding status of feeding histories.
     * Can be [approved], [rejected] or [outdated].
     * `null` by default.
     */
    suspend fun getFeedingHistoriesBy(
        feedingPointId: String,
        status: FeedingStatus? = null
    ): ApiResult<SearchFeedingHistoriesQuery.Data>

    suspend fun startFeeding(feedingPointId: String): ApiResult<String>

    suspend fun cancelFeeding(feedingPointId: String): ApiResult<String>

    suspend fun rejectFeeding(feedingPointId: String, reason: String): ApiResult<String>

    suspend fun finishFeeding(feedingPointId: String, images: List<String>): ApiResult<String>
}