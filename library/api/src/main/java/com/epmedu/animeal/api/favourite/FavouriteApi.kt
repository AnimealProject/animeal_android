package com.epmedu.animeal.api.favourite

import OnCreateFavouriteSubscription.OnCreateFavourite
import OnDeleteFavouriteSubscription.OnDeleteFavourite
import com.amplifyframework.datastore.generated.model.Favourite
import com.epmedu.animeal.common.data.wrapper.ApiResult
import kotlinx.coroutines.flow.Flow

interface FavouriteApi {
    fun getFavouriteList(userId: String): Flow<List<Favourite>>

    fun subscribeToFavouriteCreation(): Flow<OnCreateFavourite>

    fun subscribeToFavouriteDeletion(): Flow<OnDeleteFavourite>

    suspend fun addFavourite(
        feedingPointId: String,
        userId: String
    ): ApiResult<Unit>

    suspend fun deleteFavourite(
        id: String
    ): ApiResult<Unit>
}