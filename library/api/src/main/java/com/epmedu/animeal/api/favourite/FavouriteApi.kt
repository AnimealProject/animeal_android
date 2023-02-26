package com.epmedu.animeal.api.favourite

import com.amplifyframework.datastore.generated.model.Favourite
import kotlinx.coroutines.flow.Flow

interface FavouriteApi {
    fun getFavouriteList(userId: String): Flow<List<Favourite>>
}