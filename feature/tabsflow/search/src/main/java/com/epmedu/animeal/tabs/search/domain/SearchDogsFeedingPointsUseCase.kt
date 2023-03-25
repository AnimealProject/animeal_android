package com.epmedu.animeal.tabs.search.domain

import com.epmedu.animeal.common.constants.DefaultConstants
import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchDogsFeedingPointsUseCase @Inject constructor(private val feedingPointRepository: FeedingPointRepository) {
    operator fun invoke(query: String = DefaultConstants.EMPTY_STRING) =
        feedingPointRepository.getFeedingPointsBy { it.animalType == AnimalType.Dogs }
            .map { points ->
                points.filter { point ->
                    point.title.startsWith(
                        prefix = query,
                        ignoreCase = true
                    )
                }
            }
}