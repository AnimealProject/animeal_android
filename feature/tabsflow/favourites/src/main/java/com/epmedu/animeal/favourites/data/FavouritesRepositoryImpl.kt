package com.epmedu.animeal.favourites.data

import com.epmedu.animeal.favourites.data.model.FavouriteFeedingPoint
import com.epmedu.animeal.foundation.common.FeedStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FavouritesRepositoryImpl : FavouritesRepository {

    override fun getFavouriteFeedSpots(): Flow<List<FavouriteFeedingPoint>> = flowOf(
        listOf(
            FavouriteFeedingPoint(
                id = 1,
                title = "Europe Square park",
                status = FeedStatus.RED,
                isFavourite = true,
            ),
            FavouriteFeedingPoint(
                id = 2,
                title = "University garden",
                status = FeedStatus.RED,
                isFavourite = true,
            ),
            FavouriteFeedingPoint(
                id = 3,
                title = "Gorky park",
                status = FeedStatus.GREEN,
                isFavourite = true,
            )
        )
    )
}