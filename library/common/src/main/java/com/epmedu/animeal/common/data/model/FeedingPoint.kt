package com.epmedu.animeal.common.data.model

import com.epmedu.animeal.common.data.enum.AnimalPriority
import com.epmedu.animeal.common.data.enum.AnimalState
import com.epmedu.animeal.common.data.enum.Remoteness
import com.epmedu.animeal.foundation.switch.AnimalType

data class FeedingPoint(
    val id: Int, // For future implementations
    val animalPriority: AnimalPriority,
    val animalStatus: AnimalState,
    val animalType: AnimalType,
    val isFavourite: Boolean = false,
    val remoteness: Remoteness = Remoteness.ANY,
    var xCoordinate: Double,
    var yCoordinate: Double
)