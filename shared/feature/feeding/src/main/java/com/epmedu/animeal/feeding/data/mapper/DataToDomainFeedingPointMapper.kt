package com.epmedu.animeal.feeding.data.mapper

import com.amplifyframework.datastore.generated.model.CategoryTag
import com.amplifyframework.datastore.generated.model.FeedingPointStatus
import com.epmedu.animeal.common.constants.DefaultConstants.EMPTY_STRING
import com.epmedu.animeal.feeding.domain.model.enum.AnimalState
import com.epmedu.animeal.feeding.presentation.model.MapLocation
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import com.epmedu.animeal.networkstorage.domain.NetworkFile
import com.amplifyframework.datastore.generated.model.FeedingPoint as DataFeedingPoint
import com.epmedu.animeal.feeding.domain.model.FeedingPoint as DomainFeedingPoint

internal suspend fun DataFeedingPoint.toDomainFeedingPoint(
    getImageFrom: suspend (fileName: String) -> NetworkFile?,
    isFavourite: Boolean = false
) = DomainFeedingPoint(
    id = id ?: EMPTY_STRING,
    title = name ?: EMPTY_STRING,
    description = description ?: EMPTY_STRING,
    city = city.orEmpty(),
    animalStatus = when (status) {
        FeedingPointStatus.starved -> AnimalState.Starved
        FeedingPointStatus.inProgress -> AnimalState.InProgress
        FeedingPointStatus.pending -> AnimalState.Pending
        else -> AnimalState.Fed
    },
    animalType = when (category?.tag) {
        CategoryTag.cats -> AnimalType.Cats
        else -> AnimalType.Dogs
    },
    isFavourite = isFavourite,
    location = MapLocation(
        latitude = location?.lat ?: 0.0,
        longitude = location?.lon ?: 0.0
    ),
    image = images.getOrNull(0)?.let { fileName -> getImageFrom(fileName) }
)