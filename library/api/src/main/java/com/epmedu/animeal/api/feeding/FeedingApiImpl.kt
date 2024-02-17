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
import com.epmedu.animeal.extensions.YEAR_MONTH_DAY_DASH_FORMATTER
import kotlinx.coroutines.flow.Flow
import type.FeedingStatus
import type.SearchableFeedingFilterInput
import type.SearchableFeedingHistoryFilterInput
import type.SearchableStringFilterInput
import java.time.LocalDate

internal class FeedingApiImpl(
    private val animealApi: AnimealApi
) : FeedingApi {

    private val feedingsCreatedAtFilterInput = SearchableStringFilterInput.builder()
        .gte(
            LocalDate.now().minusDays(FEEDINGS_LIMIT_IN_DAYS)
                .format(YEAR_MONTH_DAY_DASH_FORMATTER)
        )
        .build()

    override fun getUserFeedings(userId: String): Flow<List<Feeding>> {
        return animealApi.getModelList(
            predicate = Feeding.USER_ID.eq(userId),
            modelClass = Feeding::class.java
        )
    }

    override suspend fun getAllFeedings(): ApiResult<SearchFeedingsQuery.Data> {
        return animealApi.launchQuery(
            query = SearchFeedingsQuery.builder()
                .filter(
                    SearchableFeedingFilterInput.builder()
                        .createdAt(feedingsCreatedAtFilterInput)
                        .build()
                )
                .build(),
            responseClass = SearchFeedingsQuery.Data::class.java
        )
    }

    override suspend fun getFeedingsBy(
        feedingPointId: String?,
        assignedModeratorId: String?,
        status: FeedingStatus?,
    ): ApiResult<SearchFeedingsQuery.Data> {
        val filterBuilder = SearchableFeedingFilterInput.builder()

        feedingPointId?.let {
            filterBuilder.feedingPointFeedingsId(
                SearchableStringFilterInput.builder()
                    .eq(feedingPointId)
                    .build()
            )
        }
        status?.let {
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
        val query = SearchFeedingsQuery.builder()
            .filter(filterBuilder.build())
            .build()

        return animealApi.launchQuery(
            query = query,
            responseClass = SearchFeedingsQuery.Data::class.java
        )
    }

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
        const val CANCEL_FEEDING_REASON = "reason"
        const val FEEDINGS_LIMIT_IN_DAYS = 10L
    }
}