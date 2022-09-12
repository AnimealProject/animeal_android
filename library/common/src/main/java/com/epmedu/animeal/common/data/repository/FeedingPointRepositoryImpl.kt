package com.epmedu.animeal.common.data.repository

import com.epmedu.animeal.common.data.enum.AnimalPriority
import com.epmedu.animeal.common.data.enum.AnimalState
import com.epmedu.animeal.common.data.model.FeedingPoint
import com.epmedu.animeal.foundation.switch.Tab
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

class FeedingPointRepositoryImpl(
    private val stubData: List<FeedingPoint> = arrayListOf<FeedingPoint>().apply {
        for (i: Int in 0 until 25) {
            add(
                FeedingPoint(
                    i,
                    AnimalPriority.values()[Random.nextInt(0, AnimalPriority.values().size)],
                    AnimalState.values()[Random.nextInt(0, AnimalState.values().size)],
                    Tab.values()[Random.nextInt(0, Tab.values().size)],
                    Random.nextBoolean(),
                    yCoordinate = Random.nextDouble(37.362023, 37.466366),
                    xCoordinate = Random.nextDouble(-122.178187, -121.978700)
                )
            )
        }
    }
) : FeedingPointRepository {

    override suspend fun getAllFeedingPoints(): Flow<List<FeedingPoint>> {
        return flow {
            emit(stubData)
        }
    }

    override suspend fun getCats(): Flow<List<FeedingPoint>> {
        return flow {
            emit(stubData.filter { feedingPoint -> feedingPoint.tab == Tab.Cats })
        }
    }

    override suspend fun getDogs(): Flow<List<FeedingPoint>> {
        return flow {
            emit(stubData.filter { feedingPoint -> feedingPoint.tab == Tab.Dogs })
        }
    }

    override suspend fun getFavourites(): Flow<List<FeedingPoint>> {
        return flow {
            emit(stubData.filter { feedingPoint -> feedingPoint.isFavourite })
        }
    }
}