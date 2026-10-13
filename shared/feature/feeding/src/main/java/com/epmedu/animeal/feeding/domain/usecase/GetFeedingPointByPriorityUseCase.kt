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
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take

class GetFeedingPointByPriorityUseCase(
    private val feedingPointRepository: FeedingPointRepository
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
    ): Flow<FeedingPoint> {
        return feedingPointRepository.getFeedingPointsBy(shouldFetch = false) {
            it.animalType == type && it.animalStatus == AnimalState.Starved
        }.map { feedingPoints ->
            resolvePriority(feedingPoints, userLocation)
        }.filterNotNull().take(1)
    }

    private fun resolvePriority(feedingPoints: List<FeedingPoint>, userLocation: MapLocation): FeedingPoint? {
        val (favourite, notFavourite) = feedingPoints.filter {
            it.location.toPoint().isNearTo(userLocation.toPoint())
        }.sortedWith(
            compareBy {
                it.location.toPoint().distanceInKmTo(userLocation.toPoint())
            }
        ).partition { it.isFavourite }

        return when {
            favourite.isNotEmpty() -> favourite // 1st and 2nd priority
            else -> notFavourite // 3rd priority
        }.firstOrNull()
    }
}
