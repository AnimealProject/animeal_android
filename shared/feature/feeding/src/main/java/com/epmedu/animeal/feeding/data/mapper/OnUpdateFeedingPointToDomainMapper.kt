package com.epmedu.animeal.feeding.data.mapper

import com.epmedu.animeal.feeding.domain.model.FeedingPoint as DomainFeedingPoint
import OnUpdateFeedingPointSubscription.OnUpdateFeedingPoint
import com.epmedu.animeal.feeding.domain.model.enum.AnimalState
import com.epmedu.animeal.feeding.presentation.model.MapLocation
import com.epmedu.animeal.foundation.tabs.model.AnimalType

internal fun OnUpdateFeedingPoint.toDomainFeedingPoint() =
    DomainFeedingPoint(
        id = id(),
        title = name(),
        description = description(),
        city = city(),
        animalStatus = when (status()) {
            type.FeedingPointStatus.fed -> AnimalState.GREEN
            else -> AnimalState.RED
        },
        animalType = when (category()?.tag()) {
            type.CategoryTag.cats -> AnimalType.Cats
            else -> AnimalType.Dogs
        },
        location = MapLocation(
            latitude = location().lat(),
            longitude = location().lon()
        )
    )
