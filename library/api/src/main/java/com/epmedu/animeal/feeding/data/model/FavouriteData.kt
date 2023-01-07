package com.epmedu.animeal.feeding.data.model

import com.amplifyframework.datastore.generated.model.Favourite
import com.epmedu.animeal.extensions.toLocalDate
import com.epmedu.animeal.feeding.data.model.FeedingPointData.Companion.toFeedingPointData
import java.time.LocalDate

data class FavouriteData(
    val id: String? = null,
    val userId: String? = null,
    val feedingPointId: String? = null,
    val feedingPoint: FeedingPointData? = null,
    val createdAt: LocalDate? = null,
    val updatedAt: LocalDate? = null,
) {
    internal companion object {
        fun Favourite.toFavouriteData(): FavouriteData = FavouriteData(
            id = id,
            userId = userId,
            feedingPointId = feedingPointId,
            feedingPoint = feedingPoint?.toFeedingPointData(),
            createdAt = createdAt?.toLocalDate(),
            updatedAt = updatedAt?.toLocalDate()
        )
    }
}
