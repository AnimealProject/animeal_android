package com.epmedu.animeal.feeding.data.api.favourite

import com.amplifyframework.datastore.generated.model.Favourite
import com.epmedu.animeal.extensions.getModelList
import com.epmedu.animeal.feeding.data.model.FavouriteData
import com.epmedu.animeal.feeding.data.model.FavouriteData.Companion.toFavouriteData
import kotlinx.coroutines.flow.Flow

internal class FavouriteApiImpl : FavouriteApi {
    override fun getFavouriteList(userId: String): Flow<List<FavouriteData>> {
        return getModelList(
            modelType = Favourite::class.java,
            predicate = Favourite.USER_ID.eq(userId),
            map = { toFavouriteData() }
        )
    }
}