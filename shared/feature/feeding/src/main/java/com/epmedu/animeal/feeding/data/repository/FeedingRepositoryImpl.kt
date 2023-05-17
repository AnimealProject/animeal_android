package com.epmedu.animeal.feeding.data.repository

import com.amplifyframework.datastore.generated.model.FeedingHistory
import com.epmedu.animeal.api.feeding.FeedingApi
import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.feeding.data.mapper.toActionResult
import com.epmedu.animeal.feeding.data.mapper.toDomain
import com.epmedu.animeal.feeding.data.mapper.toFeedingHistory
import com.epmedu.animeal.feeding.domain.model.Feeding
import com.epmedu.animeal.feeding.domain.repository.FavouriteRepository
import com.epmedu.animeal.feeding.domain.repository.FeedingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class FeedingRepositoryImpl(
    private val dispatchers: Dispatchers,
    private val authApi: AuthAPI,
    private val feedingApi: FeedingApi,
    private val favouriteRepository: FavouriteRepository
) : FeedingRepository {

    override suspend fun getUserFeedings(): List<Feeding> {
        return combine(
            feedingApi.getUserFeedings(userId = authApi.getCurrentUserId()),
            favouriteRepository.getFavouriteFeedingPointIds()
        ) { feedings, favouriteIds ->
            feedings.map { feeding ->
                feeding.toDomain(isFavourite = favouriteIds.any { it == feeding.feedingPoint.id })
            }
        }
            .flowOn(dispatchers.IO)
            .first()
    }

    override fun getApprovedFeedingHistories(feedingPointId: String): Flow<List<FeedingHistory>> {
        return flow {
            emit(
                feedingApi.getApprovedFeedingHistories(feedingPointId)
                    .data?.searchFeedingHistories()?.items()?.map { item ->
                        item.toFeedingHistory()
                    } ?: emptyList()
            )
        }
    }

    override suspend fun startFeeding(feedingPointId: String): ActionResult {
        return feedingApi.startFeeding(feedingPointId).toActionResult(feedingPointId)
    }

    override suspend fun cancelFeeding(feedingPointId: String): ActionResult {
        return feedingApi.cancelFeeding(feedingPointId).toActionResult(feedingPointId)
    }

    override suspend fun rejectFeeding(feedingPointId: String, reason: String): ActionResult {
        return feedingApi.rejectFeeding(feedingPointId, reason).toActionResult(feedingPointId)
    }

    override suspend fun finishFeeding(
        feedingPointId: String,
        images: List<String>
    ): ActionResult {
        return feedingApi.finishFeeding(feedingPointId, images).toActionResult(feedingPointId)
    }
}