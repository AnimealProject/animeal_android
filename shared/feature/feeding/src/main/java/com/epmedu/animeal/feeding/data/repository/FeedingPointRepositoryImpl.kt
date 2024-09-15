package com.epmedu.animeal.feeding.data.repository

import com.epmedu.animeal.api.feeding.FeedingPointApi
import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.feeding.data.mapper.toDomainFeedingPoint
import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.feeding.domain.repository.FavouriteRepository
import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import com.epmedu.animeal.networkstorage.data.api.StorageApi
import com.epmedu.animeal.networkstorage.domain.NetworkFile
import com.epmedu.animeal.networkuser.domain.repository.NetworkRepository
import com.epmedu.animeal.users.domain.UsersRepository
import com.epmedu.animeal.users.domain.model.User
import com.epmedu.animeal.users.domain.model.UserGroup.Administrator
import com.epmedu.animeal.users.domain.model.UserGroup.Moderator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import com.epmedu.animeal.feeding.domain.model.FeedingPoint as DomainFeedingPoint

internal class FeedingPointRepositoryImpl(
    private val dispatchers: Dispatchers,
    private val favouriteRepository: FavouriteRepository,
    private val usersRepository: UsersRepository,
    private val networkRepository: NetworkRepository,
    private val feedingPointApi: FeedingPointApi,
    private val storageApi: StorageApi
) : FeedingPointRepository {

    private val feedingPointsFlow = MutableStateFlow(emptyList<DomainFeedingPoint>())

    private var cachedFavoriteIDs = emptyList<String>()
    private var cachedModeratorsMap: Map<String, User>? = emptyMap()
    private val cachedFeedingPointsMap = mutableMapOf<String, FeedingPoint>()

    override fun getAllFeedingPoints(shouldFetch: Boolean): Flow<List<DomainFeedingPoint>> {
        val flow = when {
            shouldFetch -> merge(feedingPointsFlow, fetchFeedingPoints())
            else -> feedingPointsFlow
        }
        return flow
            .filterNot { it.isEmpty() }
            .distinctUntilChanged()
            .flowOn(dispatchers.IO)
    }

    override fun getFeedingPointsBy(
        shouldFetch: Boolean,
        predicate: (DomainFeedingPoint) -> Boolean
    ): Flow<List<DomainFeedingPoint>> {
        return getAllFeedingPoints(shouldFetch).map { feedingPoints ->
            feedingPoints.filter(predicate)
        }
    }

    override fun getFeedingPointById(id: String): FeedingPoint? {
        return cachedFeedingPointsMap[id]
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun fetchFeedingPoints(): Flow<List<DomainFeedingPoint>> {
        return combine(
            feedingPointApi.getAllFeedingPoints(),
            favouriteRepository.getFavouriteFeedingPointIds(),
            fetchModeratorsMap()
        ) { feedingPoints, favoriteFeedingPointIDs, moderatorsMap ->
            cachedFavoriteIDs = favoriteFeedingPointIDs
            cachedModeratorsMap = moderatorsMap
            cachedFeedingPointsMap.clear()
            cachedFeedingPointsMap.putAll(
                feedingPoints.associate { feedingPoint ->
                    feedingPoint.id to feedingPoint.toDomainFeedingPoint(
                        getImageFrom = ::getImageFromName,
                        moderatorsMap = moderatorsMap,
                        isFavourite = favoriteFeedingPointIDs.any { it == feedingPoint.id }
                    )
                }
            )
        }.flatMapLatest {
            merge(
                subscribeToFeedingPointsCreationAndUpdates(),
                subscribeToFeedingPointsDeletion()
            ).onStart {
                emit(cachedFeedingPointsMap.values.toList())
            }
        }.onEach {
            feedingPointsFlow.value = it
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun fetchModeratorsMap(): Flow<Map<String, User>?> {
        return flow {
            with(networkRepository.getUserGroup()) {
                emit(
                    this is ActionResult.Success && (result == Moderator || result == Administrator)
                )
            }
        }.flatMapLatest { isEligibleForAssignedModerators ->
            if (isEligibleForAssignedModerators) {
                combine(
                    usersRepository.getUsersInGroup(Moderator),
                    usersRepository.getUsersInGroup(Administrator)
                ) { moderators, administrators ->
                    (moderators + administrators).associateBy { it.id }
                }
            } else {
                flowOf(null)
            }
        }
    }

    private suspend fun getImageFromName(fileName: String): NetworkFile {
        return NetworkFile(
            name = fileName,
            url = storageApi.getUrlFrom(fileName)
        )
    }

    private fun subscribeToFeedingPointsCreationAndUpdates() = merge(
        feedingPointApi.subscribeToFeedingPointsCreation(),
        feedingPointApi.subscribeToFeedingPointsUpdates()
    ).map {
        cachedFeedingPointsMap[it.id] = it.toDomainFeedingPoint(
            getImageFrom = ::getImageFromName,
            moderatorsMap = cachedModeratorsMap,
            isFavourite = cachedFavoriteIDs.any { id -> id == it.id }
        )
        cachedFeedingPointsMap.values.toList()
    }

    private fun subscribeToFeedingPointsDeletion(): Flow<List<DomainFeedingPoint>> {
        return feedingPointApi.subscribeToFeedingPointsDeletion().map { deletedFeedingPoint ->
            cachedFeedingPointsMap.remove(deletedFeedingPoint.id)
            cachedFeedingPointsMap.values.toList()
        }
    }
}
