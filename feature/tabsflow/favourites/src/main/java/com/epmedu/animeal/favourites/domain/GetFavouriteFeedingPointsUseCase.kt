package com.epmedu.animeal.favourites.domain

import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository

class GetFavouriteFeedingPointsUseCase(private val feedingPointRepository: FeedingPointRepository) {

    operator fun invoke() = feedingPointRepository.getFavourites()
}