package com.epmedu.animeal.feeding.presentation.mapper

import com.epmedu.animeal.feeding.domain.model.DomainFeedState
import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.feeding.domain.model.enum.AnimalState
import com.epmedu.animeal.feeding.presentation.model.FeedStatus
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.feeding.presentation.model.MapLocation
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedState

internal fun FeedState.toDomainFeedState(): DomainFeedState =
    DomainFeedState(
        feedPoint = feedPoint?.toDomainFeedingPoint(),
        feedingConfirmationState = feedingConfirmationState,
    )

internal fun FeedingPointModel.toDomainFeedingPoint(): FeedingPoint =
    FeedingPoint(
        id = this.id,
        title = this.title,
        description = this.description,
        city = this.city,
        animalStatus = when (this.feedStatus) {
            FeedStatus.Fed -> AnimalState.Fed
            FeedStatus.Pending -> AnimalState.Pending
            FeedStatus.InProgress -> AnimalState.InProgress
            else -> AnimalState.Starved
        },
        animalType = animalType,
        isFavourite = isFavourite,
        image = image,
        location = MapLocation(
            latitude = coordinates.latitude(),
            longitude = coordinates.longitude()
        )
    )
