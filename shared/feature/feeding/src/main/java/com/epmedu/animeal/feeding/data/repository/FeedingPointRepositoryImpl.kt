package com.epmedu.animeal.feeding.data.repository

import com.amplifyframework.datastore.generated.model.CategoryTag.cats
import com.amplifyframework.datastore.generated.model.Feeding
import com.amplifyframework.datastore.generated.model.FeedingPointStatus.fed
import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.common.constants.DefaultConstants.EMPTY_STRING
import com.epmedu.animeal.feeding.data.api.favourite.FavouriteApi
import com.epmedu.animeal.feeding.data.api.feeding.FeedingPointApi
import com.epmedu.animeal.feeding.domain.model.Feeder
import com.epmedu.animeal.feeding.domain.model.enum.AnimalState
import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import com.epmedu.animeal.feeding.presentation.model.MapLocation
import com.epmedu.animeal.foundation.switch.AnimalType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import com.amplifyframework.datastore.generated.model.FeedingPoint as DataFeedingPoint
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

    private fun DataFeedingPoint.toDomainFeedingPoint(isFavourite: Boolean) = DomainFeedingPoint(
        id = id ?: EMPTY_STRING,
        title = name ?: EMPTY_STRING,
        description = description ?: EMPTY_STRING,
        animalStatus = when (status) {
            fed -> AnimalState.GREEN
            else -> AnimalState.RED
        },
        animalType = when (category?.tag) {
            cats -> AnimalType.Cats
            else -> AnimalType.Dogs
        },
        isFavourite = isFavourite,
        lastFeeder = feedings?.lastOrNull()?.toFeeder() ?: Feeder(),
        location = MapLocation(
            latitude = location?.lat ?: 0.0,
            longitude = location?.lon ?: 0.0
        )
    )

    private fun Feeding.toFeeder() = Feeder(
        id = userId ?: EMPTY_STRING,
        name = updatedBy ?: EMPTY_STRING,
        time = updatedAt?.format() ?: EMPTY_STRING
    )
}
