package com.epmedu.animeal.tabs.search.domain

import com.epmedu.animeal.common.constants.DefaultConstants
import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchCatsFeedingPointsUseCase @Inject constructor(private val feedingPointRepository: FeedingPointRepository) {
    operator fun invoke(query: String = DefaultConstants.EMPTY_STRING): Flow<List<FeedingPoint>> =
        feedingPointRepository.getFeedingPointsBy { it.animalType == AnimalType.Cats }
            .map { points ->
                points.filter { point ->
                    point.title.startsWith(
                        prefix = query,
                        ignoreCase = true
                    )
                }
            }
}