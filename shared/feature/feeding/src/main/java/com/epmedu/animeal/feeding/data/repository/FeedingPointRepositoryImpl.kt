package com.epmedu.animeal.feeding.data.repository

import com.epmedu.animeal.api.favourite.FavouriteApi
import com.epmedu.animeal.api.feeding.FeedingPointApi
import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.feeding.data.mapper.toActionResult
import com.epmedu.animeal.feeding.data.mapper.toDomainFeedingPoint
import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import com.epmedu.animeal.feeding.domain.model.FeedingPoint as DomainFeedingPoint

internal class FeedingPointRepositoryImpl(
    private val authAPI: AuthAPI,
    private val favouriteApi: FavouriteApi,
    private val feedingPointApi: FeedingPointApi
) : FeedingPointRepository {

    private val feedingPoints: Flow<List<DomainFeedingPoint>> by lazy { fetchFeedingPoints() }

    private fun fetchFeedingPoints(): Flow<List<DomainFeedingPoint>> {
        return feedingPointApi.getAllFeedingPoints()
            .combine(favouriteApi.getFavouriteList(authAPI.currentUserId)) { dataFeedingPointsList, favouriteList ->
                dataFeedingPointsList.map { dataFeedingPoint ->
                    dataFeedingPoint.toDomainFeedingPoint(
                        isFavourite = favouriteList.any { it.feedingPointId == dataFeedingPoint.id }
                    )
                }
            }
    }

    override fun getAllFeedingPoints(): Flow<List<DomainFeedingPoint>> = feedingPoints

    override suspend fun startFeeding(feedingPointId: String): ActionResult {
        return feedingPointApi.startFeeding(feedingPointId).toActionResult(feedingPointId)
    }

    override suspend fun cancelFeeding(feedingPointId: String): ActionResult {
        return feedingPointApi.cancelFeeding(feedingPointId).toActionResult(feedingPointId)
    }

    override suspend fun finishFeeding(
        feedingPointId: String,
        images: List<String>
    ): ActionResult {
        return feedingPointApi.finishFeeding(feedingPointId, images).toActionResult(feedingPointId)
    }
}
