package com.epmedu.animeal.feeding.data.repository

import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.feeding.data.api.favourite.FavouriteApi
import com.epmedu.animeal.feeding.data.api.feeding.FeedingPointApi
import com.epmedu.animeal.feeding.data.mapper.toDomainFeedingPoint
import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import com.epmedu.animeal.foundation.switch.AnimalType
import com.epmedu.animeal.profile.data.model.Profile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import com.epmedu.animeal.feeding.domain.model.FeedingPoint as DomainFeedingPoint

internal class FeedingPointRepositoryImpl(
    private val authAPI: AuthAPI,
    private val favouriteApi: FavouriteApi,
    private val feedingPointApi: FeedingPointApi
) : FeedingPointRepository {

    private val feedingPoints: Flow<List<DomainFeedingPoint>> by lazy { fetchFeedingPoints() }

    private fun fetchFeedingPoints(): Flow<List<DomainFeedingPoint>> {
        return feedingPointApi.getAllFeedingPoints()
            .combine(favouriteApi.getFavouriteList(authAPI.currentUserId)) { dataFeedingPointsList, favouriteList ->
                dataFeedingPointsList.map { dataFeedingPoint ->
                    dataFeedingPoint.toDomainFeedingPoint(
                        isFavourite = favouriteList.any { it.feedingPointId == dataFeedingPoint.id }
                    )
                }
            }
    }

    override fun getAllFeedingPoints(): Flow<List<DomainFeedingPoint>> = feedingPoints

    override fun getCats(): Flow<List<DomainFeedingPoint>> {
        return feedingPoints.map { feedingPointsList ->
            feedingPointsList.filter { feedingPoint -> feedingPoint.animalType == AnimalType.Cats }
        }
    }

    override fun getDogs(): Flow<List<DomainFeedingPoint>> {
        return feedingPoints.map { feedingPointsList ->
            feedingPointsList.filter { feedingPoint -> feedingPoint.animalType == AnimalType.Dogs }
        }
    }

    override fun getFavourites(): Flow<List<DomainFeedingPoint>> {
        return feedingPoints.map { feedingPointsList ->
            feedingPointsList.filter { feedingPoint -> feedingPoint.isFavourite }
        }
    }

    override fun getFeedingPoint(id: String): Flow<DomainFeedingPoint?> {
        return feedingPoints.map { feedingPointsList ->
            feedingPointsList.find { feedingPoint -> feedingPoint.id == id }
        }
    }

    // TODO: Replace with actual feeding flow
    override fun saveUserAsCurrentFeeder(
        user: Profile,
        feedingPointId: String
    ): Flow<Boolean> = flow { emit(true) }
}
