package com.epmedu.animeal.tabs.more.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.common.presentation.viewmodel.delegate.ActionDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.feedings.presentation.model.FeedingsButtonState
import com.epmedu.animeal.feedings.presentation.viewmodel.handlers.FeedingsButtonHandler
import com.epmedu.animeal.networkuser.domain.usecase.DeleteNetworkUserUseCase
import com.epmedu.animeal.networkuser.domain.usecase.LogOutUseCase
import com.epmedu.animeal.profile.domain.ClearProfileUseCase
import com.epmedu.animeal.tabs.more.presentation.MoreScreenEvent
import com.epmedu.animeal.tabs.more.presentation.MoreScreenEvent.Delete
import com.epmedu.animeal.tabs.more.presentation.MoreScreenEvent.GoToOnboarding
import com.epmedu.animeal.tabs.more.presentation.MoreScreenEvent.Logout
import com.epmedu.animeal.tabs.more.presentation.MoreScreenEvent.ToastShown
import com.epmedu.animeal.tabs.more.presentation.model.MoreOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MoreViewModel @Inject constructor(
    private val actionDelegate: ActionDelegate,
    private val feedingsButtonHandler: FeedingsButtonHandler,
    savedStateHandle: SavedStateHandle,
    private val logOutUseCase: LogOutUseCase,
    private val clearProfileUseCase: ClearProfileUseCase,
    private val deleteNetworkUserUseCase: DeleteNetworkUserUseCase,
) : ViewModel(),
    ActionDelegate by actionDelegate,
    StateDelegate<MoreState> by DefaultStateDelegate(MoreState()) {

    val parentRoute: String? = savedStateHandle["parentRoute"]

    init {
        viewModelScope.launch {
            if (parentRoute != null) {
                val option = when (parentRoute) {
                    MoreOption.Account.route.name -> MoreOption.Account
                    else -> MoreOption.Account
                }

                updateState {
                    copy(
                        generalOptions = option.children
                            ?.toImmutableList()
                            ?: persistentListOf(),
                        moderationOptions = persistentListOf(),
                        titleResource = option.stringResource
                    )
                }
            } else {
                val options = persistentListOf(
                    MoreOption.Account,
                    MoreOption.Linebreak,

                    MoreOption.Terms,
                    MoreOption.Policy,
                    MoreOption.About,
                    MoreOption.Linebreak,

                    MoreOption.FAQ,
                    MoreOption.Donate,
                )

                feedingsButtonHandler.getFeedingsButtonState(
                    shouldFetchFeedings = false
                ).collect { buttonState ->

                    val moderatorOptions = mutableSetOf<MoreOption>()
                    if (buttonState != FeedingsButtonState.Hidden) {
                        moderatorOptions.add(
                            MoreOption.Feedings(
                                isIndicatorEnabled = buttonState == FeedingsButtonState.Pulsating
                            )
                        )
                    }
                    updateState {
                        copy(
                            generalOptions = options,
                            moderationOptions = moderatorOptions.toImmutableList(),
                            titleResource = null
                        )
                    }
                }
            }
        }
    }

    fun handleScreenEvent(event: MoreScreenEvent) {
        when (event) {
            Logout -> logout()
            ToastShown -> clearToast()
            GoToOnboarding -> updateState { copy(isNavigatingToOnboarding = false) }
            Delete -> delete()
        }
    }

    private fun logout() {
        performAction(logOutUseCase::invoke, MoreToast.SuccessfulLogout)
    }

    private fun clearToast() {
        updateState { copy(toastToShow = null) }
    }

    private fun delete() {
        performAction(deleteNetworkUserUseCase::invoke, MoreToast.SuccessfulDelete)
    }

    private fun performAction(
        action: suspend () -> ActionResult<Unit>,
        toastToShowOnSuccess: MoreToast
    ) {
        viewModelScope.launch {
            performAction(
                action = action,
                onSuccess = {
                    clearProfileUseCase()
                    updateState {
                        copy(
                            toastToShow = toastToShowOnSuccess,
                            isNavigatingToOnboarding = true
                        )
                    }
                }
            )
        }
    }
}