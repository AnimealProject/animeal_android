package com.epmedu.animeal.feeding.domain.usecase

import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.feeding.domain.model.enum.AnimalState
import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import com.epmedu.animeal.feeding.presentation.model.MapLocation
import com.epmedu.animeal.feeding.presentation.model.MapLocation.Companion.toPoint
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import com.epmedu.animeal.geolocation.location.distanceInKmTo
import com.epmedu.animeal.geolocation.location.isNearTo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFeedingPointByPriorityUseCase(
    val feedingPointRepository: FeedingPointRepository
) {

    /*
     * Filters list of FeedingPoint by priority defined at EPMEDU-594:
     * - 1st: Status = Red, is favorite and is near to user location
     * - 2nd: Status = Red, is favorite
     * - 3rd: Status = Red, is not favorite but is near to user location
     */
    operator fun invoke(
        type: AnimalType,
        userLocation: MapLocation,
    ): Flow<FeedingPoint?> {
        return feedingPointRepository.getFeedingPointsBy(false) { it.animalType == type && it.animalStatus == AnimalState.RED }
            .map { feedingPoints ->
                val readyToFeed = feedingPoints
                    .sortByNearestToLocation(userLocation)
                var feedPointFound: FeedingPoint? = null
                if (readyToFeed.isNotEmpty()) {
                    feedPointFound = readyToFeed
                        .find {
                            it.isFavourite && it.location.toPoint().isNearTo(userLocation.toPoint())
                        }
                        ?: run { readyToFeed.find { it.isFavourite } }
                        ?: readyToFeed.first()
                }
                feedPointFound
            }
    }
    private fun Iterable<FeedingPoint>.sortByNearestToLocation(
        userLocation: MapLocation
    ): List<FeedingPoint> {
        return sortedWith(
            compareBy {
                it.location.toPoint().distanceInKmTo(userLocation.toPoint())
            }
        )
    }
}