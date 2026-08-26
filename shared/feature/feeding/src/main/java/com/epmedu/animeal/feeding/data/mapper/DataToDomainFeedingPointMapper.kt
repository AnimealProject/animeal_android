package com.epmedu.animeal.feeding.data.mapper

import com.amplifyframework.datastore.generated.model.CategoryTag
import com.amplifyframework.datastore.generated.model.FeedingPoint
import com.amplifyframework.datastore.generated.model.FeedingPointStatus
import com.epmedu.animeal.common.constants.DefaultConstants.NA_STRING
import com.epmedu.animeal.feeding.domain.model.enum.AnimalState
import com.epmedu.animeal.feeding.presentation.model.MapLocation
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import com.epmedu.animeal.networkstorage.domain.NetworkFile
import com.epmedu.animeal.users.domain.model.User
import com.amplifyframework.datastore.generated.model.FeedingPoint as DataFeedingPoint
import com.amplifyframework.datastore.generated.model.FeedingPointStatus as DataFeedingPointStatus
import com.epmedu.animeal.feeding.domain.model.FeedingPoint as DomainFeedingPoint
import type.CategoryTag as TypeCategoryTag
import type.FeedingPointStatus as TypeFeedingPointStatus

internal suspend fun DataFeedingPoint.toDomainFeedingPoint(
    getImageFrom: suspend (fileName: String) -> NetworkFile?,
    moderatorsMap: Map<String, User>?,
    isFavourite: Boolean = false,
    locale: String? = null
): DomainFeedingPoint {
    var localeData = i18n?.firstOrNull { it.locale == locale }
    return DomainFeedingPoint(
        id = id.orEmpty(),
        code = code ?: NA_STRING,
        title = localeData?.name ?: name.orEmpty(),
        description = localeData?.description ?: description.orEmpty(),
        city = localeData?.city ?: city.orEmpty(),
        address = localeData?.address ?: address.orEmpty(),
        animalStatus = status.toAnimalState(),
        animalType = category?.tag.toAnimalType(),
        isFavourite = isFavourite,
        location = MapLocation(
            latitude = location?.lat ?: 0.0,
            longitude = location?.lon ?: 0.0
        ),
        image = (cover?.takeIf { it.isNotEmpty() } ?: images?.firstOrNull())
            ?.let { getImageFrom(it) },
        assignedModerators = getAssignedModerators(moderatorsMap),
        inactive = disabled ?: false
    )
}

internal suspend fun GetFeedingPointsQuery.GetFeedingPoint.toDomainFeedingPoint(
    getImageFrom: suspend (fileName: String) -> NetworkFile?,
    moderatorsMap: Map<String, User>?,
    isFavourite: Boolean = false,
    locale: String? = null
): DomainFeedingPoint {
    var localeData = i18n()?.firstOrNull { it.locale() == locale }
    return DomainFeedingPoint(
        id = id(),
        code = code() ?: NA_STRING,
        title = localeData?.name() ?: name(),
        description = localeData?.description() ?: description(),
        city = localeData?.city() ?: city(),
        address = localeData?.address() ?: address(),
        animalStatus = status().toAnimalState(),
        animalType = category()?.tag().toAnimalType(),
        isFavourite = isFavourite,
        location = MapLocation(
            latitude = location().lat(),
            longitude = location().lon()
        ),
        image = (cover()?.takeIf { it.isNotEmpty() } ?: images()?.firstOrNull())
            ?.let { getImageFrom(it) },
        assignedModerators = getAssignedModerators(moderatorsMap),
        inactive = disabled() ?: false
    )
}

private fun DataFeedingPointStatus?.toAnimalState(): AnimalState {
    return when (this) {
        FeedingPointStatus.starved -> AnimalState.Starved
        FeedingPointStatus.inProgress -> AnimalState.InProgress
        FeedingPointStatus.pending -> AnimalState.Pending
        else -> AnimalState.Fed
    }
}

private fun TypeFeedingPointStatus?.toAnimalState(): AnimalState {
    return when (this) {
        TypeFeedingPointStatus.starved -> AnimalState.Starved
        TypeFeedingPointStatus.inProgress -> AnimalState.InProgress
        TypeFeedingPointStatus.pending -> AnimalState.Pending
        else -> AnimalState.Fed
    }
}

private fun CategoryTag?.toAnimalType(): AnimalType {
    return when (this) {
        CategoryTag.cats -> AnimalType.Cats
        else -> AnimalType.Dogs
    }
}

private fun TypeCategoryTag?.toAnimalType(): AnimalType {
    return when (this) {
        TypeCategoryTag.cats -> AnimalType.Cats
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

private fun GetFeedingPointsQuery.GetFeedingPoint.getAssignedModerators(moderatorsMap: Map<String, User>?): List<User>? {
    return moderatorsMap?.let {
        users()?.items()?.mapNotNull { assignedModerator ->
            assignedModerator?.userId()?.let { id -> moderatorsMap[id] }
        }
    }
}
