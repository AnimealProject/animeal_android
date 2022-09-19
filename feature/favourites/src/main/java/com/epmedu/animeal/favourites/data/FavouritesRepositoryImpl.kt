package com.epmedu.animeal.favourites.data

import com.epmedu.animeal.favourites.data.model.FavouriteFeedSpot
import com.epmedu.animeal.foundation.common.FeedStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FavouritesRepositoryImpl : FavouritesRepository {

    override suspend fun getFavouriteFeedSpots(): Flow<List<FavouriteFeedSpot>> = flowOf(
        listOf(
            FavouriteFeedSpot(
                id = 1,
                title = "Europe Square park",
                status = FeedStatus.ORANGE,
                isFavourite = true,
            ),
            FavouriteFeedSpot(
                id = 2,
                title = "University garden",
                status = FeedStatus.RED,
                isFavourite = true,
            ),
            FavouriteFeedSpot(
                id = 3,
                title = "Gorky park",
                status = FeedStatus.GREEN,
                isFavourite = true,
            )
        )
    )
}