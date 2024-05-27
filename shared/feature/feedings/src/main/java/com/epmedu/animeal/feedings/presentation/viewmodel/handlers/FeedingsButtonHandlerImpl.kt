package com.epmedu.animeal.feedings.presentation.viewmodel.handlers

import com.epmedu.animeal.common.component.BuildConfigProvider
import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.feedings.domain.usecase.GetHasNewPendingFeedingUseCase
import com.epmedu.animeal.feedings.presentation.model.FeedingsButtonState
import com.epmedu.animeal.feedings.presentation.model.FeedingsButtonState.Hidden
import com.epmedu.animeal.feedings.presentation.model.FeedingsButtonState.Pulsating
import com.epmedu.animeal.feedings.presentation.model.FeedingsButtonState.Static
import com.epmedu.animeal.networkuser.domain.usecase.GetCurrentUserGroupUseCase
import com.epmedu.animeal.users.domain.model.UserGroup
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FeedingsButtonHandlerImpl @Inject constructor(
    private val buildConfigProvider: BuildConfigProvider,
    private val getUserGroupUseCase: GetCurrentUserGroupUseCase,
    private val getHasNewPendingFeedingUseCase: GetHasNewPendingFeedingUseCase
) : FeedingsButtonHandler {

    override fun getFeedingsButtonState(shouldFetchFeedings: Boolean): Flow<FeedingsButtonState> {
        return flow {
            when {
                buildConfigProvider.isProdFlavor ||
                    getUserGroupUseCase().isEligibleForFeedingsButton().not() -> {
                    emit(Hidden)
                }

                else -> {
                    emit(Static)

                    getHasNewPendingFeedingUseCase(
                        shouldFetchFeedings
                    ).collect { hasNewPendingFeeding ->
                        emit(
                            if (hasNewPendingFeeding) Pulsating else Static
                        )
                    }
                }
            }
        }
    }
}

private fun ActionResult<UserGroup>.isEligibleForFeedingsButton(): Boolean {
    return this is ActionResult.Success && (result == UserGroup.Administrator || result == UserGroup.Moderator)
}