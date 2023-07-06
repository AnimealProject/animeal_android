package com.epmedu.animeal.feeding.domain.usecase

import com.epmedu.animeal.feeding.presentation.model.FeedStatus
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel.Companion.isNearTo
import com.epmedu.animeal.feeding.presentation.model.MapLocation
import kotlinx.collections.immutable.ImmutableList

class GetFeedingPointByPriorityUseCase {

    /*
     * Filters iterable of FeedingPointModel by priority defined at EPMEDU-594:
     * - 1st: Status = Red, is favorite and is near to user location
     * - 2nd: Status = Red, is favorite
     * - 3rd: Status = Red, is not favorite but is near to user location
     */
    operator fun invoke(
        feedingPoints: ImmutableList<FeedingPointModel>,
        userLocation: MapLocation
    ): FeedingPointModel? {
        val readyToFeed = feedingPoints.filter { it.feedStatus == FeedStatus.RED }
        return if (readyToFeed.isNotEmpty()) {
            readyToFeed.firstOrNull { it.isFavourite && it.isNearTo(userLocation) } ?: run {
                readyToFeed.firstOrNull { it.isFavourite }
            } ?: readyToFeed.firstOrNull { it.isNearTo(userLocation) }
        } else {
            null
        }
    }
}