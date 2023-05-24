package com.epmedu.animeal.api.feeding

import SearchFeedingHistoriesQuery
import com.amplifyframework.datastore.generated.model.Feeding
import com.epmedu.animeal.common.data.wrapper.ApiResult
import kotlinx.coroutines.flow.Flow

interface FeedingApi {

    fun getUserFeedings(userId: String): Flow<List<Feeding>>

    suspend fun getFeedingsInProgress(feedingPointId: String): ApiResult<SearchFeedingsQuery.Data>

    suspend fun getApprovedFeedingHistories(feedingPointId: String): ApiResult<SearchFeedingHistoriesQuery.Data>

    suspend fun startFeeding(feedingPointId: String): ApiResult<String>

    suspend fun cancelFeeding(feedingPointId: String): ApiResult<String>

    suspend fun rejectFeeding(feedingPointId: String, reason: String): ApiResult<String>

    suspend fun finishFeeding(feedingPointId: String, images: List<String>): ApiResult<String>
}