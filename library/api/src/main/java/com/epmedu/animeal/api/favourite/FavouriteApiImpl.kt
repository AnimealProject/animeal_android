package com.epmedu.animeal.api.favourite

import CreateFavouriteMutation
import DeleteFavouriteMutation
import com.amplifyframework.api.graphql.SubscriptionType
import com.amplifyframework.datastore.generated.model.Favourite
import com.epmedu.animeal.api.extensions.getModelList
import com.epmedu.animeal.api.extensions.performMutation
import com.epmedu.animeal.api.extensions.subscribe
import com.epmedu.animeal.common.data.wrapper.ApiResult
import kotlinx.coroutines.flow.Flow
import type.CreateFavouriteInput
import type.DeleteFavouriteInput

internal class FavouriteApiImpl : FavouriteApi {

    override fun getFavouriteList(userId: String): Flow<List<Favourite>> {
        return getModelList(predicate = Favourite.USER_ID.eq(userId))
    }

    override fun subscribeToFavouriteCreation(): Flow<Favourite> {
        return subscribe(SubscriptionType.ON_CREATE)
    }

    override fun subscribeToFavouriteDeletion(): Flow<Favourite> {
        return subscribe(SubscriptionType.ON_DELETE)
    }

    override suspend fun addFavourite(
        feedingPointId: String,
        userId: String
    ): ApiResult<Unit> {
        return CreateFavouriteMutation(
            CreateFavouriteInput.builder()
                .feedingPointId(feedingPointId)
                .userId(userId)
                .build(),
            null
        ).performMutation()
    }

    override suspend fun deleteFavourite(
        id: String
    ): ApiResult<Unit> {
        return DeleteFavouriteMutation(
            DeleteFavouriteInput.builder()
                .id(id)
                .build(),
            null
        ).performMutation()
    }
}