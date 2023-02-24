package com.epmedu.animeal.feeding.domain.model

import com.epmedu.animeal.feeding.domain.model.enum.AnimalState
import com.epmedu.animeal.feeding.domain.model.enum.Remoteness
import com.epmedu.animeal.feeding.presentation.model.MapLocation
import com.epmedu.animeal.foundation.switch.AnimalType

data class FeedingPoint(
    val id: String,
    val title: String,
    val description: String,
    val animalStatus: AnimalState,
    val animalType: AnimalType,
    val isFavourite: Boolean = false,
    val lastFeeder: Feeder = Feeder(),
    val remoteness: Remoteness = Remoteness.ANY,
    val location: MapLocation,
)
