package com.epmedu.animeal.api.favourite

import CreateFavouriteMutation
import DeleteFavouriteMutation
import OnCreateFavouriteSubscription
import OnCreateFavouriteSubscription.OnCreateFavourite
import OnDeleteFavouriteSubscription
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient
import com.amplifyframework.datastore.generated.model.Favourite
import com.epmedu.animeal.api.extensions.getModelList
import com.epmedu.animeal.api.extensions.performMutation
import com.epmedu.animeal.api.extensions.subscribe
import com.epmedu.animeal.common.data.wrapper.ApiResult
import kotlinx.coroutines.flow.Flow
import type.CreateFavouriteInput
import type.DeleteFavouriteInput

internal class FavouriteApiImpl(
    private val awsAppSyncClient: AWSAppSyncClient
) : FavouriteApi {

    override fun getFavouriteList(userId: String): Flow<List<Favourite>> {
        return getModelList(predicate = Favourite.USER_ID.eq(userId))
    }

    override fun subscribeToFavouriteCreation(): Flow<OnCreateFavourite> {
        return awsAppSyncClient.subscribe(
            subscription = OnCreateFavouriteSubscription(),
            getData = { onCreateFavourite() }
        )
    }

    override fun subscribeToFavouriteDeletion(): Flow<OnDeleteFavouriteSubscription.OnDeleteFavourite> {
        return awsAppSyncClient.subscribe(
            subscription = OnDeleteFavouriteSubscription(),
            getData = { onDeleteFavourite() }
        )
    }

    override suspend fun addFavourite(
        feedingPointId: String,
        userId: String
    ): ApiResult<Unit> {
        return awsAppSyncClient.performMutation(
            CreateFavouriteMutation(
                CreateFavouriteInput.builder()
                    .id(null)
                    .feedingPointId(feedingPointId)
                    .userId(userId)
                    .build(),
                null
            )
        )
    }

    override suspend fun deleteFavourite(
        id: String
    ): ApiResult<Unit> {
        return awsAppSyncClient.performMutation(
            DeleteFavouriteMutation(
                DeleteFavouriteInput.builder()
                    .id(id)
                    .build(),
                null
            )
        )
    }
}