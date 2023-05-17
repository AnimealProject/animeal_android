package com.epmedu.animeal.feeding.domain.repository

import com.epmedu.animeal.feeding.domain.model.Feeder
import kotlinx.coroutines.flow.Flow

interface FeederRepository {

    fun getFeeders(feedingPointId: String): Flow<List<Feeder>>
}