package com.epmedu.animeal.feeding.data.repository

import com.epmedu.animeal.feeding.domain.model.Feeder
import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.feeding.domain.model.enum.AnimalPriority
import com.epmedu.animeal.feeding.domain.model.enum.AnimalState
import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import com.epmedu.animeal.feeding.presentation.model.MapLocation
import com.epmedu.animeal.foundation.switch.AnimalType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlin.random.Random
import kotlin.random.nextInt

class FeedingPointRepositoryImpl : FeedingPointRepository {

    private val stubData: List<FeedingPoint> = List(25) { index ->
        FeedingPoint(
            index,
            title = "$index - Near to Bukia Garden M.S Technical University",
            "$index - This area covers about 100 sq.m. -S, it starts with Bukia Garden " +
                "and Sports At the palace. There are about 1000 homeless people here " +
                "The dog lives with the habit of helping You need.",
            AnimalPriority.values().random(),
            AnimalState.values().random(),
            AnimalType.values().random(),
            Random.nextBoolean(),
            location = MapLocation(
                Random.nextDouble(41.6752, 41.7183),
                Random.nextDouble(44.7724, 44.8658)
            ),
            lastFeeder = Feeder(
                id = index,
                name = "$index - Giorgi Abutidze",
                time = "${Random.nextInt(0..24)} hours ago"
            )
        )
    }

    override fun getAllFeedingPoints(): Flow<List<FeedingPoint>> = flowOf(stubData)

    override fun getCats(): Flow<List<FeedingPoint>> = flowOf(
        stubData.filter { feedingPoint -> feedingPoint.animalType == AnimalType.Cats }
    )

    override fun getDogs(): Flow<List<FeedingPoint>> = flowOf(
        stubData.filter { feedingPoint -> feedingPoint.animalType == AnimalType.Dogs }
    )

    override fun getFavourites(): Flow<List<FeedingPoint>> = flowOf(
        stubData.filter { feedingPoint -> feedingPoint.isFavourite }
    )

    override fun getFeedingPoint(id: Int): Flow<FeedingPoint> = flowOf(
        stubData.first { feedingPoint -> feedingPoint.id == id }
    )
}