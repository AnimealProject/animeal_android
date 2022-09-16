package com.epmedu.animeal.home.data.model

import com.epmedu.animeal.common.data.model.MapLocation
import com.epmedu.animeal.foundation.switch.AnimalType
import com.epmedu.animeal.home.data.model.enum.AnimalPriority
import com.epmedu.animeal.home.data.model.enum.AnimalState
import com.epmedu.animeal.home.data.model.enum.Remoteness

data class FeedingPoint(
    val id: Int, // For future implementations
    val animalPriority: AnimalPriority,
    val animalStatus: AnimalState,
    val animalType: AnimalType,
    val isFavourite: Boolean = false,
    val remoteness: Remoteness = Remoteness.ANY,
    val location: MapLocation
)