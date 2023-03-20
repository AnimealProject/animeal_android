package com.epmedu.animeal.feeding.data.repository

import com.amplifyframework.datastore.generated.model.Favourite
import com.epmedu.animeal.api.favourite.FavouriteApi
import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.feeding.data.mapper.toActionResult
import com.epmedu.animeal.feeding.domain.repository.FavouriteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
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

    private suspend fun getFavourites(): Flow<List<Favourite>> {
        return favouriteApi.getFavouriteList(authApi.getCurrentUserId())
    }

    private fun subscribeToFavouritesCreations(): Flow<List<Favourite>> {
        return favouriteApi.subscribeToFavouriteCreation()
            .filter { it.userId == authApi.getCurrentUserId() }
            .map { favourites + it }
    }

    private fun subscribeToFavouritesDeletions(): Flow<List<Favourite>> {
        return favouriteApi.subscribeToFavouriteDeletion()
            .filter { it.userId == authApi.getCurrentUserId() }
            .map { onDeleteFavourite ->
                favourites.filterNot { it.feedingPointId == onDeleteFavourite.feedingPointId }
            }
    }

    override suspend fun addFeedingPointToFavourites(feedingPointId: String): ActionResult {
        return favouriteApi.addFavourite(
            feedingPointId = feedingPointId,
            userId = authApi.getCurrentUserId()
        ).toActionResult()
    }

    override suspend fun removeFeedingPointFromFavourites(feedingPointId: String): ActionResult {
        return favourites.find { it.feedingPointId == feedingPointId }?.let { favourite ->
            favouriteApi.deleteFavourite(favourite.id)
        }.toActionResult()
    }
}