package com.epmedu.animeal.feeding.data.repository

import com.amplifyframework.datastore.generated.model.Favourite
import com.epmedu.animeal.api.favourite.FavouriteApi
import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.feeding.data.mapper.toActionResult
import com.epmedu.animeal.feeding.data.mapper.toFavourite
import com.epmedu.animeal.feeding.domain.repository.FavouriteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch

class FavouriteRepositoryImpl(
    dispatchers: Dispatchers,
    private val authApi: AuthAPI,
    private val favouriteApi: FavouriteApi,
) : FavouriteRepository {

    private val coroutineScope = CoroutineScope(dispatchers.IO)

    private val favouritesFlow = MutableStateFlow(emptyList<Favourite>())
    private val favourites
        get() = favouritesFlow.value

    init {
        coroutineScope.launch {
            merge(
                getFavourites(),
                subscribeToFavouritesCreations(),
                subscribeToFavouritesDeletions()
            ).collect {
                favouritesFlow.value = it
            }
        }
    }

    override fun getFavouriteFeedingPointIds(): Flow<List<String>> {
        return favouritesFlow.map { favourites ->
            favourites.map { it.feedingPointId }
        }
    }

    private fun getFavourites(): Flow<List<Favourite>> {
        return favouriteApi.getFavouriteList(authApi.currentUserId)
    }

    private fun subscribeToFavouritesCreations(): Flow<List<Favourite>> {
        return favouriteApi.subscribeToFavouriteCreation()
            .filter { it.userId() == authApi.currentUserId }
            .map { favourites + it.toFavourite() }
    }

    private fun subscribeToFavouritesDeletions(): Flow<List<Favourite>> {
        return favouriteApi.subscribeToFavouriteDeletion()
            .filter { it.userId() == authApi.currentUserId }
            .map { onDeleteFavourite ->
                favourites.filterNot { it.feedingPointId == onDeleteFavourite.feedingPointId() }
            }
    }

    override suspend fun addFeedingPointToFavourites(feedingPointId: String): ActionResult {
        return favouriteApi.addFavourite(
            feedingPointId = feedingPointId,
            userId = authApi.currentUserId
        ).toActionResult()
    }

    override suspend fun removeFeedingPointFromFavourites(feedingPointId: String): ActionResult {
        return favourites.find { it.feedingPointId == feedingPointId }?.let { favourite ->
            favouriteApi.deleteFavourite(favourite.id)
        }.toActionResult()
    }
}