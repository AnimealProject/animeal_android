package com.epmedu.animeal.tabs.search.domain

import com.epmedu.animeal.common.constants.DefaultConstants
import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.map

class SearchDogsFeedingPointsUseCase @Inject constructor(private val feedingPointRepository: FeedingPointRepository) {
    operator fun invoke(query: String = DefaultConstants.EMPTY_STRING) =
        feedingPointRepository.getDogs(query).map { points ->
            points.filter { point ->
                point.title.startsWith(prefix = query, ignoreCase = true)
            }
        }
}