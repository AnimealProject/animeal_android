package com.epmedu.animeal.feedings.presentation.viewmodel.handlers

import com.epmedu.animeal.common.component.BuildConfigProvider
import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.feedings.domain.usecase.GetIsNewFeedingPendingUseCase
import com.epmedu.animeal.feedings.presentation.model.FeedingsButtonState
import com.epmedu.animeal.feedings.presentation.model.FeedingsButtonState.Pulsating
import com.epmedu.animeal.feedings.presentation.model.FeedingsButtonState.Static
import com.epmedu.animeal.networkuser.domain.usecase.GetCurrentUserGroupUseCase
import com.epmedu.animeal.users.domain.model.UserGroup
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FeedingsButtonHandlerImpl @Inject constructor(
    private val buildConfigProvider: BuildConfigProvider,
    private val getUserGroupUseCase: GetCurrentUserGroupUseCase,
    private val getIsNewFeedingPendingUseCase: GetIsNewFeedingPendingUseCase
) : FeedingsButtonHandler {

    override fun getFeedingsButtonState(): Flow<FeedingsButtonState> {
        return when {
            buildConfigProvider.isProdFlavor -> {
                flowOf(FeedingsButtonState.Hidden)
            }
            else -> {
                combine(
                    getIsNewFeedingPendingUseCase(),
                    flow { emit(getUserGroupUseCase()) }
                ) { isNewFeedingPending, userGroupResult ->
                    when {
                        userGroupResult.isEligibleForFeedingsButton() -> {
                            if (isNewFeedingPending) Pulsating else Static
                        }

                        else -> {
                            FeedingsButtonState.Hidden
                        }
                    }
                }
            }
        }
    }

    private fun ActionResult<UserGroup>.isEligibleForFeedingsButton(): Boolean {
        return this is ActionResult.Success && (result == UserGroup.Administrator || result == UserGroup.Moderator)
    }
}