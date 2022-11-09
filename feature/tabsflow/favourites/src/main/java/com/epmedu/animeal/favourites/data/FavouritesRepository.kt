package com.epmedu.animeal.favourites.data

import com.epmedu.animeal.feeding.data.model.FeedingPoint
import kotlinx.coroutines.flow.Flow

interface FavouritesRepository {
    fun getFavouriteFeedSpots(): Flow<List<FeedingPoint>>
}
