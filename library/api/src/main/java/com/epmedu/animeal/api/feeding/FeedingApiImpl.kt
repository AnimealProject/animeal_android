package com.epmedu.animeal.api.feeding

import com.amplifyframework.datastore.generated.model.Feeding
import com.epmedu.animeal.api.AnimealApi
import com.epmedu.animeal.common.data.wrapper.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import type.FeedingStatus

internal class FeedingApiImpl(
    private val animealApi: AnimealApi
) : FeedingApi {

    override fun getUserFeedings(isGuest: Boolean, userId: String): Flow<List<Feeding>> {
        if (isGuest) {
            return flow { emptyList<Feeding>() }
        }

        return animealApi.getModelList(
            predicate = Feeding.USER_ID.eq(userId),
            modelClass = Feeding::class.java
        )
    }

    override suspend fun getAllFeedings(): ApiResult<GetActiveFeedingsQuery.Data> {
        return animealApi.launchQuery(
            query = GetActiveFeedingsQuery.builder()
                .build(),
            responseClass = GetActiveFeedingsQuery.Data::class.java
        )
    }

    override suspend fun getFeedingsBy(
        feedingPointId: String?,
        assignedModeratorId: String?,
        status: FeedingStatus?
    ): ApiResult<GetActiveFeedingsQuery.Data> {
        val requestBuilder = GetActiveFeedingsQuery.builder()

        feedingPointId?.let {
            requestBuilder.feedingPointId(feedingPointId)
        }
        status?.let {
            requestBuilder.status(status.name)
        }
        assignedModeratorId?.let {
            requestBuilder.moderatorId(assignedModeratorId)
        }

        return animealApi.launchQuery(
            query = requestBuilder.build(),
            responseClass = GetActiveFeedingsQuery.Data::class.java
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
}