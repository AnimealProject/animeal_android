package com.epmedu.animeal.feeding.data.model

import com.amplifyframework.datastore.generated.model.Feeding
import com.epmedu.animeal.extensions.toLocalDate
import com.epmedu.animeal.feeding.data.model.FeedingPointData.Companion.toFeedingPointData
import java.time.LocalDate

data class FeedingData(
    val id: String? = null,
    val userId: String? = null,
    val images: List<String>? = null,
    val status: FeedingDataStatus? = null,
    val createdAt: LocalDate? = null,
    val updatedAt: LocalDate? = null,
    val createdBy: String? = null,
    val updatedBy: String? = null,
    val owner: String? = null,
    val feedingPoint: FeedingPointData? = null,
    val expireAt: LocalDate? = null,
) {
    internal companion object {
        fun Feeding.toFeedingData(): FeedingData = FeedingData(
            id = id,
            userId = userId,
            images = images,
            status = status?.toFeedingDataStatus(),
            createdAt = createdAt?.toLocalDate(),
            updatedAt = updatedAt?.toLocalDate(),
            createdBy = createdBy,
            updatedBy = updatedBy,
            owner = owner,
            feedingPoint = feedingPoint?.toFeedingPointData(),
            expireAt = expireAt?.toLocalDate()
        )
    }
}
