package com.epmedu.animeal.feeding.data.mapper

import com.amplifyframework.datastore.generated.model.FeedingPoint as DataFeedingPoint
import com.epmedu.animeal.feeding.domain.model.FeedingPoint as DomainFeedingPoint
import com.amplifyframework.datastore.generated.model.CategoryTag
import com.amplifyframework.datastore.generated.model.Feeding
import com.amplifyframework.datastore.generated.model.FeedingPointStatus
import com.epmedu.animeal.common.constants.DefaultConstants
import com.epmedu.animeal.feeding.domain.model.Feeder
import com.epmedu.animeal.feeding.domain.model.enum.AnimalState
import com.epmedu.animeal.feeding.presentation.model.MapLocation
import com.epmedu.animeal.foundation.switch.AnimalType

internal fun DataFeedingPoint.toDomainFeedingPoint(isFavourite: Boolean) =
    DomainFeedingPoint(
        id = id ?: DefaultConstants.EMPTY_STRING,
        title = name ?: DefaultConstants.EMPTY_STRING,
        description = description ?: DefaultConstants.EMPTY_STRING,
        animalStatus = when (status) {
            FeedingPointStatus.fed -> AnimalState.GREEN
            else -> AnimalState.RED
        },
        animalType = when (category?.tag) {
            CategoryTag.cats -> AnimalType.Cats
            else -> AnimalType.Dogs
        },
        isFavourite = isFavourite,
        lastFeeder = feedings?.lastOrNull()?.toFeeder() ?: Feeder(),
        location = MapLocation(
            latitude = location?.lat ?: 0.0,
            longitude = location?.lon ?: 0.0
        )
    )

internal fun Feeding.toFeeder() = Feeder(
    id = userId ?: DefaultConstants.EMPTY_STRING,
    name = updatedBy ?: DefaultConstants.EMPTY_STRING,
    time = updatedAt?.format() ?: DefaultConstants.EMPTY_STRING
)