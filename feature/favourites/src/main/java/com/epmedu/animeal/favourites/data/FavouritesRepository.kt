package com.epmedu.animeal.favourites.data

import com.epmedu.animeal.favourites.data.model.FavouriteFeedingPoint
import kotlinx.coroutines.flow.Flow

interface FavouritesRepository {
    suspend fun getFavouriteFeedSpots(): Flow<List<FavouriteFeedingPoint>>
}
