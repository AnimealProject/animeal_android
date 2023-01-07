package com.epmedu.animeal.favourites.data

import com.epmedu.animeal.feeding.domain.model.Feeder
import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.feeding.domain.model.enum.AnimalState
import com.epmedu.animeal.feeding.presentation.model.MapLocation
import com.epmedu.animeal.foundation.switch.AnimalType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FavouritesRepositoryImpl : FavouritesRepository {

    override fun getFavouriteFeedSpots(): Flow<List<FeedingPoint>> = flowOf(
        listOf(
            FeedingPoint(
                id = "0",
                title = "Europe Square park",
                isFavourite = true,
                animalStatus = AnimalState.RED,
                animalType = AnimalType.Dogs,
                description = "Hungry dog",
                lastFeeder = Feeder(id = "0", "Fred", "12:00"),
                location = MapLocation.Tbilisi,
            ),
            FeedingPoint(
                id = "1",
                title = "University garden",
                isFavourite = true,
                animalStatus = AnimalState.RED,
                animalType = AnimalType.Dogs,
                description = "Hungry dog",
                lastFeeder = Feeder(id = "0", "Bob", "13:00"),
                location = MapLocation.Tbilisi,
            ),
            FeedingPoint(
                id = "2",
                title = "Gorky park",
                isFavourite = true,
                animalStatus = AnimalState.GREEN,
                animalType = AnimalType.Cats,
                description = "Hungry cat",
                lastFeeder = Feeder(id = "0", "Anna", "14:00"),
                location = MapLocation.Tbilisi,
            ),
        )
    )
}