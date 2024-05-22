package com.epmedu.animeal.feedings.domain.usecase

import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.feeding.domain.model.Feeding
import com.epmedu.animeal.feeding.domain.repository.FeedingRepository
import com.epmedu.animeal.networkuser.domain.usecase.GetCurrentUserGroupUseCase
import com.epmedu.animeal.users.domain.model.UserGroup
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class GetAllFeedingsUseCase(
    private val getCurrentUserGroupUseCase: GetCurrentUserGroupUseCase,
    private val repository: FeedingRepository
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<List<Feeding>> {
        return flow {
            emit(getCurrentUserGroupUseCase())
        }.flatMapLatest { userGroupResult ->
            if (userGroupResult is ActionResult.Success) {
                when (userGroupResult.result) {
                    UserGroup.Administrator -> {
                        repository.getAllFeedings(shouldFetch = true).map { feedings ->
                            feedings.sortedByDescending { feeding -> feeding.date }
                        }
                    }

                    UserGroup.Moderator -> {
                        repository.getAssignedFeedings(shouldFetch = true).map { feedings ->
                            feedings.sortedByDescending { feeding -> feeding.date }
                        }
                    }

                    UserGroup.Volunteer -> {
                        flowOf(emptyList())
                    }
                }
            } else {
                flowOf(emptyList())
            }
        }
    }
}