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
            it.animalType == type && it.animalStatus == AnimalState.RED
        }.map { feedingPoints ->
            Factory(feedingPoints).resolvePriority(userLocation)
        }.filterNotNull().take(1)
    }
    sealed interface FeedingPointsPriority {
        val feedingPoints: List<FeedingPoint>

        fun getPrioritisedFeedingPoints(userLocation: MapLocation): List<FeedingPoint>

        class Favourite(override val feedingPoints: List<FeedingPoint>) : FeedingPointsPriority {

            override fun getPrioritisedFeedingPoints(userLocation: MapLocation): List<FeedingPoint> =
                feedingPoints.sortedWith(
                    compareBy {
                        it.location.toPoint().distanceInKmTo(userLocation.toPoint())
                    }
                )
        }

        class NotFavourite(override val feedingPoints: List<FeedingPoint>) : FeedingPointsPriority {

            override fun getPrioritisedFeedingPoints(userLocation: MapLocation): List<FeedingPoint> =
                feedingPoints.filter {
                    it.location.toPoint().isNearTo(userLocation.toPoint())
                }
        }
    }

    class Factory(private val feedingPoints: List<FeedingPoint>) {

        fun resolvePriority(userLocation: MapLocation): FeedingPoint? {
            val (favourite, notFavourite) = feedingPoints.partition { it.isFavourite }

            val priority = when {
                favourite.isNotEmpty() -> FeedingPointsPriority.Favourite(favourite) // 1st and 2nd priority
                else -> FeedingPointsPriority.NotFavourite(notFavourite) // 3rd priority
            }

            return priority.getPrioritisedFeedingPoints(userLocation).firstOrNull()
        }
    }
}
