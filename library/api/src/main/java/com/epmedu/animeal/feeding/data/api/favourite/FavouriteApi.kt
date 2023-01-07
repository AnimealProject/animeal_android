package com.epmedu.animeal.feeding.data.api.favourite

import com.epmedu.animeal.feeding.data.model.FavouriteData
import kotlinx.coroutines.flow.Flow

interface FavouriteApi {
    fun getFavouriteList(userId: String): Flow<List<FavouriteData>>
}