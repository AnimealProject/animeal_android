package com.epmedu.animeal.api.feeding

import SearchFeedingsQuery
import com.amplifyframework.datastore.generated.model.Feeding
import com.epmedu.animeal.api.AnimealApi
import com.epmedu.animeal.api.feeding.FeedingFilters.feedingsCreatedAtFilterInput
import com.epmedu.animeal.common.data.wrapper.ApiResult
import kotlinx.coroutines.flow.Flow
import type.FeedingStatus
import type.SearchableFeedingFilterInput
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
        createdAt: SearchableStringFilterInput?
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
        createdAt?.let {
            filterBuilder.createdAt(createdAt)
        }

        val query = SearchFeedingsQuery.builder()
            .filter(filterBuilder.build())
            .build()

        return animealApi.launchQuery(
            query = query,
            responseClass = SearchFeedingsQuery.Data::class.java
        )
    }

    override fun subscribeToFeedingsUpdates(): Flow<OnUpdateFeedingExtSubscription.Data> {
        return animealApi.launchSubscription(
            subscription = OnUpdateFeedingExtSubscription.builder().build(),
            responseClass = OnUpdateFeedingExtSubscription.Data::class.java
        )
    }

    override fun subscribeToFeedingsCreation(): Flow<OnCreateFeedingExtSubscription.Data> {
        return animealApi.launchSubscription(
            subscription = OnCreateFeedingExtSubscription.builder().build(),
            responseClass = OnCreateFeedingExtSubscription.Data::class.java
        )
    }

    override fun subscribeToFeedingsDeletion(): Flow<OnDeleteFeedingExtSubscription.Data> {
        return animealApi.launchSubscription(
            subscription = OnDeleteFeedingExtSubscription.builder().build(),
            responseClass = OnDeleteFeedingExtSubscription.Data::class.java
        )
    }
}