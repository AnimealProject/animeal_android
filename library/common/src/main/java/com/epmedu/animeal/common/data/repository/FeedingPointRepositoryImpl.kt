package com.epmedu.animeal.common.data.repository

import com.epmedu.animeal.common.data.enum.AnimalPriority
import com.epmedu.animeal.common.data.enum.AnimalState
import com.epmedu.animeal.common.data.model.FeedingPoint
import com.epmedu.animeal.common.data.model.MapLocation
import com.epmedu.animeal.foundation.switch.AnimalType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

class FeedingPointRepositoryImpl : FeedingPointRepository {

    private val stubData: List<FeedingPoint> = List(25) { index ->
        FeedingPoint(
            index,
            AnimalPriority.values()[Random.nextInt(0, AnimalPriority.values().size)],
            AnimalState.values()[Random.nextInt(0, AnimalState.values().size)],
            AnimalType.values()[Random.nextInt(0, AnimalType.values().size)],
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