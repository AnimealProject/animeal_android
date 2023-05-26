package com.epmedu.animeal.api.favourite

import CreateFavouriteMutation
import DeleteFavouriteMutation
import com.amplifyframework.api.graphql.SubscriptionType
import com.amplifyframework.datastore.generated.model.Favourite
import com.epmedu.animeal.api.AnimealApi
import com.epmedu.animeal.common.data.wrapper.ApiResult
import kotlinx.coroutines.flow.Flow
import type.CreateFavouriteInput
import type.DeleteFavouriteInput

internal class FavouriteApiImpl(
    private val animealApi: AnimealApi
) : FavouriteApi {

    override fun getFavouriteList(userId: String): Flow<List<Favourite>> {
        return animealApi.getModelList(
            modelClass = Favourite::class.java,
            predicate = Favourite.USER_ID.eq(userId)
        )
    }

    override fun subscribeToFavouriteCreation(): Flow<Favourite> {
        return animealApi.subscribe(
            subscriptionType = SubscriptionType.ON_CREATE,
            modelClass = Favourite::class.java
        )
    }

    override fun subscribeToFavouriteDeletion(): Flow<Favourite> {
        return animealApi.subscribe(
            subscriptionType = SubscriptionType.ON_DELETE,
            modelClass = Favourite::class.java
        )
    }

    override suspend fun addFavourite(
        feedingPointId: String,
        userId: String
    ): ApiResult<Unit> {
        val mutation = CreateFavouriteMutation(
            CreateFavouriteInput.builder()
                .feedingPointId(feedingPointId)
                .userId(userId)
                .build(),
            null
        )
        return animealApi.launchMutation(
            mutation = mutation,
            responseClass = Unit::class.java
        )
    }

    override suspend fun deleteFavourite(
        id: String
    ): ApiResult<Unit> {
        val mutation = DeleteFavouriteMutation(
            DeleteFavouriteInput.builder()
                .id(id)
                .build(),
            null
        )
        return animealApi.launchMutation(
            mutation = mutation,
            responseClass = Unit::class.java
        )
    }
}