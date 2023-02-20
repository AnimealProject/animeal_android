package com.epmedu.animeal.feeding.data.repository

import com.amplifyframework.datastore.generated.model.Favourite
import com.epmedu.animeal.api.favourite.FavouriteApi
import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.feeding.data.mapper.toFavourite
import com.epmedu.animeal.feeding.domain.repository.FavouriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge

class FavouriteRepositoryImpl(
    private val authApi: AuthAPI,
    private val favouriteApi: FavouriteApi,
) : FavouriteRepository {

    private var cachedFavourites = emptyList<Favourite>()

    override fun getFavouriteFeedingPointIds(): Flow<List<String>> {
        return merge(
            getFavourites(),
            subscribeToFavouritesCreations(),
            subscribeToFavouritesDeletions()
        ).map { favourites ->
            favourites.map {
                it.feedingPointId
            }
        }
    }

    private fun getFavourites(): Flow<List<Favourite>> {
        return favouriteApi.getFavouriteList(authApi.currentUserId).map {
            cachedFavourites = it
            cachedFavourites
        }
    }

    private fun subscribeToFavouritesCreations(): Flow<List<Favourite>> {
        return favouriteApi.subscribeToFavouriteCreations()
            .filter { it.userId() == authApi.currentUserId }
            .map {
                cachedFavourites = cachedFavourites + it.toFavourite()
                cachedFavourites
            }
    }

    private fun subscribeToFavouritesDeletions(): Flow<List<Favourite>> {
        return favouriteApi.subscribeToFavouriteDeletions()
            .filter { it.userId() == authApi.currentUserId }
            .map { onDeleteFavourite ->
                cachedFavourites = cachedFavourites.filterNot {
                    it.feedingPointId == onDeleteFavourite.feedingPointId()
                }
                cachedFavourites
            }
    }

    override suspend fun addFavouriteFeedingPoint(feedingPointId: String) {
        favouriteApi.addFavourite(
            feedingPointId = feedingPointId,
            userId = authApi.currentUserId
        )
    }

    override suspend fun deleteFavouriteFeedingPoint(feedingPointId: String) {
        val favouriteToRemove = cachedFavourites.find { favourite ->
            favourite.feedingPointId == feedingPointId && favourite.userId == authApi.currentUserId
        }
        favouriteToRemove?.let { favouriteApi.deleteFavourite(favouriteToRemove.id) }
    }
}