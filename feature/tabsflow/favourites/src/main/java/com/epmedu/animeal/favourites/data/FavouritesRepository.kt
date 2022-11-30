package com.epmedu.animeal.favourites.data

import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import kotlinx.coroutines.flow.Flow

interface FavouritesRepository {
    fun getFavouriteFeedSpots(): Flow<List<FeedingPoint>>
}
