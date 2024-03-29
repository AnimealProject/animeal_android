package com.epmedu.animeal.splash.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.common.presentation.viewmodel.delegate.ActionDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.network.NetworkState
import com.epmedu.animeal.network.NetworkStateProvider
import com.epmedu.animeal.networkuser.domain.usecase.GetCurrentUserGroupUseCase
import com.epmedu.animeal.networkuser.domain.usecase.GetIsPhoneNumberVerifiedUseCase
import com.epmedu.animeal.networkuser.domain.usecase.LogOutUseCase
import com.epmedu.animeal.networkuser.domain.usecase.authenticationtype.SetFacebookAuthenticationTypeUseCase
import com.epmedu.animeal.networkuser.domain.usecase.authenticationtype.SetMobileAuthenticationTypeUseCase
import com.epmedu.animeal.splash.domain.usecase.GetIsProfileSavedUseCase
import com.epmedu.animeal.splash.domain.usecase.GetIsSignedInUseCase
import com.epmedu.animeal.splash.domain.usecase.SetFinishProfileAsStartDestinationUseCase
import com.epmedu.animeal.splash.domain.usecase.SetOnboardingAsSignUpStartDestinationUseCase
import com.epmedu.animeal.splash.presentation.SplashScreenEvent
import com.epmedu.animeal.splash.presentation.SplashScreenEvent.ErrorShown
import com.epmedu.animeal.splash.presentation.viewmodel.SplashNextDestination.Home
import com.epmedu.animeal.splash.presentation.viewmodel.SplashNextDestination.SignUp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SplashViewModel @Inject constructor(
    actionDelegate: ActionDelegate,
    private val getIsSignedInUseCase: GetIsSignedInUseCase,
    private val getIsProfileSavedUseCase: GetIsProfileSavedUseCase,
    private val getIsPhoneNumberVerifiedUseCase: GetIsPhoneNumberVerifiedUseCase,
    private val getCurrentUserGroupUseCase: GetCurrentUserGroupUseCase,
    private val setMobileAuthenticationTypeUseCase: SetMobileAuthenticationTypeUseCase,
    private val setFacebookAuthenticationTypeUseCase: SetFacebookAuthenticationTypeUseCase,
    private val setFinishProfileAsStartDestinationUseCase: SetFinishProfileAsStartDestinationUseCase,
    private val setOnboardingAsSignUpStartDestinationUseCase: SetOnboardingAsSignUpStartDestinationUseCase,
    private val networkStateProvider: NetworkStateProvider,
    private val logOutUseCase: LogOutUseCase
) : ViewModel(),
    StateDelegate<SplashState> by DefaultStateDelegate(SplashState()),
    ActionDelegate by actionDelegate {

    init {
        getNetWorkStateAsync().onAwait.run {
            checkIfUserIsSignedIn()
        }
    }

    private fun getNetWorkStateAsync() = viewModelScope.async {
        networkStateProvider.getNetworkState().collect { state ->
            updateState {
                copy(networkState = state)
            }
        }
    }

    private fun checkIfUserIsSignedIn() {
        viewModelScope.launch {
            if (getIsSignedInUseCase()) {
                val results = awaitAll(
                    async { getIsPhoneNumberVerifiedUseCase() },
                    async { getIsProfileSavedUseCase() },
                    async { getCurrentUserGroupUseCase(shouldFetch = true) }
                )
                val isPhoneNumberVerified = results[0] as ActionResult<Boolean>
                val isProfileSaved = results[1] as Boolean

                selectNextDirection(isPhoneNumberVerified, isProfileSaved)
            } else {
                navigateToNextDirection(SignUp)
            }
        }
    }

    private suspend fun selectNextDirection(
        isPhoneNumberVerifiedResult: ActionResult<Boolean>,
        isProfileSaved: Boolean
    ) {
        if (isPhoneNumberVerifiedResult is ActionResult.Success) {
            val isPhoneNumberVerified = isPhoneNumberVerifiedResult.result
            when {
                isPhoneNumberVerified && isProfileSaved -> {
                    navigateToNextDirection(Home)
                }
                isPhoneNumberVerified -> {
                    setMobileAuthenticationTypeUseCase()
                    setNextDestinationByNetworkState()
                    navigateToNextDirection(SignUp)
                }
                isProfileSaved -> {
                    setFacebookAuthenticationTypeUseCase(isPhoneNumberVerified = false)
                    setNextDestinationByNetworkState()
                    navigateToNextDirection(SignUp)
                }
                else -> {
                    logOut()
                }
            }
        } else {
            logOut()
        }
    }

    private fun setNextDestinationByNetworkState() {
        if (state.networkState == NetworkState.Available) {
            setFinishProfileAsStartDestinationUseCase()
        } else {
            setOnboardingAsSignUpStartDestinationUseCase()
        }
    }

    private suspend fun logOut() {
        performAction(
            action = {
                logOutUseCase()
            },
            onSuccess = {
                navigateToNextDirection(SignUp)
            },
            onError = {
                updateState { copy(isError = true) }
            }
        )
    }

    private fun navigateToNextDirection(destination: SplashNextDestination) {
        updateState { copy(nextDestination = destination) }
    }

    fun handleEvents(event: SplashScreenEvent) {
        when (event) {
            is ErrorShown -> updateState { copy(isError = false) }
        }
    }
}