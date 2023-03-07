package com.epmedu.animeal.splash.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.networkuser.domain.usecase.GetIsPhoneNumberVerifiedUseCase
import com.epmedu.animeal.networkuser.domain.usecase.authenticationtype.SetFacebookAuthenticationTypeUseCase
import com.epmedu.animeal.networkuser.domain.usecase.authenticationtype.SetMobileAuthenticationTypeUseCase
import com.epmedu.animeal.profile.domain.LogOutUseCase
import com.epmedu.animeal.splash.domain.usecase.GetIsProfileSavedUseCase
import com.epmedu.animeal.splash.domain.usecase.GetIsSignedInUseCase
import com.epmedu.animeal.splash.domain.usecase.SetFinishProfileAsStartDestinationUseCase
import com.epmedu.animeal.splash.presentation.SplashScreenEvent
import com.epmedu.animeal.splash.presentation.SplashScreenEvent.ErrorShown
import com.epmedu.animeal.splash.presentation.viewmodel.SplashNextDestination.Home
import com.epmedu.animeal.splash.presentation.viewmodel.SplashNextDestination.SignUp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("LongParameterList")
@HiltViewModel
internal class SplashViewModel @Inject constructor(
    private val getIsSignedInUseCase: GetIsSignedInUseCase,
    private val getIsProfileSavedUseCase: GetIsProfileSavedUseCase,
    private val getIsPhoneNumberVerifiedUseCase: GetIsPhoneNumberVerifiedUseCase,
    private val setMobileAuthenticationTypeUseCase: SetMobileAuthenticationTypeUseCase,
    private val setFacebookAuthenticationTypeUseCase: SetFacebookAuthenticationTypeUseCase,
    private val setFinishProfileAsStartDestinationUseCase: SetFinishProfileAsStartDestinationUseCase,
    private val logOutUseCase: LogOutUseCase
) : ViewModel(),
    StateDelegate<SplashState> by DefaultStateDelegate(SplashState()) {

    init {
        checkIfUserIsSignedIn()
    }

    private fun checkIfUserIsSignedIn() {
        viewModelScope.launch {
            val (isSignedIn, isProfileSaved, isPhoneNumberVerified) = awaitAll(
                async { getIsSignedInUseCase() },
                async { getIsProfileSavedUseCase() },
                async { getIsPhoneNumberVerifiedUseCase() }
            )

            if (isSignedIn) {
                selectNextDirection(isPhoneNumberVerified, isProfileSaved)
            } else {
                navigateToNextDirection(SignUp)
            }
        }
    }

    private suspend fun selectNextDirection(
        isPhoneNumberVerified: Boolean,
        isProfileSaved: Boolean
    ) {
        when {
            isPhoneNumberVerified && isProfileSaved -> {
                navigateToNextDirection(Home)
            }
            isPhoneNumberVerified -> {
                setMobileAuthenticationTypeUseCase()
                setFinishProfileAsStartDestinationUseCase()
                navigateToNextDirection(SignUp)
            }
            isProfileSaved -> {
                setFacebookAuthenticationTypeUseCase(isPhoneNumberVerified = false)
                setFinishProfileAsStartDestinationUseCase()
                navigateToNextDirection(SignUp)
            }
            else -> {
                logOutUseCase(
                    onSuccess = {
                        navigateToNextDirection(SignUp)
                    },
                    onError = {
                        updateState { copy(isError = true) }
                    }
                )
            }
        }
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