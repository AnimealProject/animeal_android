package com.epmedu.animeal.feeding.domain.usecase

import com.epmedu.animeal.feeding.domain.repository.FavouriteRepository

class AddFavouriteFeedingPointUseCase(
    private val repo: FavouriteRepository
) {

    suspend operator fun invoke(feedingPointId: String) {
        repo.addFavouriteFeedingPoint(feedingPointId)
    }
}