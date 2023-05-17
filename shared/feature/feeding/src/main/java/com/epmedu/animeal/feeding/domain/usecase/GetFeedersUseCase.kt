package com.epmedu.animeal.feeding.domain.usecase

import com.epmedu.animeal.feeding.domain.model.Feeder
import com.epmedu.animeal.feeding.domain.repository.FeederRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFeedersUseCase(private val repository: FeederRepository) {

    operator fun invoke(feedingPointId: String): Flow<List<Feeder>> {
        return repository.getFeeders(feedingPointId).map { feeders ->
            feeders.sortedByDescending { feeder -> feeder.feedingDate }
        }
    }
}