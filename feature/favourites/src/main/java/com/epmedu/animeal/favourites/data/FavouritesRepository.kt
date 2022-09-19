package com.epmedu.animeal.favourites.data

import com.epmedu.animeal.favourites.data.model.FavouriteFeedSpot
import kotlinx.coroutines.flow.Flow

interface FavouritesRepository {
    suspend fun getFavouriteFeedSpots(): Flow<List<FavouriteFeedSpot>>
}
