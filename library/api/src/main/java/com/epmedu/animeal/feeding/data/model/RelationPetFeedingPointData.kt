package com.epmedu.animeal.feeding.data.model

import com.amplifyframework.datastore.generated.model.RelationPetFeedingPoint
import com.epmedu.animeal.extensions.toLocalDate
import com.epmedu.animeal.feeding.data.model.FeedingPointData.Companion.toFeedingPointData
import com.epmedu.animeal.feeding.data.model.PetData.Companion.toPetData
import java.time.LocalDate

data class RelationPetFeedingPointData(
    val id: String? = null,
    val pet: PetData? = null,
    val feedingPoint: FeedingPointData? = null,
    val createdAt: LocalDate? = null,
    val updatedAt: LocalDate? = null,
    val owner: String? = null,
) {
    companion object {
        fun RelationPetFeedingPoint.toRelationPetFeedingPointData(): RelationPetFeedingPointData =
            RelationPetFeedingPointData(
                id = id,
                pet = pet?.toPetData(),
                feedingPoint = feedingPoint?.toFeedingPointData(),
                createdAt = createdAt?.toLocalDate(),
                updatedAt = updatedAt?.toLocalDate(),
                owner = owner
            )
    }
}
