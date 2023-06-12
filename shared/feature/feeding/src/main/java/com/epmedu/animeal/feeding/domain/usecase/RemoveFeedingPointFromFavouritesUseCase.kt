package com.epmedu.animeal.feeding.domain.usecase

import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.feeding.domain.repository.FavouriteRepository

class RemoveFeedingPointFromFavouritesUseCase(
    private val repository: FavouriteRepository
) {

    suspend operator fun invoke(feedingPointId: String): ActionResult<Unit> {
        return repository.removeFeedingPointFromFavourites(feedingPointId)
    }
}