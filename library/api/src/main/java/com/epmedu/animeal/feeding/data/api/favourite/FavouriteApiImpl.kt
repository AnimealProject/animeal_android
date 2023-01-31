package com.epmedu.animeal.feeding.data.api.favourite

import com.amplifyframework.datastore.generated.model.Favourite
import com.epmedu.animeal.extensions.getModelList
import kotlinx.coroutines.flow.Flow

internal class FavouriteApiImpl : FavouriteApi {

    override fun getFavouriteList(userId: String): Flow<List<Favourite>> {
        return getModelList(
            modelType = Favourite::class.java,
            predicate = Favourite.USER_ID.eq(userId),
        )
    }
}