package com.epmedu.animeal.feeding.data.mapper

import com.amplifyframework.datastore.generated.model.CategoryTag
import com.amplifyframework.datastore.generated.model.FeedingPointStatus
import com.epmedu.animeal.common.constants.DefaultConstants.EMPTY_STRING
import com.epmedu.animeal.feeding.domain.model.enum.AnimalState
import com.epmedu.animeal.feeding.presentation.model.MapLocation
import com.amplifyframework.datastore.generated.model.FeedingPoint as DataFeedingPoint
import com.epmedu.animeal.feeding.domain.model.FeedingPoint as DomainFeedingPoint
import com.epmedu.animeal.foundation.switch.model.AnimalType

internal fun DataFeedingPoint.toDomainFeedingPoint(isFavourite: Boolean = false) =
    DomainFeedingPoint(
        id = id ?: EMPTY_STRING,
        title = name ?: EMPTY_STRING,
        description = description ?: EMPTY_STRING,
        animalStatus = when (status) {
            FeedingPointStatus.fed -> AnimalState.GREEN
            FeedingPointStatus.pending -> AnimalState.YELLOW
            else -> AnimalState.RED
        },
        animalType = when (category?.tag) {
            CategoryTag.cats -> AnimalType.Cats
            else -> AnimalType.Dogs
        },
        isFavourite = isFavourite,
        location = MapLocation(
            latitude = location?.lat ?: 0.0,
            longitude = location?.lon ?: 0.0
        )
    )