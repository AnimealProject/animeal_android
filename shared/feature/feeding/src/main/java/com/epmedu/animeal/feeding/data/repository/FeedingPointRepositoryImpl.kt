package com.epmedu.animeal.feeding.data.repository

import com.epmedu.animeal.api.feeding.FeedingPointApi
import com.epmedu.animeal.api.storage.StorageApi
import com.epmedu.animeal.extensions.replaceElement
import com.epmedu.animeal.feeding.data.mapper.toDomainFeedingPoint
import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.feeding.domain.repository.FavouriteRepository
import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch
import com.epmedu.animeal.feeding.domain.model.FeedingPoint as DomainFeedingPoint

internal class FeedingPointRepositoryImpl(
    dispatchers: Dispatchers,
    private val favouriteRepository: FavouriteRepository,
    private val feedingPointApi: FeedingPointApi,
    private val storageApi: StorageApi
) : FeedingPointRepository {

    private val coroutineScope = CoroutineScope(dispatchers.IO)

    private val feedingPointsFlow = MutableStateFlow(emptyList<DomainFeedingPoint>())
    private val feedingPoints
        get() = feedingPointsFlow.value

    init {
        coroutineScope.launch {
            merge(
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
            }.collect {
                feedingPointsFlow.value = it
            }
        }
    }

    private fun getFeedingPoints(): Flow<List<DomainFeedingPoint>> {
        return feedingPointApi.getAllFeedingPoints().map { dataFeedingPoints ->
            dataFeedingPoints.map { dataFeedingPoint ->
                dataFeedingPoint.toDomainFeedingPoint()
            }
        }
    }

    override suspend fun getFeedingPointById(id: String): FeedingPoint {
        return feedingPointApi.getAllFeedingPoints().map { feedingPoints ->
            feedingPoints.filter { it.id == id }[0].toDomainFeedingPoint()
        }.first()
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

    override fun getAllFeedingPoints(): Flow<List<DomainFeedingPoint>> {
        return feedingPointsFlow.asStateFlow()
    }

    override fun getFeedingPointsBy(predicate: (DomainFeedingPoint) -> Boolean): Flow<List<DomainFeedingPoint>> {
        return getAllFeedingPoints().map { feedingPoints -> feedingPoints.filter(predicate) }
    }
}
