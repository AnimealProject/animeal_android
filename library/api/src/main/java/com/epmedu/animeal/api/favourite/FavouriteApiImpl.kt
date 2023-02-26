package com.epmedu.animeal.api.favourite

import com.amplifyframework.datastore.generated.model.Favourite
import com.epmedu.animeal.api.extensions.getModelList
import kotlinx.coroutines.flow.Flow

internal class FavouriteApiImpl : FavouriteApi {

    override fun getFavouriteList(userId: String): Flow<List<Favourite>> {
        return getModelList(predicate = Favourite.USER_ID.eq(userId))
    }
}