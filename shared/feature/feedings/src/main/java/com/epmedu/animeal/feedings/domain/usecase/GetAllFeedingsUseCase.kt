package com.epmedu.animeal.feedings.domain.usecase

import com.epmedu.animeal.feeding.domain.model.Feeding
import com.epmedu.animeal.feeding.domain.repository.FeedingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllFeedingsUseCase(private val repository: FeedingRepository) {

    operator fun invoke(): Flow<List<Feeding>> {
        return repository.getAllFeedings().map { feedings ->
            feedings.sortedByDescending { feeding -> feeding.date }
        }
    }
}