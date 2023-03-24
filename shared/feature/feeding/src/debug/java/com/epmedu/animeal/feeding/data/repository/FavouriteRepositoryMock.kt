package com.epmedu.animeal.feeding.data.repository

import com.amplifyframework.datastore.generated.model.Favourite
import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.feeding.domain.repository.FavouriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

internal class FavouriteRepositoryMock : FavouriteRepository {

    private val favouritesFlow = MutableStateFlow(emptyList<Favourite>())
    private val favourites
        get() = favouritesFlow.value

    override fun getFavouriteFeedingPointIds(): Flow<List<String>> {
        return favouritesFlow.map { favourites ->
            favourites.map { it.feedingPointId }
        }
    }

    override suspend fun addFeedingPointToFavourites(feedingPointId: String): ActionResult {
        favouritesFlow.update { favourites ->
            favourites + Favourite.builder()
                .userId("")
                .feedingPointId(feedingPointId)
                .build()
        }
        return ActionResult.Success
    }

    override suspend fun removeFeedingPointFromFavourites(feedingPointId: String): ActionResult {
        val favouriteToRemove = favourites.find { it.feedingPointId == feedingPointId }

        return favouriteToRemove?.let {
            favouritesFlow.value = favourites - favouriteToRemove
            ActionResult.Success
        } ?: ActionResult.Failure(Throwable())
    }
}