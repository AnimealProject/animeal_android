package com.epmedu.animeal.feeding.presentation.mapper

import com.epmedu.animeal.feeding.domain.model.DomainFeedState
import com.epmedu.animeal.feeding.domain.model.DomainFeedingConfirmationState
import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.feeding.domain.model.enum.AnimalState
import com.epmedu.animeal.feeding.presentation.model.FeedStatus
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.feeding.presentation.model.MapLocation
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedState
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedingConfirmationState

internal fun FeedState.toDomainFeedState(): DomainFeedState =
    DomainFeedState(
        feedPoint = this.feedPoint?.toDomainFeedingPoint(),
        feedingConfirmationState = this.feedingConfirmationState.toDomainFeedingConfirmationState(),
    )

internal fun FeedingPointModel.toDomainFeedingPoint(): FeedingPoint =
    FeedingPoint(
        id = this.id,
        title = this.title,
        description = this.description,
        city = this.city,
        animalStatus = when (this.feedStatus) {
            FeedStatus.GREEN -> AnimalState.GREEN
            FeedStatus.YELLOW -> AnimalState.YELLOW
            else -> AnimalState.RED
        },
        animalType = animalType,
        images = listOf(image),
        location = MapLocation(
            latitude = coordinates.latitude(),
            longitude = coordinates.longitude()
        )
    )

internal fun FeedingConfirmationState.toDomainFeedingConfirmationState(): DomainFeedingConfirmationState =
    when (this) {
        FeedingConfirmationState.Loading -> DomainFeedingConfirmationState.Loading
        FeedingConfirmationState.Showing -> DomainFeedingConfirmationState.Showing
        FeedingConfirmationState.FeedingStarted -> DomainFeedingConfirmationState.FeedingStarted
        FeedingConfirmationState.FeedingWasAlreadyBooked -> DomainFeedingConfirmationState.FeedingWasAlreadyBooked
        FeedingConfirmationState.Dismissed -> DomainFeedingConfirmationState.Dismissed
    }

internal fun DomainFeedingConfirmationState.toPresentationFeedingConfirmationState(): FeedingConfirmationState =
    when (this) {
        DomainFeedingConfirmationState.Loading -> FeedingConfirmationState.Loading
        DomainFeedingConfirmationState.Showing -> FeedingConfirmationState.Showing
        DomainFeedingConfirmationState.FeedingStarted -> FeedingConfirmationState.FeedingStarted
        DomainFeedingConfirmationState.FeedingWasAlreadyBooked -> FeedingConfirmationState.FeedingWasAlreadyBooked
        DomainFeedingConfirmationState.Dismissed -> FeedingConfirmationState.Dismissed
    }
