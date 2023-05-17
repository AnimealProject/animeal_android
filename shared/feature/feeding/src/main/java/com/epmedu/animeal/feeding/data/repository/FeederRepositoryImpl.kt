package com.epmedu.animeal.feeding.data.repository

import com.epmedu.animeal.feeding.domain.model.Feeder
import com.epmedu.animeal.feeding.domain.repository.FeederRepository
import com.epmedu.animeal.feeding.domain.repository.FeedingRepository
import com.epmedu.animeal.users.domain.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

internal class FeederRepositoryImpl(
    private val dispatchers: Dispatchers,
    private val feedingRepository: FeedingRepository,
    private val usersRepository: UsersRepository
) : FeederRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getFeeders(feedingPointId: String): Flow<List<Feeder>> {
        return feedingRepository.getApprovedFeedingHistories(feedingPointId)
            .flatMapLatest { feedings ->
                if (feedings.isEmpty()) {
                    flowOf(emptyList())
                } else {
                    val feedingsUserIds = feedings.map { it.userId }.toSet()

                    usersRepository.getUsersById(feedingsUserIds).map { users ->
                        val usersMap = users.associateBy { it.id }

                        feedings.map { feeding ->
                            val user = usersMap[feeding.userId]

                            Feeder(
                                id = feeding.userId,
                                name = user?.name.orEmpty(),
                                surname = user?.surname.orEmpty(),
                                feedingDate = feeding.createdAt.toDate()
                            )
                        }
                    }
                }
            }.flowOn(dispatchers.IO)
    }
}