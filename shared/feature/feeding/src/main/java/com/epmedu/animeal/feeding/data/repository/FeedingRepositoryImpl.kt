package com.epmedu.animeal.feeding.data.repository

import SearchFeedingHistoriesQuery
import SearchFeedingsQuery
import com.amplifyframework.core.model.temporal.Temporal
import com.amplifyframework.datastore.generated.model.FeedingStatus
import com.epmedu.animeal.api.feeding.FeedingApi
import com.epmedu.animeal.api.feeding.FeedingPointApi
import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.feeding.data.mapper.toActionResult
import com.epmedu.animeal.feeding.data.mapper.toData
import com.epmedu.animeal.feeding.data.mapper.toDomain
import com.epmedu.animeal.feeding.data.mapper.toFeeding
import com.epmedu.animeal.feeding.domain.model.DomainFeedState
import com.epmedu.animeal.feeding.domain.model.Feeding
import com.epmedu.animeal.feeding.domain.model.FeedingHistory
import com.epmedu.animeal.feeding.domain.model.FeedingInProgress
import com.epmedu.animeal.feeding.domain.model.UserFeeding
import com.epmedu.animeal.feeding.domain.repository.FavouriteRepository
import com.epmedu.animeal.feeding.domain.repository.FeedingRepository
import com.epmedu.animeal.networkstorage.data.api.StorageApi
import com.epmedu.animeal.networkstorage.domain.NetworkFile
import com.epmedu.animeal.users.domain.UsersRepository
import com.epmedu.animeal.users.domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import type.FeedingStatus.rejected
import com.epmedu.animeal.feeding.domain.model.FeedingStatus as DomainFeedingStatus

internal class FeedingRepositoryImpl(
    private val dispatchers: Dispatchers,
    private val authApi: AuthAPI,
    private val feedingApi: FeedingApi,
    private val feedingPointApi: FeedingPointApi,
    private val storageApi: StorageApi,
    private val favouriteRepository: FavouriteRepository,
    private val usersRepository: UsersRepository
) : FeedingRepository {

    private val _domainFeedState = MutableSharedFlow<DomainFeedState>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override fun getFeedStateFlow(): Flow<DomainFeedState> {
        return _domainFeedState.asSharedFlow()
    }

    override suspend fun getUserFeedings(): List<UserFeeding> {
        return combine(
            feedingApi.getUserFeedings(userId = authApi.getCurrentUserId()),
            feedingPointApi.getAllFeedingPoints(),
            favouriteRepository.getFavouriteFeedingPointIds(shouldFetch = false)
        ) { feedings, feedingPoints, favouriteIds ->
            feedings.filter { it.status == FeedingStatus.inProgress }
                .map { feeding ->
                    feeding.toDomain(
                        getImageFromName = { fileName ->
                            NetworkFile(
                                name = fileName,
                                url = storageApi.getUrlFrom(fileName = fileName)
                            )
                        },
                        isFavourite = favouriteIds.any { it == feeding.feedingPointFeedingsId },
                        feedingPoint = feedingPoints.first { it.id == feeding.feedingPointFeedingsId }
                    )
                }
        }
            .flowOn(dispatchers.IO)
            .first()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getFeedingInProgress(feedingPointId: String): Flow<FeedingInProgress?> {
        return flow {
            emit(
                feedingApi.getFeedingsBy(feedingPointId).data?.searchFeedings()?.items()
            )
        }.flatMapLatest { feedings ->
            if (feedings.isNullOrEmpty()) {
                flowOf(null)
            } else {
                val feeding = feedings.first()

                usersRepository.getUserById(feeding.userId()).map { user ->
                    FeedingInProgress(
                        id = feeding.userId(),
                        name = user?.name.orEmpty(),
                        surname = user?.surname.orEmpty(),
                        startDate = Temporal.DateTime(feeding.createdAt()).toDate()
                    )
                }
            }
        }.flowOn(dispatchers.IO)
    }

    override fun getAllFeedings(): Flow<List<Feeding>> {
        return flow {
            val feedingHistories =
                feedingApi.getAllFeedingHistories().data?.searchFeedingHistories()?.items()
            val feedings = feedingApi.getAllFeedings().data?.searchFeedings()?.items()

            emit(FeedingsContainer(feedings, feedingHistories))
        }
            .mergeToFeedings()
            .flowOn(dispatchers.IO)
    }

    override fun getAssignedFeedings(): Flow<List<Feeding>> {
        return flow {
            val currentUserId = authApi.getCurrentUserId()
            val feedingHistories = feedingApi.getFeedingHistoriesBy(
                assignedModeratorId = currentUserId
            ).data?.searchFeedingHistories()?.items()
            val feedings = feedingApi.getFeedingsBy(
                assignedModeratorId = currentUserId
            ).data?.searchFeedings()?.items()

            emit(FeedingsContainer(feedings, feedingHistories))
        }
            .mergeToFeedings()
            .flowOn(dispatchers.IO)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun Flow<FeedingsContainer>.mergeToFeedings(): Flow<List<Feeding>> {
        return flatMapLatest { feedingsContainer ->
            val userIds = feedingsContainer.feedingHistories.orEmpty().mapNotNull { it?.userId() }
                .plus(feedingsContainer.feedings.orEmpty().mapNotNull { it?.userId() })
                .toSet()

            usersRepository.getUsersById(userIds).map { users ->
                mergeToFeedings(feedingsContainer, users)
            }
        }
    }

    private suspend fun mergeToFeedings(
        container: FeedingsContainer,
        users: List<User>
    ): List<Feeding> {
        val usersMap = users.associateBy { it.id }
        val getImageFrom: suspend (String) -> NetworkFile = { name ->
            NetworkFile(
                name = name,
                url = storageApi.getUrlFrom(name)
            )
        }

        return container.feedingHistories.orEmpty().mapNotNull { feeding ->
            when {
                feeding.isCanceled() -> {
                    null
                }

                else -> {
                    feeding?.toFeeding(
                        feeder = usersMap[feeding.userId()],
                        getImageFrom = getImageFrom
                    )
                }
            }
        } + container.feedings.orEmpty().mapNotNull { feeding ->
            when {
                feeding.isCanceled() -> {
                    null
                }

                else -> {
                    feeding?.toFeeding(
                        feeder = usersMap[feeding.userId()],
                        getImageFrom = getImageFrom
                    )
                }
            }
        }
    }

    private fun SearchFeedingsQuery.Item?.isCanceled() = this?.status() == rejected && images().isEmpty()

    private fun SearchFeedingHistoriesQuery.Item?.isCanceled() = this?.status() == rejected && images().isEmpty()

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getFeedingHistoriesBy(
        feedingPointId: String,
        status: DomainFeedingStatus?
    ): Flow<List<FeedingHistory>> {
        return flow {
            emit(
                feedingApi.getFeedingHistoriesBy(
                    feedingPointId = feedingPointId,
                    status = status?.toData()
                ).data?.searchFeedingHistories()?.items()
            )
        }.flatMapLatest { feedingsHistories ->
            if (feedingsHistories.isNullOrEmpty()) {
                flowOf(emptyList())
            } else {
                val userIds = feedingsHistories.map { it.userId() }.toSet()

                usersRepository.getUsersById(userIds).map { users ->
                    val usersMap = users.associateBy { it.id }

                    feedingsHistories.mapNotNull { feeding ->
                        feeding.toDomain(feeder = usersMap[feeding.userId()])
                    }
                }
            }
        }
            .flowOn(dispatchers.IO)
    }

    override suspend fun startFeeding(feedingPointId: String): ActionResult<Unit> {
        return feedingApi.startFeeding(feedingPointId).toActionResult(feedingPointId)
    }

    override suspend fun cancelFeeding(feedingPointId: String): ActionResult<Unit> {
        return feedingApi.cancelFeeding(feedingPointId).toActionResult(feedingPointId)
    }

    override suspend fun rejectFeeding(feedingPointId: String, reason: String): ActionResult<Unit> {
        return feedingApi.rejectFeeding(feedingPointId, reason).toActionResult(feedingPointId)
    }

    override suspend fun finishFeeding(
        feedingPointId: String,
        images: List<String>
    ): ActionResult<Unit> {
        return feedingApi.finishFeeding(feedingPointId, images).toActionResult(feedingPointId)
    }

    override suspend fun updateFeedStateFlow(newFeedState: DomainFeedState) {
        _domainFeedState.emit(newFeedState)
    }

    private data class FeedingsContainer(
        val feedings: List<SearchFeedingsQuery.Item?>?,
        val feedingHistories: List<SearchFeedingHistoriesQuery.Item?>?
    )
}