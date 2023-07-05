package com.epmedu.animeal.feeding.data.repository

import com.epmedu.animeal.api.feeding.FeedingPointApi
import com.epmedu.animeal.api.storage.StorageApi
import com.epmedu.animeal.extensions.replaceElement
import com.epmedu.animeal.feeding.data.mapper.toDomainFeedingPoint
import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.feeding.domain.repository.FavouriteRepository
import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import com.epmedu.animeal.feeding.domain.model.FeedingPoint as DomainFeedingPoint

internal class FeedingPointRepositoryImpl(
    private val dispatchers: Dispatchers,
    private val favouriteRepository: FavouriteRepository,
    private val feedingPointApi: FeedingPointApi,
    private val storageApi: StorageApi
) : FeedingPointRepository {

    private val feedingPointsFlow = MutableStateFlow(emptyList<DomainFeedingPoint>())
    private val feedingPoints
        get() = feedingPointsFlow.value

    override fun getAllFeedingPoints(shouldFetch: Boolean): Flow<List<DomainFeedingPoint>> {
        val flow = when {
            shouldFetch -> merge(feedingPointsFlow, fetchFeedingPoints())
            else -> feedingPointsFlow
        }
        return flow
            .filterNot { it.isEmpty() }
            .distinctUntilChanged()
            .flowOn(dispatchers.IO)
    }

    override fun getFeedingPointsBy(
        shouldFetch: Boolean,
        predicate: (DomainFeedingPoint) -> Boolean
    ): Flow<List<DomainFeedingPoint>> {
        return getAllFeedingPoints(shouldFetch).map { feedingPoints ->
            feedingPoints.filter(predicate)
        }
    }

    override fun getFeedingPointById(id: String): FeedingPoint {
        return feedingPoints.find { it.id == id }
            ?: throw IllegalArgumentException("No feeding point with id: $id")
    }

    private fun fetchFeedingPoints(): Flow<List<DomainFeedingPoint>> {
        return merge(
            getFeedingPoints(),
            subscribeToFeedingPointsCreation(),
            subscribeToFeedingPointsUpdates(),
            subscribeToFeedingPointsDeletion()
        ).combine(
            favouriteRepository.getFavouriteFeedingPointIds()
        ) { feedingPointsList, favouriteIds ->
            feedingPointsList.map { dataFeedingPoint ->
                dataFeedingPoint.copy(
                    isFavourite = favouriteIds.any { it == dataFeedingPoint.id },
                    images = listOf(storageApi.parseAmplifyUrl(dataFeedingPoint.images[0]))
                )
            }
        }.onEach {
            feedingPointsFlow.value = it
        }
    }

    private fun getFeedingPoints(): Flow<List<DomainFeedingPoint>> {
        return feedingPointApi.getAllFeedingPoints().map { dataFeedingPoints ->
            dataFeedingPoints.map { dataFeedingPoint ->
                dataFeedingPoint.toDomainFeedingPoint()
            }
        }
    }

    private fun subscribeToFeedingPointsCreation(): Flow<List<DomainFeedingPoint>> {
        return feedingPointApi.subscribeToFeedingPointsCreation().map { createdFeedingPoint ->
            feedingPoints + createdFeedingPoint.toDomainFeedingPoint()
        }
    }

    private fun subscribeToFeedingPointsUpdates(): Flow<List<DomainFeedingPoint>> {
        return feedingPointApi.subscribeToFeedingPointsUpdates().map { updatedFeedingPoint ->
            feedingPoints.find { it.id == updatedFeedingPoint.id }?.let {
                feedingPoints.replaceElement(
                    oldElement = it,
                    newElement = updatedFeedingPoint.toDomainFeedingPoint()
                )
            } ?: feedingPoints
        }
    }

    private fun subscribeToFeedingPointsDeletion(): Flow<List<DomainFeedingPoint>> {
        return feedingPointApi.subscribeToFeedingPointsDeletion().map { deletedFeedingPoint ->
            feedingPoints.find { it.id == deletedFeedingPoint.id }?.let {
                feedingPoints - it
            } ?: feedingPoints
        }
    }
}
