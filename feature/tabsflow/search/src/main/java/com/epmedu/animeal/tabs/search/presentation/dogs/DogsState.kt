package com.epmedu.animeal.tabs.search.presentation.dogs

import com.epmedu.animeal.feeding.domain.model.Feeder
import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.feeding.domain.model.enum.AnimalState
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.feeding.presentation.model.MapLocation
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import com.epmedu.animeal.tabs.search.domain.model.GroupFeedingPointsModel

data class DogsState(
    val groupFeedingPointsModels: List<GroupFeedingPointsModel> = emptyList(),
    var query: String = "",
)
//
//fun getFakeMappedFeedingPoints(): List<GroupFeedingPointsModel> {
//
//    return feedingPoints
//        .groupBy { it.city }
//        .map { GroupFeedingPointsModel(title = it.key, points = it.value) }
//}

val feedingPoints: List<FeedingPointModel> = listOf(
    FeedingPointModel(
        FeedingPoint(
            id = "0",
            title = "first",
            description = "Hungry dog",
            city = "erevan",
            animalStatus = AnimalState.RED,
            animalType = AnimalType.Dogs,
            isFavourite = true,
            lastFeeder = Feeder(id = "0", "Fred", "12:00"),
            location = MapLocation.Tbilisi,
        )
    ), FeedingPointModel(
        FeedingPoint(
            id = "1",
            title = "second",
            description = "Hungry dog",
            city = "minsk",
            animalStatus = AnimalState.RED,
            animalType = AnimalType.Dogs,
            isFavourite = true,
            lastFeeder = Feeder(id = "0", "Fred", "12:00"),
            location = MapLocation.Tbilisi,
        )
    ), FeedingPointModel(
        FeedingPoint(
            id = "2",
            title = "third",
            description = "Hungry dog",
            city = "erevan",
            animalStatus = AnimalState.RED,
            animalType = AnimalType.Dogs,
            isFavourite = true,
            lastFeeder = Feeder(id = "0", "Fred", "12:00"),
            location = MapLocation.Tbilisi,
        )
    ),
    FeedingPointModel(
        FeedingPoint(
            id = "3",
            title = "third",
            description = "Hungry dog",
            city = "erevan",
            animalStatus = AnimalState.RED,
            animalType = AnimalType.Dogs,
            isFavourite = true,
            lastFeeder = Feeder(id = "0", "Fred", "12:00"),
            location = MapLocation.Tbilisi,
        )
    ),
    FeedingPointModel(
        FeedingPoint(
            id = "4",
            title = "third",
            description = "Hungry dog",
            city = "erevan",
            animalStatus = AnimalState.RED,
            animalType = AnimalType.Dogs,
            isFavourite = true,
            lastFeeder = Feeder(id = "0", "Fred", "12:00"),
            location = MapLocation.Tbilisi,
        )
    ),
    FeedingPointModel(
        FeedingPoint(
            id = "5",
            title = "third",
            description = "Hungry dog",
            city = "erevan",
            animalStatus = AnimalState.RED,
            animalType = AnimalType.Dogs,
            isFavourite = true,
            lastFeeder = Feeder(id = "0", "Fred", "12:00"),
            location = MapLocation.Tbilisi,
        )
    ),
    FeedingPointModel(
        FeedingPoint(
            id = "6",
            title = "third",
            description = "Hungry dog",
            city = "erevan",
            animalStatus = AnimalState.RED,
            animalType = AnimalType.Dogs,
            isFavourite = true,
            lastFeeder = Feeder(id = "0", "Fred", "12:00"),
            location = MapLocation.Tbilisi,
        )
    ),
    FeedingPointModel(
        FeedingPoint(
            id = "7",
            title = "third",
            description = "Hungry dog",
            city = "erevan",
            animalStatus = AnimalState.RED,
            animalType = AnimalType.Dogs,
            isFavourite = true,
            lastFeeder = Feeder(id = "0", "Fred", "12:00"),
            location = MapLocation.Tbilisi,
        )
    ),
    FeedingPointModel(
        FeedingPoint(
            id = "8",
            title = "third",
            description = "Hungry dog",
            city = "erevan",
            animalStatus = AnimalState.RED,
            animalType = AnimalType.Dogs,
            isFavourite = true,
            lastFeeder = Feeder(id = "0", "Fred", "12:00"),
            location = MapLocation.Tbilisi,
        )
    )
)
