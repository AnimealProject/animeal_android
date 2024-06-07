package com.epmedu.animeal.feeding.data.repository

import OnCreateFeedingExtSubscription
import OnCreateFeedingHistoryExtSubscription
import OnUpdateFeedingExtSubscription
import SearchFeedingHistoriesQuery
import SearchFeedingsQuery
import com.amplifyframework.core.model.temporal.Temporal
import com.amplifyframework.datastore.generated.model.FeedingStatus
import com.apollographql.apollo.api.Operation.Data
import com.epmedu.animeal.api.feeding.FeedingActionApi
import com.epmedu.animeal.api.feeding.FeedingApi
import com.epmedu.animeal.api.feeding.FeedingFilters
import com.epmedu.animeal.api.feeding.FeedingHistoryApi
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
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import type.FeedingStatus.rejected
import com.epmedu.animeal.feeding.domain.model.FeedingStatus as DomainFeedingStatus

@Suppress("LongParameterList")
internal class FeedingRepositoryImpl(
    private val dispatchers: Dispatchers,
    private val authApi: AuthAPI,
    private val feedingApi: FeedingApi,
    private val feedingHistoryApi: FeedingHistoryApi,
    private val feedingActionApi: FeedingActionApi,
    private val feedingPointApi: FeedingPointApi,
    private val storageApi: StorageApi,
    private val favouriteRepository: FavouriteRepository,
    private val usersRepository: UsersRepository
) : FeedingRepository {

    private val _domainFeedState = MutableSharedFlow<DomainFeedState>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    private val feedingsFlow = MutableSharedFlow<List<Feeding>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private var cachedFeedingsMap = mutableMapOf<String, Feeding>()

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

    override fun getAllFeedings(shouldFetch: Boolean): Flow<List<Feeding>> {
        return when {
            shouldFetch -> {
                merge(
                    fetchAllFeedings()
                        .onEach { feedings ->
                            cachedFeedingsMap = feedings.associateBy { it.id }.toMutableMap()
                        },
                    merge(
                        subscribeToAllFeedings(),
                        subscribeToAllFeedingHistories()
                    ).map {
                        cachedFeedingsMap.values.toList()
                    }
                ).onEach { feedings ->
                    feedingsFlow.emit(feedings)
                }
            }

            else -> {
                feedingsFlow.asSharedFlow()
            }
        }
    }

    private fun fetchAllFeedings(): Flow<List<Feeding>> {
        return flow {
            val feedingHistories =
                feedingHistoryApi.getAllFeedingHistories().data?.searchFeedingHistories()
                    ?.items()
            val feedings = feedingApi.getAllFeedings().data?.searchFeedings()?.items()

            emit(FeedingsContainer(feedings, feedingHistories))
        }
            .mergeToFeedings()
    }

    private fun subscribeToAllFeedingHistories(): Flow<Any?> {
        return feedingHistoryApi.subscribeToFeedingHistoriesCreation()
            .addCreatedFeedingHistoryToFeedingsMap()
    }

    private fun subscribeToAllFeedings(): Flow<Any?> {
        return merge(
            feedingApi.subscribeToFeedingsCreation().addCreatedFeedingToFeedingsMap(),
            feedingApi.subscribeToFeedingsUpdates().updateFeedingInFeedingsMap(),
            subscribeToFeedingsDeletion()
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun <RawData : Data, RawFeeding> Flow<RawData>.updateFeedingsMap(
        getRawFeeding: RawData.() -> RawFeeding?,
        getFeedingId: RawFeeding.() -> String,
        getUserId: RawFeeding.() -> String,
        toFeeding: suspend RawFeeding.(User?) -> Feeding?
    ): Flow<Any?> {
        return flatMapLatest { data ->
            data.getRawFeeding()?.let { rawFeeding ->
                usersRepository.getUserById(rawFeeding.getUserId()).map { user ->
                    rawFeeding.toFeeding(user)?.let { feeding ->
                        cachedFeedingsMap[rawFeeding.getFeedingId()] = feeding
                    }
                }
            } ?: emptyFlow()
        }
    }

    override fun getAssignedFeedings(shouldFetch: Boolean): Flow<List<Feeding>> {
        return when {
            shouldFetch -> {
                merge(
                    fetchAssignedFeedings()
                        .onEach { feedings ->
                            cachedFeedingsMap = feedings.associateBy { it.id }.toMutableMap()
                        },
                    merge(
                        subscribeToAssignedFeedings(),
                        subscribeToAssignedFeedingHistories()
                    ).map {
                        cachedFeedingsMap.values.toList()
                    }
                ).onEach { feedings ->
                    feedingsFlow.emit(feedings)
                }
            }

            else -> {
                feedingsFlow.asSharedFlow()
            }
        }
    }

    private fun fetchAssignedFeedings(): Flow<List<Feeding>> {
        return flow {
            val currentUserId = authApi.getCurrentUserId()
            val feedingHistories = feedingHistoryApi.getFeedingHistoriesBy(
                assignedModeratorId = currentUserId,
                createdAt = FeedingFilters.feedingsCreatedAtFilterInput
            ).data?.searchFeedingHistories()?.items()
            val feedings = feedingApi.getFeedingsBy(
                assignedModeratorId = currentUserId,
                createdAt = FeedingFilters.feedingsCreatedAtFilterInput
            ).data?.searchFeedings()?.items()

            emit(FeedingsContainer(feedings, feedingHistories))
        }
            .mergeToFeedings()
    }

    private fun subscribeToAssignedFeedingHistories(): Flow<Any?> {
        return feedingHistoryApi.subscribeToFeedingHistoriesCreation()
            .filter { data ->
                data.onCreateFeedingHistoryExt()?.assignedModerators().containsCurrentUserId()
            }
            .addCreatedFeedingHistoryToFeedingsMap()
    }

    private fun subscribeToAssignedFeedings(): Flow<Any?> {
        return merge(
            feedingApi.subscribeToFeedingsCreation()
                .filter { data ->
                    data.onCreateFeedingExt()?.assignedModerators().containsCurrentUserId()
                }
                .addCreatedFeedingToFeedingsMap(),
            feedingApi.subscribeToFeedingsUpdates()
                .filter { data ->
                    data.onUpdateFeedingExt()?.assignedModerators().containsCurrentUserId()
                }
                .updateFeedingInFeedingsMap(),
            subscribeToFeedingsDeletion()
        )
    }

    private fun subscribeToFeedingsDeletion() = feedingApi.subscribeToFeedingsDeletion().map { data ->
        cachedFeedingsMap.remove(data.onDeleteFeedingExt()?.id())
    }

    private suspend fun List<String>?.containsCurrentUserId() =
        this?.contains(authApi.getCurrentUserId()) == true

    private fun Flow<OnCreateFeedingHistoryExtSubscription.Data>.addCreatedFeedingHistoryToFeedingsMap(): Flow<Any?> {
        return updateFeedingsMap(
            getRawFeeding = { onCreateFeedingHistoryExt() },
            getFeedingId = { id() },
            getUserId = { userId() },
            toFeeding = { user -> toFeeding(::getImageFromName, user) }
        )
    }

    private fun Flow<OnCreateFeedingExtSubscription.Data>.addCreatedFeedingToFeedingsMap(): Flow<Any?> {
        return updateFeedingsMap(
            getRawFeeding = { onCreateFeedingExt() },
            getFeedingId = { id() },
            getUserId = { userId() },
            toFeeding = { user -> toFeeding(::getImageFromName, user) }
        )
    }

    private fun Flow<OnUpdateFeedingExtSubscription.Data>.updateFeedingInFeedingsMap(): Flow<Any?> {
        return updateFeedingsMap(
            getRawFeeding = { onUpdateFeedingExt() },
            getFeedingId = { id() },
            getUserId = { userId() },
            toFeeding = { user -> toFeeding(::getImageFromName, user) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun Flow<FeedingsContainer>.mergeToFeedings(): Flow<List<Feeding>> {
        return flatMapLatest { feedingsContainer ->
            val userIds = mutableSetOf<String>()

            feedingsContainer.feedings?.forEach { item ->
                item?.let { userIds.add(item.userId()) }
            }
            feedingsContainer.feedingHistories?.forEach { item ->
                item?.let {
                    userIds.add(item.userId())
                    item.moderatedBy()?.let { id -> userIds.add(id) }
                }
            }

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

        return container.feedingHistories.orEmpty().mapNotNull { feeding ->
            when {
                feeding.isCanceled() -> {
                    null
                }

                else -> {
                    feeding?.toFeeding(
                        feeder = usersMap[feeding.userId()],
                        reviewedBy = usersMap[feeding.moderatedBy()],
                        rejectionReason = feeding.reason(),
                        getImageFrom = ::getImageFromName
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
                        getImageFrom = ::getImageFromName
                    )
                }
            }
        }
    }

    private fun SearchFeedingsQuery.Item?.isCanceled() =
        this?.status() == rejected && images().isEmpty()

    private fun SearchFeedingHistoriesQuery.Item?.isCanceled() =
        this?.status() == rejected && images().isEmpty()

    private suspend fun getImageFromName(fileName: String): NetworkFile {
        return NetworkFile(
            name = fileName,
            url = storageApi.getUrlFrom(fileName)
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getFeedingHistoriesBy(
        feedingPointId: String,
        status: DomainFeedingStatus?
    ): Flow<List<FeedingHistory>> {
        return flow {
            emit(
                feedingHistoryApi.getFeedingHistoriesBy(
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
        return feedingActionApi.startFeeding(feedingPointId).toActionResult(feedingPointId)
    }

    override suspend fun cancelFeeding(feedingPointId: String): ActionResult<Unit> {
        return feedingActionApi.cancelFeeding(feedingPointId).toActionResult(feedingPointId)
    }

    override suspend fun rejectFeeding(feedingPointId: String, reason: String): ActionResult<Unit> {
        return feedingActionApi.rejectFeeding(feedingPointId, reason).toActionResult(feedingPointId)
    }

    override suspend fun finishFeeding(
        feedingPointId: String,
        images: List<String>
    ): ActionResult<Unit> {
        return feedingActionApi.finishFeeding(feedingPointId, images).toActionResult(feedingPointId)
    }

    override suspend fun approveFeeding(feedingPointId: String): ActionResult<Unit> {
        return feedingActionApi.approveFeeding(feedingPointId).toActionResult(feedingPointId)
    }

    override suspend fun updateFeedStateFlow(newFeedState: DomainFeedState) {
        _domainFeedState.emit(newFeedState)
    }

    private data class FeedingsContainer(
        val feedings: List<SearchFeedingsQuery.Item?>?,
        val feedingHistories: List<SearchFeedingHistoriesQuery.Item?>?
    )
}