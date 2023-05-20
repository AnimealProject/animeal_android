package com.epmedu.animeal.feeding.data.repository

import com.amplifyframework.datastore.generated.model.Favourite
import com.epmedu.animeal.api.favourite.FavouriteApi
import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.feeding.data.mapper.toActionResult
import com.epmedu.animeal.feeding.domain.repository.FavouriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach

internal class FavouriteRepositoryImpl(
    private val dispatchers: Dispatchers,
    private val authApi: AuthAPI,
    private val favouriteApi: FavouriteApi,
) : FavouriteRepository {

    private val favouritesFlow = MutableStateFlow(emptyList<Favourite>())
    private val favourites
        get() = favouritesFlow.value

    override fun getFavouriteFeedingPointIds(): Flow<List<String>> {
        return merge(favouritesFlow, fetchFavourites()).map { favourites ->
            favourites.map { it.feedingPointId }
        }
            .distinctUntilChanged()
            .flowOn(dispatchers.IO)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun fetchFavourites(): Flow<List<Favourite>> {
        return flow { emit(authApi.getCurrentUserId()) }.flatMapLatest { userId ->
            merge(
                favouriteApi.getFavouriteList(userId),
                subscribeToFavouritesCreations(),
                subscribeToFavouritesDeletions()
            )
        }.onEach {
            favouritesFlow.value = it
        }
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