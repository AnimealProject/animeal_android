package com.epmedu.animeal.feeding.data.repository

import com.amplifyframework.core.model.temporal.Temporal
import com.epmedu.animeal.api.feeding.FeedingApi
import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.feeding.data.mapper.toActionResult
import com.epmedu.animeal.feeding.data.mapper.toDomain
import com.epmedu.animeal.feeding.domain.model.FeedingHistory
import com.epmedu.animeal.feeding.domain.model.UserFeeding
import com.epmedu.animeal.feeding.domain.repository.FavouriteRepository
import com.epmedu.animeal.feeding.domain.repository.FeedingRepository
import com.epmedu.animeal.users.domain.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

internal class FeedingRepositoryImpl(
    private val dispatchers: Dispatchers,
    private val authApi: AuthAPI,
    private val feedingApi: FeedingApi,
    private val favouriteRepository: FavouriteRepository,
    private val usersRepository: UsersRepository
) : FeedingRepository {

    override suspend fun getUserFeedings(): List<UserFeeding> {
        return combine(
            feedingApi.getUserFeedings(userId = authApi.getCurrentUserId()),
            favouriteRepository.getFavouriteFeedingPointIds(shouldFetch = false)
        ) { feedings, favouriteIds ->
            feedings.map { feeding ->
                feeding.toDomain(isFavourite = favouriteIds.any { it == feeding.feedingPoint.id })
            }
        }
            .flowOn(dispatchers.IO)
            .first()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getFeedingHistories(feedingPointId: String): Flow<List<FeedingHistory>> {
        return flow {
            emit(
                feedingApi.getApprovedFeedingHistories(feedingPointId).data?.searchFeedingHistories()
                    ?.items()
            )
        }.flatMapLatest { feedingsHistories ->
            if (feedingsHistories.isNullOrEmpty()) {
                flowOf(emptyList())
            } else {
                val userIds = feedingsHistories.map { it.userId() }.toSet()

                usersRepository.getUsersById(userIds).map { users ->
                    val usersMap = users.associateBy { it.id }

                    feedingsHistories.map { feeding ->
                        val user = usersMap[feeding.userId()]

                        FeedingHistory(
                            id = feeding.userId(),
                            name = user?.name.orEmpty(),
                            surname = user?.surname.orEmpty(),
                            date = Temporal.DateTime(feeding.createdAt()).toDate()
                        )
                    }
                }
            }
        }.flowOn(dispatchers.IO)
    }

    override suspend fun startFeeding(feedingPointId: String): ActionResult {
        return feedingApi.startFeeding(feedingPointId).toActionResult(feedingPointId)
    }

    override suspend fun cancelFeeding(feedingPointId: String): ActionResult {
        return feedingApi.cancelFeeding(feedingPointId).toActionResult(feedingPointId)
    }

    override suspend fun rejectFeeding(feedingPointId: String, reason: String): ActionResult {
        return feedingApi.rejectFeeding(feedingPointId, reason).toActionResult(feedingPointId)
    }

    override suspend fun finishFeeding(
        feedingPointId: String,
        images: List<String>
    ): ActionResult {
        return feedingApi.finishFeeding(feedingPointId, images).toActionResult(feedingPointId)
    }
}