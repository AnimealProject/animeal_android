package com.epmedu.animeal.tabs.search.domain

import com.epmedu.animeal.common.constants.DefaultConstants
import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchFeedingPointsUseCase @Inject constructor(private val feedingPointRepository: FeedingPointRepository) {
    operator fun invoke(
        query: String = DefaultConstants.EMPTY_STRING,
        animalType: AnimalType
    ): Flow<List<FeedingPoint>> =
        feedingPointRepository.getFeedingPointsBy { point -> point.animalType == animalType }
            .map { points ->
                points.filter { point ->
                    point.title.contains(
                        other = query,
                        ignoreCase = true
                    )
                }
            }
}