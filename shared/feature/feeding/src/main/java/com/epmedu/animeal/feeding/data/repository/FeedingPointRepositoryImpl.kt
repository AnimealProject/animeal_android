package com.epmedu.animeal.feeding.data.repository

import com.epmedu.animeal.api.favourite.FavouriteApi
import com.epmedu.animeal.api.feeding.FeedingPointApi
import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.extensions.replaceElement
import com.epmedu.animeal.feeding.data.mapper.toActionResult
import com.epmedu.animeal.feeding.data.mapper.toDomainFeedingPoint
import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import com.epmedu.animeal.feeding.domain.model.FeedingPoint as DomainFeedingPoint

internal class FeedingPointRepositoryImpl(
    private val authAPI: AuthAPI,
    private val favouriteApi: FavouriteApi,
    private val feedingPointApi: FeedingPointApi,
    private val dispatchers: Dispatchers
) : FeedingPointRepository {

    private val feedingPointsFlow: Flow<List<DomainFeedingPoint>> by lazy { fetchFeedingPoints() }
    private var cachedFeedingPoints = emptyList<DomainFeedingPoint>()

    private fun fetchFeedingPoints(): Flow<List<DomainFeedingPoint>> {
        return merge(
            getFeedingPoints(),
            subscribeToFeedingPointsCreation(),
            subscribeToFeedingPointsUpdates(),
            subscribeToFeedingPointsDeletion()
        ).combine(favouriteApi.getFavouriteList(authAPI.currentUserId)) { feedingPointsList, favourites ->
            cachedFeedingPoints = feedingPointsList.map { dataFeedingPoint ->
                dataFeedingPoint.copy(
                    isFavourite = favourites.any { it.feedingPointId == dataFeedingPoint.id }
                )
            }
            cachedFeedingPoints
        }.flowOn(dispatchers.IO)
    }

    private fun getFeedingPoints(): Flow<List<DomainFeedingPoint>> {
        return feedingPointApi.getAllFeedingPoints().map { dataFeedingPoints ->
            cachedFeedingPoints = dataFeedingPoints.map { dataFeedingPoint ->
                dataFeedingPoint.toDomainFeedingPoint()
            }
            cachedFeedingPoints
        }
    }

    private fun subscribeToFeedingPointsCreation(): Flow<List<DomainFeedingPoint>> {
        return feedingPointApi.subscribeToFeedingPointsCreation().map { createdFeedingPoint ->
            cachedFeedingPoints = cachedFeedingPoints + createdFeedingPoint.toDomainFeedingPoint()
            cachedFeedingPoints
        }
    }

    private fun subscribeToFeedingPointsUpdates(): Flow<List<DomainFeedingPoint>> {
        return feedingPointApi.subscribeToFeedingPointsUpdates().map { updatedFeedingPoint ->
            cachedFeedingPoints.find { it.id == updatedFeedingPoint.id() }?.let {
                cachedFeedingPoints = cachedFeedingPoints.replaceElement(
                    oldElement = it,
                    newElement = updatedFeedingPoint.toDomainFeedingPoint()
                )
            }
            cachedFeedingPoints
        }
    }

    private fun subscribeToFeedingPointsDeletion(): Flow<List<DomainFeedingPoint>> {
        return feedingPointApi.subscribeToFeedingPointsDeletion().map { deletedFeedingPointId ->
            cachedFeedingPoints.find { it.id == deletedFeedingPointId }?.let {
                cachedFeedingPoints = cachedFeedingPoints - it
            }
            cachedFeedingPoints
        }
    }

    override fun getAllFeedingPoints(): Flow<List<DomainFeedingPoint>> = feedingPointsFlow

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
