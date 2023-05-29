package com.epmedu.animeal.api.feeding

import CancelFeedingMutation
import FinishFeedingMutation
import RejectFeedingMutation
import SearchFeedingHistoriesQuery
import SearchFeedingsQuery
import StartFeedingMutation
import com.amplifyframework.datastore.generated.model.Feeding
import com.epmedu.animeal.api.AnimealApi
import com.epmedu.animeal.common.data.wrapper.ApiResult
import kotlinx.coroutines.flow.Flow
import type.SearchableFeedingFilterInput
import type.SearchableFeedingHistoryFilterInput
import type.SearchableIDFilterInput
import type.SearchableStringFilterInput

internal class FeedingApiImpl(
    private val animealApi: AnimealApi
) : FeedingApi {

    override fun getUserFeedings(userId: String): Flow<List<Feeding>> {
        return animealApi.getModelList(
            predicate = Feeding.USER_ID.eq(userId),
            modelClass = Feeding::class.java
        )
    }

    override suspend fun getFeedingsInProgress(feedingPointId: String): ApiResult<SearchFeedingsQuery.Data> {
        val query = SearchFeedingsQuery.builder()
            .filter(
                SearchableFeedingFilterInput.builder()
                    .feedingPointFeedingsId(
                        SearchableIDFilterInput.builder()
                            .eq(feedingPointId)
                            .build()
                    )
                    .build()
            )
            .build()
        return animealApi.launchQuery(
            query = query,
            responseClass = SearchFeedingsQuery.Data::class.java
        )
    }

    override suspend fun getApprovedFeedingHistories(feedingPointId: String): ApiResult<SearchFeedingHistoriesQuery.Data> {
        val query = SearchFeedingHistoriesQuery.builder()
            .filter(
                SearchableFeedingHistoryFilterInput.builder()
                    .status(
                        SearchableStringFilterInput.builder()
                            .eq(APPROVED_FILTER)
                            .build()
                    )
                    .feedingPointId(
                        SearchableStringFilterInput.builder()
                            .eq(feedingPointId)
                            .build()
                    )
                    .build()
            )
            .build()
        return animealApi.launchQuery(
            query = query,
            responseClass = SearchFeedingHistoriesQuery.Data::class.java
        )
    }

    override suspend fun startFeeding(feedingPointId: String): ApiResult<String> {
        return animealApi.launchMutation(
            mutation = StartFeedingMutation(feedingPointId),
            responseClass = String::class.java
        )
    }

    override suspend fun cancelFeeding(feedingPointId: String): ApiResult<String> {
        return animealApi.launchMutation(
            mutation = CancelFeedingMutation(feedingPointId, CANCEL_FEEDING_REASON),
            responseClass = String::class.java
        )
    }

    override suspend fun rejectFeeding(feedingPointId: String, reason: String): ApiResult<String> {
        return animealApi.launchMutation(
            mutation = RejectFeedingMutation(feedingPointId, reason, null),
            responseClass = String::class.java
        )
    }

    override suspend fun finishFeeding(
        feedingPointId: String,
        images: List<String>
    ): ApiResult<String> {
        return animealApi.launchMutation(
            mutation = FinishFeedingMutation(feedingPointId, images),
            responseClass = String::class.java
        )
    }

    private companion object {
        const val APPROVED_FILTER = "approved"
        const val CANCEL_FEEDING_REASON = "reason"
    }
}