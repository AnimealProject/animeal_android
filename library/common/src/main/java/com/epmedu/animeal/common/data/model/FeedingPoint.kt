package com.epmedu.animeal.common.data.model

import com.epmedu.animeal.common.data.enum.AnimalPriority
import com.epmedu.animeal.common.data.enum.AnimalState
import com.epmedu.animeal.common.data.enum.Remoteness
import com.epmedu.animeal.foundation.switch.Tab

data class FeedingPoint(
    val id: Int, // For future implementations
    val priority: AnimalPriority,
    val status: AnimalState,
    val tab: Tab,
    val isFavourite: Boolean = false,
    val remoteness: Remoteness = Remoteness.ANY,
    var xCoordinate: Double,
    var yCoordinate: Double
)