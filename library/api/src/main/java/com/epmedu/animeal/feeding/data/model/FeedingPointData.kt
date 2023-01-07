package com.epmedu.animeal.feeding.data.model

import com.amplifyframework.datastore.generated.model.FeedingPoint
import com.epmedu.animeal.extensions.toLocalDate
import com.epmedu.animeal.feeding.data.model.FeedingData.Companion.toFeedingData
import com.epmedu.animeal.feeding.data.model.FeedingPointDataLocalisation.Companion.toFeedingPointDataLocalisation
import com.epmedu.animeal.feeding.data.model.LocationData.Companion.toLocationData
import com.epmedu.animeal.feeding.data.model.RelationPetFeedingPointData.Companion.toRelationPetFeedingPointData
import java.time.LocalDate

data class FeedingPointData(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val city: String? = null,
    val street: String? = null,
    val address: String? = null,
    val images: List<String>? = null,
    val location: LocationData? = null,
    val region: String? = null,
    val neighborhood: String? = null,
    val distance: Double? = null,
    val status: FeedingPointDataStatus? = null,
    val localisations: List<FeedingPointDataLocalisation?>? = null,
    val statusUpdatedAt: LocalDate? = null,
    val createdAt: LocalDate? = null,
    val updatedAt: LocalDate? = null,
    val createdBy: String? = null,
    val updatedBy: String? = null,
    val owner: String? = null,
    val pets: List<RelationPetFeedingPointData?>? = null,
    val categoryTag: CategoryTagData? = null,
    val feedings: List<FeedingData?>? = null,
    val cover: String? = null,
    val feedingPointCategoryId: String? = null,
) {
    companion object {
        fun FeedingPoint.toFeedingPointData(): FeedingPointData = FeedingPointData(
            id = id,
            name = name,
            description = description,
            city = city,
            street = street,
            address = address,
            images = images,
            location = location?.toLocationData(),
            region = region,
            neighborhood = neighborhood,
            distance = distance,
            status = status?.toFeedingPointDataStatus(),
            localisations = i18n?.map { it?.toFeedingPointDataLocalisation() },
            statusUpdatedAt = statusUpdatedAt?.toLocalDate(),
            createdAt = createdAt?.toLocalDate(),
            updatedAt = updatedAt?.toLocalDate(),
            createdBy = createdBy,
            updatedBy = updatedBy,
            owner = owner,
            pets = pets?.map { it?.toRelationPetFeedingPointData() },
            categoryTag = category?.tag?.toCategoryTagData(),
            feedings = feedings?.map { it?.toFeedingData() },
            cover = cover,
            feedingPointCategoryId = feedingPointCategoryId
        )
    }
}