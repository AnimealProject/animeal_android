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
                    AnimalState.Starved -> 0
                    AnimalState.InProgress -> 1
                    AnimalState.Pending -> 2
                    AnimalState.Fed -> 3
                }
            }
    }
}