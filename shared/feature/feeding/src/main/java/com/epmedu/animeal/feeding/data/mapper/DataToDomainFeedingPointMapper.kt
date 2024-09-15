package com.epmedu.animeal.feeding.data.mapper

import com.amplifyframework.datastore.generated.model.CategoryTag
import com.amplifyframework.datastore.generated.model.FeedingPoint
import com.amplifyframework.datastore.generated.model.FeedingPointStatus
import com.epmedu.animeal.common.constants.DefaultConstants.EMPTY_STRING
import com.epmedu.animeal.feeding.domain.model.enum.AnimalState
import com.epmedu.animeal.feeding.presentation.model.MapLocation
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import com.epmedu.animeal.networkstorage.domain.NetworkFile
import com.epmedu.animeal.users.domain.model.User
import com.amplifyframework.datastore.generated.model.FeedingPoint as DataFeedingPoint
import com.epmedu.animeal.feeding.domain.model.FeedingPoint as DomainFeedingPoint

internal suspend fun DataFeedingPoint.toDomainFeedingPoint(
    getImageFrom: suspend (fileName: String) -> NetworkFile?,
    moderatorsMap: Map<String, User>?,
    isFavourite: Boolean = false
) = DomainFeedingPoint(
    id = id ?: EMPTY_STRING,
    title = name ?: EMPTY_STRING,
    description = description ?: EMPTY_STRING,
    city = city.orEmpty(),
    animalStatus = status.toAnimalState(),
    animalType = category?.tag.toAnimalType(),
    isFavourite = isFavourite,
    location = MapLocation(
        latitude = location?.lat ?: 0.0,
        longitude = location?.lon ?: 0.0
    ),
    image = images.getOrNull(0)?.let { fileName -> getImageFrom(fileName) },
    assignedModerators = getAssignedModerators(moderatorsMap)
)

private fun FeedingPointStatus?.toAnimalState(): AnimalState {
    return when (this) {
        FeedingPointStatus.starved -> AnimalState.Starved
        FeedingPointStatus.inProgress -> AnimalState.InProgress
        FeedingPointStatus.pending -> AnimalState.Pending
        else -> AnimalState.Fed
    }
}

private fun CategoryTag?.toAnimalType(): AnimalType {
    return when (this) {
        CategoryTag.cats -> AnimalType.Cats
        else -> AnimalType.Dogs
    }
}

private fun FeedingPoint.getAssignedModerators(moderatorsMap: Map<String, User>?): List<User>? {
    return moderatorsMap?.let {
        users?.mapNotNull { assignedModerator ->
            assignedModerator?.userId?.let { id -> moderatorsMap[id] }
        }
    }
}
