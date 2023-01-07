package com.epmedu.animeal.feeding.data.repository

import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.common.constants.DefaultConstants.EMPTY_STRING
import com.epmedu.animeal.extensions.formatDateToString
import com.epmedu.animeal.feeding.data.api.favourite.FavouriteApi
import com.epmedu.animeal.feeding.data.api.feeding.FeedingPointApi
import com.epmedu.animeal.feeding.data.model.CategoryTagData.Cats
import com.epmedu.animeal.feeding.data.model.FeedingData
import com.epmedu.animeal.feeding.data.model.FeedingPointData
import com.epmedu.animeal.feeding.data.model.FeedingPointDataStatus.Fed
import com.epmedu.animeal.feeding.domain.model.Feeder
import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.feeding.domain.model.enum.AnimalState
import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import com.epmedu.animeal.feeding.presentation.model.MapLocation
import com.epmedu.animeal.foundation.switch.AnimalType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf

internal class FeedingPointRepositoryImpl(
    private val authAPI: AuthAPI,
    private val favouriteApi: FavouriteApi,
    private val feedingPointApi: FeedingPointApi
) : FeedingPointRepository {

    private var feedingPoints: List<FeedingPoint> = emptyList()

    override fun getAllFeedingPoints(): Flow<List<FeedingPoint>> {
        return feedingPointApi.getAllFeedingPoints()
            .combine(favouriteApi.getFavouriteList(authAPI.currentUserId)) { feedingPointDataList, favouriteDataList ->
                feedingPointDataList.map { feedingPointData ->
                    feedingPointData.toFeedingPoint(
                        isFavourite = favouriteDataList.any { it.feedingPointId == feedingPointData.id }
                    )
                }.also {
                    feedingPoints = it
                }
            }
    }

    override fun getCats(): Flow<List<FeedingPoint>> = flowOf(
        feedingPoints.filter { feedingPoint -> feedingPoint.animalType == AnimalType.Cats }
    )

    override fun getDogs(): Flow<List<FeedingPoint>> = flowOf(
        feedingPoints.filter { feedingPoint -> feedingPoint.animalType == AnimalType.Dogs }
    )

    override fun getFavourites(): Flow<List<FeedingPoint>> = flowOf(
        feedingPoints.filter { feedingPoint -> feedingPoint.isFavourite }
    )

    override fun getFeedingPoint(id: String): Flow<FeedingPoint?> = flowOf(
        feedingPoints.find { feedingPoint -> feedingPoint.id == id }
    )

    private fun FeedingPointData.toFeedingPoint(isFavourite: Boolean) = FeedingPoint(
        id = id ?: EMPTY_STRING,
        title = name ?: EMPTY_STRING,
        description = description ?: EMPTY_STRING,
        animalStatus = when (status) {
            Fed -> AnimalState.GREEN
            else -> AnimalState.RED
        },
        animalType = when (categoryTag) {
            Cats -> AnimalType.Cats
            else -> AnimalType.Dogs
        },
        isFavourite = isFavourite,
        lastFeeder = feedings?.lastOrNull()?.toFeeder() ?: Feeder(),
        location = MapLocation(
            latitude = location?.lat ?: 0.0,
            longitude = location?.lon ?: 0.0
        )
    )

    private fun FeedingData.toFeeder() = Feeder(
        id = userId ?: EMPTY_STRING,
        name = updatedBy ?: EMPTY_STRING,
        time = formatDateToString(updatedAt)
    )
}
