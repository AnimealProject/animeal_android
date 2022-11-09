package com.epmedu.animeal.feeding.data.model

import com.epmedu.animeal.feeding.data.model.enum.AnimalPriority
import com.epmedu.animeal.feeding.data.model.enum.AnimalState
import com.epmedu.animeal.feeding.data.model.enum.Remoteness
import com.epmedu.animeal.feeding.presentation.model.MapLocation
import com.epmedu.animeal.foundation.switch.AnimalType

data class FeedingPoint(
    val id: Int, // For future implementations
    val title: String,
    val description: String,
    val animalPriority: AnimalPriority,
    val animalStatus: AnimalState,
    val animalType: AnimalType,
    val isFavourite: Boolean = false,
    val lastFeeder: Feeder,
    val remoteness: Remoteness = Remoteness.ANY,
    val location: MapLocation,
)
