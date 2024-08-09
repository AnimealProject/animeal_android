package com.epmedu.animeal.feeding.domain.model

import com.epmedu.animeal.feeding.domain.model.enum.AnimalState
import com.epmedu.animeal.feeding.presentation.model.MapLocation
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import com.epmedu.animeal.networkstorage.domain.NetworkFile
import com.epmedu.animeal.users.domain.model.User

data class FeedingPoint(
    val id: String,
    val title: String,
    val description: String,
    val city: String,
    val animalStatus: AnimalState,
    val animalType: AnimalType,
    val location: MapLocation,
    val assignedModerators: List<User>?,
    val isFavourite: Boolean = false,
    val image: NetworkFile? = null,
)
