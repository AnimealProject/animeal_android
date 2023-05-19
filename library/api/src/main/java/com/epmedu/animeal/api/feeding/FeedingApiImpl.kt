package com.epmedu.animeal.api.feeding

import CancelFeedingMutation
import FinishFeedingMutation
import RejectFeedingMutation
import SearchFeedingHistoriesQuery
import SearchFeedingsQuery
import StartFeedingMutation
import com.amplifyframework.datastore.generated.model.Feeding
import com.epmedu.animeal.api.extensions.getModelList
import com.epmedu.animeal.api.extensions.launch
import com.epmedu.animeal.common.data.wrapper.ApiResult
import kotlinx.coroutines.flow.Flow
import type.SearchableFeedingFilterInput
import type.SearchableFeedingHistoryFilterInput
import type.SearchableIDFilterInput
import type.SearchableStringFilterInput

internal class FeedingApiImpl : FeedingApi {

    override fun getUserFeedings(userId: String): Flow<List<Feeding>> {
        return getModelList(predicate = Feeding.USER_ID.eq(userId))
    }

    override suspend fun getFeedingsInProgress(feedingPointId: String): ApiResult<SearchFeedingsQuery.Data> {
        return SearchFeedingsQuery.builder()
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
            .launch()
    }

    override suspend fun getApprovedFeedingHistories(feedingPointId: String): ApiResult<SearchFeedingHistoriesQuery.Data> {
        return SearchFeedingHistoriesQuery.builder()
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
            .launch()
    }

    override suspend fun startFeeding(feedingPointId: String): ApiResult<String> {
        return StartFeedingMutation(feedingPointId).launch()
    }

    override suspend fun cancelFeeding(feedingPointId: String): ApiResult<String> {
        return CancelFeedingMutation(
            feedingPointId,
            CANCEL_FEEDING_REASON
        ).launch()
    }

    override suspend fun rejectFeeding(feedingPointId: String, reason: String): ApiResult<String> {
        return RejectFeedingMutation(
            feedingPointId,
            reason,
            null
        ).launch()
    }

    override suspend fun finishFeeding(
        feedingPointId: String,
        images: List<String>
    ): ApiResult<String> {
        return FinishFeedingMutation(feedingPointId, images).launch()
    }

    private companion object {
        const val APPROVED_FILTER = "approved"
        const val CANCEL_FEEDING_REASON = "reason"
    }
}