package com.epmedu.animeal.favourites.domain

import com.epmedu.animeal.feeding.domain.model.enum.AnimalState
import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import kotlinx.coroutines.flow.map

class GetFavouriteFeedingPointsUseCase(private val feedingPointRepository: FeedingPointRepository) {

    operator fun invoke() = feedingPointRepository.getAllFeedingPoints().map { feedingPointsList ->
        feedingPointsList
            .filter { feedingPoint -> feedingPoint.isFavourite }
            .sortedBy {
                when (it.animalStatus) {
                    AnimalState.RED -> 0
                    AnimalState.YELLOW -> 1
                    AnimalState.GREEN -> 2
                }
            }
    }
}