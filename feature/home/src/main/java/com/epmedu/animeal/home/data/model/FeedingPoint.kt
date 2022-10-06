package com.epmedu.animeal.home.data.model

import com.epmedu.animeal.foundation.switch.AnimalType
import com.epmedu.animeal.home.data.model.enum.AnimalPriority
import com.epmedu.animeal.home.data.model.enum.AnimalState
import com.epmedu.animeal.home.data.model.enum.Remoteness
import com.epmedu.animeal.home.presentation.model.MapLocation

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
    val location: MapLocation
)
