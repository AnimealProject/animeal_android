package com.epmedu.animeal.tabs.more.account.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.common.presentation.viewmodel.delegate.ActionDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.networkuser.domain.usecase.DeleteNetworkUserUseCase
import com.epmedu.animeal.networkuser.domain.usecase.LogOutUseCase
import com.epmedu.animeal.profile.domain.ClearProfileUseCase
import com.epmedu.animeal.tabs.more.account.AccountScreenEvent
import com.epmedu.animeal.tabs.more.account.AccountScreenEvent.Delete
import com.epmedu.animeal.tabs.more.account.AccountScreenEvent.Logout
import com.epmedu.animeal.tabs.more.account.AccountScreenEvent.ToastShown
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AccountViewModel @Inject constructor(
    private val actionDelegate: ActionDelegate,
    private val logOutUseCase: LogOutUseCase,
    private val deleteNetworkUserUseCase: DeleteNetworkUserUseCase,
    private val clearProfileUseCase: ClearProfileUseCase
) : ViewModel(),
    ActionDelegate by actionDelegate,
    StateDelegate<AccountScreenState> by DefaultStateDelegate(AccountScreenState()) {

    fun handleScreenEvent(event: AccountScreenEvent) {
        when (event) {
            Logout -> logout()
            Delete -> delete()
            ToastShown -> clearToast()
        }
    }

    private fun logout() {
        performAccountAction(logOutUseCase::invoke, AccountToast.SuccessfulLogout)
    }

    private fun delete() {
        performAccountAction(deleteNetworkUserUseCase::invoke, AccountToast.SuccessfulDelete)
    }

    private fun clearToast() {
        updateState { copy(toastToShow = null) }
    }

    private fun performAccountAction(
        action: suspend () -> ActionResult<Unit>,
        toastToShowOnSuccess: AccountToast
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