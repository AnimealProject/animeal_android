package com.epmedu.animeal.home.data

import com.epmedu.animeal.common.data.model.MapLocation
import com.epmedu.animeal.foundation.switch.AnimalType
import com.epmedu.animeal.home.data.model.FeedingPoint
import com.epmedu.animeal.home.data.model.enum.AnimalPriority
import com.epmedu.animeal.home.data.model.enum.AnimalState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

class FeedingPointRepositoryImpl : FeedingPointRepository {

    private val stubData: List<FeedingPoint> = List(25) { index ->
        FeedingPoint(
            index,
            AnimalPriority.values().random(),
            AnimalState.values().random(),
            AnimalType.values().random(),
            Random.nextBoolean(),
            location = MapLocation(
                Random.nextDouble(37.362023, 37.466366),
                Random.nextDouble(-122.178187, -121.978700)
            )
        )
    }

    override suspend fun getAllFeedingPoints(): Flow<List<FeedingPoint>> {
        return flow {
            emit(stubData)
        }
    }

    override suspend fun getCats(): Flow<List<FeedingPoint>> {
        return flow {
            emit(stubData.filter { feedingPoint -> feedingPoint.animalType == AnimalType.Cats })
        }
    }

    override suspend fun getDogs(): Flow<List<FeedingPoint>> {
        return flow {
            emit(stubData.filter { feedingPoint -> feedingPoint.animalType == AnimalType.Dogs })
        }
    }

    override suspend fun getFavourites(): Flow<List<FeedingPoint>> {
        return flow {
            emit(stubData.filter { feedingPoint -> feedingPoint.isFavourite })
        }
    }
}