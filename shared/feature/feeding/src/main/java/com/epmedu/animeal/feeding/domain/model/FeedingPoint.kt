package com.epmedu.animeal.feeding.domain.model

import com.epmedu.animeal.feeding.domain.model.enum.AnimalState
import com.epmedu.animeal.feeding.domain.model.enum.Remoteness
import com.epmedu.animeal.feeding.presentation.model.MapLocation
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import com.epmedu.animeal.networkstorage.domain.NetworkFile

data class FeedingPoint(
    val id: String,
    val title: String,
    val description: String,
    val city: String,
    val animalStatus: AnimalState,
    val animalType: AnimalType,
    val isFavourite: Boolean = false,
    val remoteness: Remoteness = Remoteness.ANY,
    val location: MapLocation,
    val image: NetworkFile? = null,
)
