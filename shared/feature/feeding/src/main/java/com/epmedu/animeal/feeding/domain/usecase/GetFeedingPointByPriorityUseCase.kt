package com.epmedu.animeal.feeding.domain.usecase

import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.feeding.domain.model.enum.AnimalState
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.feeding.presentation.model.MapLocation
import com.epmedu.animeal.feeding.presentation.model.MapLocation.Companion.toPoint
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import com.epmedu.animeal.geolocation.location.distanceInKmTo
import com.epmedu.animeal.geolocation.location.isNearTo

class GetFeedingPointByPriorityUseCase(
    val getAllFeedingPointsUseCase: GetAllFeedingPointsUseCase
) {

    /*
     * Filters iterable of FeedingPointModel by priority defined at EPMEDU-594:
     * - 1st: Status = Red, is favorite and is near to user location
     * - 2nd: Status = Red, is favorite
     * - 3rd: Status = Red, is not favorite but is near to user location
     */
    suspend operator fun invoke(
        type: AnimalType,
        userLocation: MapLocation,
        onFeedingPointFound: (FeedingPointModel) -> Unit,
    ) {
        getAllFeedingPointsUseCase(type = type).collect { domainFeedingPoints ->
            val readyToFeed = domainFeedingPoints
                .filter { it.animalStatus == AnimalState.RED }
                .sortByNearestToLocation(userLocation)
            if (readyToFeed.isNotEmpty()) {
                val feedPointFound = readyToFeed
                    .firstOrNull {
                        it.isFavourite && it.location.toPoint().isNearTo(userLocation.toPoint())
                    }
                    ?: run { readyToFeed.firstOrNull { it.isFavourite } }
                    ?: readyToFeed.firstOrNull()
                feedPointFound?.let {
                    onFeedingPointFound(FeedingPointModel(it))
                }
            }
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